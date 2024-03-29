import time
from typing import Tuple, List, Union, Dict, Counter

import numpy as np
import pandas as pd
import torch
from torch import optim, nn
from torch.utils.data import DataLoader

from classifiers.BaseClassifier import BaseClassifier, ClassificationResult, compute_classification_result
from classifiers.config import Config
from model.ProjectClassifier import ProjectClassifier
from preprocessing.context_split import ContextSplit
from util import ProcessedFolder


class NNClassifier(BaseClassifier):
    """
    An implementation of PbNN classifier. For the code of the neural network part please see authorship_pipeline.model
    """
    def __init__(self, config: Config, project_folder: ProcessedFolder, change_entities: pd.Series,
                 change_to_time_bucket: Dict, min_max_count: Tuple[int, int], author_occurrences: Counter,
                 context_splits: List[ContextSplit]):
        super(NNClassifier, self).__init__(config, project_folder, change_entities, change_to_time_bucket,
                                           min_max_count, author_occurrences, context_splits)

    def __sample_loaders(self, fold_ind: Union[int, Tuple[int, int]] = 0) -> Tuple[DataLoader, DataLoader]:
        """
        Define training and testing data loaders for a given testing fold.
        """
        train_dataset, test_dataset = self._split_train_test(self._loader, fold_ind, pad=True)
        train_loader = DataLoader(train_dataset, self.config.batch_size(), shuffle=True)
        test_loader = DataLoader(test_dataset, self.config.batch_size())
        return train_loader, test_loader

    def __train(self, train_loader, test_loaders, model, optimizer, loss_function, n_epochs, log_batches, batch_size,
                fold_ind, should_train):
        """
        Train the model and report metrics for each of the testing datasets defined by test_loaders.
        """
        print("Start training")
        accuracies = [ClassificationResult(0, 0, 0, 0) for _ in range(len(test_loaders))]
        if not should_train:
            n_epochs = 1

        for epoch in range(n_epochs):
            print("Epoch #{}".format(epoch + 1))
            model.train()
            if should_train:
                current_loss = 0
                start_time = time.time()
                for n_batch, sample in enumerate(train_loader):
                    starts, paths, ends, labels = sample['starts'], sample['paths'], sample['ends'], sample['labels']
                    optimizer.zero_grad()

                    predictions = model((starts, paths, ends))
                    loss = loss_function(predictions, labels)
                    loss.backward()
                    optimizer.step()

                    current_loss += loss.item()
                    if (n_batch + 1) % log_batches == 0:
                        print("After {} batches: average loss {}".format(n_batch + 1, current_loss / log_batches))
                        print(f"Throughput {int(log_batches * batch_size / (time.time() - start_time))} examples / sec")
                        current_loss = 0
                        start_time = time.time()

            model.eval()
            with torch.no_grad():
                for i, test_loader in enumerate(test_loaders):
                    total = len(test_loader.dataset)
                    predictions = np.zeros(total)
                    targets = np.zeros(total)
                    cur = 0
                    test_loss = 0.
                    n_batches = 0
                    for sample in test_loader:
                        starts, paths, ends, labels = sample['starts'], sample['paths'], sample['ends'], sample[
                            'labels']
                        batched_predictions = model((starts, paths, ends))

                        test_loss += loss_function(batched_predictions, labels)
                        n_batches += 1

                        batched_predictions = np.argmax(batched_predictions, axis=1)
                        batched_targets = labels
                        predictions[cur:cur + len(batched_predictions)] = batched_predictions
                        targets[cur:cur + len(batched_targets)] = batched_targets
                        cur += len(batched_predictions)

                    # print(predictions)
                    # print(targets)
                    print("average loss {}".format(test_loss / n_batches))
                    classification_result = compute_classification_result(targets, predictions, fold_ind)
                    print(f"classification results: {classification_result}")
                    accuracies[i] = max(accuracies[i], classification_result, key=lambda cl: cl.accuracy)
                    # values, counts = np.unique(predictions, return_counts=True)
                    # vc = [(c, v) for v, c in zip(values, counts)]
                    # for cnt, val in sorted(vc):
                    #     print(cnt, val)

        print("Training completed")
        return accuracies

    def __run_classifier(self, train_loader: DataLoader, test_loaders: Union[DataLoader, List[DataLoader]], fold_ind) \
            -> Union[float, List[float]]:
        if isinstance(fold_ind, int) or isinstance(fold_ind, np.int64) or fold_ind[0] not in self.models:
            model = ProjectClassifier(self._loader.tokens().size,
                                      self._loader.paths().size,
                                      dim=self.config.hidden_dim(),
                                      n_classes=self._loader.n_classes())
            should_train = True
        else:
            model = self.models[fold_ind[0]]
            should_train = False

        optimizer = optim.Adam(model.parameters(), lr=self.config.learning_rate())
        loss_function = nn.CrossEntropyLoss()
        if type(test_loaders) is DataLoader:
            test_loaders = [test_loaders]
        # test_loaders.append(train_loader)
        accuracies = self.__train(train_loader, test_loaders, model, optimizer, loss_function,
                                  n_epochs=self.config.epochs(),
                                  log_batches=self.config.log_batches(),
                                  batch_size=self.config.batch_size(),
                                  fold_ind=fold_ind, should_train=should_train)

        if not isinstance(fold_ind, int) and not isinstance(fold_ind, np.int64) and fold_ind[0] not in self.models:
            self.models[fold_ind[0]] = model

        if len(test_loaders) == 1:
            return max(accuracies, key=lambda cl: cl.accuracy)
        else:
            return accuracies

    def run(self, fold_indices: Union[List[int], List[Tuple[int, int]]]) \
            -> Tuple[float, float, List[ClassificationResult]]:
        """
        Run experiments for all the testing datasets defined by fold_indices.
        """
        print("Begin cross validation")
        scores = []
        for fold_ind in fold_indices:
            # print(fold_ind)
            train_loader, test_loader = self.__sample_loaders(fold_ind)
            scores.append(self.__run_classifier(train_loader, test_loader, fold_ind))
            print(scores[-1])
        print(scores)
        mean = float(np.mean([score.accuracy for score in scores]))
        std = float(np.std([score.accuracy for score in scores]))
        return mean, std, scores

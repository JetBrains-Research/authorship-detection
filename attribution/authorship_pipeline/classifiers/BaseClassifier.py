from collections import namedtuple
from math import ceil
from typing import Tuple, Dict, Union, List, Counter

import numpy as np
import pandas as pd

from classifiers.config import Config
from data_loading.PathMinerDataset import PathMinerDataset
from data_loading.PathMinerLoader import PathMinerLoader
from data_loading.PathMinerSnapshotLoader import PathMinerSnapshotLoader
from preprocessing.context_split import PickType, ContextSplit
from util import ProcessedFolder, ProcessedSnapshotFolder

ClassificationResult = namedtuple(
    'ClassificationResult',
    ('accuracy', 'macro_precision', 'macro_recall', 'fold_ind')
)


def compute_classification_result(
        true_labels: List, predicted_labels: List, fold_ind: Union[int, Tuple[int, int]]
) -> ClassificationResult:
    """
    Compute metric values (accuracy, precision, recall), given the predictions.
    :param true_labels: true authors
    :param predicted_labels: model's predictions
    :param fold_ind: index that is used to refer to the fold in cross-validation
    :return: an instance of ClassificationResult that contains the computed metric values
    """
    true_labels = np.array(true_labels, dtype=np.int)
    predicted_labels = np.array(predicted_labels, dtype=np.int)
    labels, counts = np.unique(true_labels, return_counts=True)
    tp, fp, tn, fn = 0, 0, 0, 0
    precisions = []
    recalls = []
    # print('===========')
    # for true_label, predicted_label in zip(true_labels, predicted_labels):
    #     if true_label != predicted_label:
    #         print(f'true: {true_label} predicted: {predicted_label}')
    # print('===========')
    for label, count in zip(labels, counts):
        true_positive = np.sum(np.logical_and(true_labels == label, predicted_labels == label))
        false_positive = np.sum(np.logical_and(true_labels != label, predicted_labels == label))
        true_negative = np.sum(np.logical_and(true_labels != label, predicted_labels != label))
        false_negative = np.sum(np.logical_and(true_labels == label, predicted_labels != label))
        tp += true_positive
        fp += false_positive
        tn += true_negative
        fn += false_negative
        precisions.append(tp / (tp + fp) if (tp + fp > 0) else 0.)
        recalls.append(tp / (tp + fn))

    return ClassificationResult(
        accuracy=np.mean(true_labels == predicted_labels),
        macro_precision=np.mean(precisions),
        macro_recall=np.mean(recalls),
        fold_ind=fold_ind
    )


class BaseClassifier:
    """
    Base class for all classifiers that handles correct setup of data loading, data splitting, and cross-validation.
    """
    def __init__(self, config: Config, project_folder: Union[ProcessedFolder, ProcessedSnapshotFolder],
                 change_entities: pd.Series, change_to_time_bucket: Dict, min_max_count: Tuple[int, int],
                 author_occurrences: Counter, context_splits: List[ContextSplit]):
        self.config = config
        self.__fix_random()
        if config.mode() == "snapshot":
            self._loader = PathMinerSnapshotLoader(project_folder)
        else:
            self._loader = PathMinerLoader(
                project_folder, change_entities, change_to_time_bucket, min_max_count, author_occurrences,
                context_splits
            )
        self.__indices_per_class, self._n_classes = self.__split_into_classes()
        self.update_chosen_classes()
        self.models = {}

    def __fix_random(self):
        np.random.seed(self.config.seed())
        self.__seed = self.config.seed()

    def __split_into_classes(self) -> Tuple[np.ndarray, int]:
        """
        Computes indices that belong to each class (author).
        """
        print("Splitting into classes")
        index = self._loader.labels()
        n_classes = self._loader.n_classes()
        indices_per_class = [[] for _ in range(n_classes)]
        for i, ind in enumerate(index):
            indices_per_class[ind].append(i)
        indices_per_class = np.array([np.array(inds, dtype=np.int32) for inds in indices_per_class])
        # for k in range(n_classes):
        #     np.random.shuffle(indices_per_class[k])
        return indices_per_class, n_classes

    def update_chosen_classes(self):
        """
        For evaluation on the data from a subset of authors, this method re-samples the picked authors.
        If all the authors should be used, it keeps selecting the complete set of authors.
        """
        chosen_classes = np.random.choice(self._n_classes, self.config.n_classes(), replace=False) \
            if self.config.n_classes() is not None \
            else np.arange(self._n_classes)
        self.__chosen_classes = chosen_classes

    def _split_train_test(self, loader: PathMinerLoader, fold_ind: Union[int, Tuple[int, int]], pad: bool = False) \
            -> Tuple[PathMinerDataset, PathMinerDataset]:
        """
        Creates train and test datasets. The type of the experiment (regular, context, time) is controlled by the config
        passed to the Classifier object at the initialization step. Fold index is used to tell which part of data to
        use for testing (selected fold in cross-validation, test slice for 'time', or test subset of code snippets for
        'context').
        :param loader: data loader
        :param fold_ind: part of data used for testing (number in case of cross-validation or 'context', two numbers for 'time')
        :param pad: whether to pad data (used for preparing tensors for the Neural Network model)
        :return: a tuple of training and testing datasets
        """
        chosen_classes = self.__chosen_classes
        if self.config.mode() == 'time':
            train_fold, test_fold = fold_ind
            train_indices = self._loader.time_buckets() == train_fold
            test_indices = self._loader.time_buckets() == test_fold
        elif self.config.mode() == 'context':
            train_indices = self._loader.context_indices(fold_ind) == PickType.TRAIN
            test_indices = self._loader.context_indices(fold_ind) == PickType.TEST
        else:
            test_size = self.config.test_size()
            if isinstance(test_size, int):
                start_ind = fold_ind * test_size
                train_indices = np.concatenate([
                    np.concatenate((inds[:min(inds.size, start_ind)], inds[min(inds.size, start_ind + test_size):]))
                    for inds in self.__indices_per_class[chosen_classes]
                ])
                test_indices = np.concatenate([
                    inds[min(inds.size, start_ind):min(inds.size, start_ind + test_size)]
                    for inds in self.__indices_per_class[chosen_classes]
                ])
            else:
                train_indices = np.concatenate([
                    np.concatenate((inds[:ceil(test_size * inds.size) * fold_ind],
                                    inds[min(inds.size, ceil(test_size * inds.size) * (fold_ind + 1)):]))
                    for inds in self.__indices_per_class[chosen_classes]
                ])
                test_indices = np.concatenate([
                    inds[
                    ceil(test_size * inds.size) * fold_ind:min(inds.size, ceil(test_size * inds.size) * (fold_ind + 1))]
                    for inds in self.__indices_per_class[chosen_classes]
                ])
            train_indices = np.array(train_indices, dtype=np.int32)
            test_indices = np.array(test_indices, dtype=np.int32)

        return self._create_datasets(loader, train_indices, test_indices, pad)

    def _create_datasets(self, loader, train_indices, test_indices, pad) -> Tuple[PathMinerDataset, PathMinerDataset]:
        """
        :return: datasets for training and testing
        """
        return PathMinerDataset.from_loader(loader, train_indices, pad), \
               PathMinerDataset.from_loader(loader, test_indices, pad)

    def cross_validation_folds(self) -> List[int]:
        """
        :return: a list of fold indices depending on the test size passed in config
        """
        test_size = self.config.test_size()
        if isinstance(test_size, float):
            return list(range(int(np.ceil(1. / test_size))))
        else:
            return list(range((int(np.ceil(max([inds.size for inds in self.__indices_per_class]) / test_size)))))

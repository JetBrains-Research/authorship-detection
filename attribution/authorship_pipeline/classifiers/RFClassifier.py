from typing import List, Tuple, Union, Dict, Counter

import numpy as np
import pandas as pd
from scipy.sparse import csc_matrix
from sklearn.ensemble import RandomForestClassifier

from classifiers.BaseClassifier import BaseClassifier, ClassificationResult, compute_classification_result
from classifiers.config import Config
from data_loading.PathMinerDataset import PathMinerDataset
from preprocessing.compute_rf_mi import compute_mi, limit_features
from preprocessing.context_split import ContextSplit
from util import ProcessedFolder


class RFClassifier(BaseClassifier):
    """
    An implementation of PbRF classifier.
    """
    def __init__(self, config: Config, project_folder: ProcessedFolder, change_entities: pd.Series,
                 change_to_time_bucket: Dict, min_max_count: Tuple[int, int], author_occurrences: Counter,
                 context_splits: List[ContextSplit]):
        super(RFClassifier, self).__init__(config, project_folder, change_entities, change_to_time_bucket,
                                           min_max_count, author_occurrences, context_splits)
        self.__feature_scores = None
        self.mis = {}

    def __build_sparse_matrix(self, dataset: PathMinerDataset, features: List[str]) -> csc_matrix:
        """
        Build a sparse matrix containing normalized frequencies of tokens/paths for each author.
        """
        print("Building sparse matrix")
        feature_counts = [self.__feature_count(f) for f in features]
        data = []
        row_ind, col_ind = [], []
        pref = 0
        for feature, feature_count in zip(features, feature_counts):
            for i, item in enumerate(dataset):
                inds, counts = np.unique(item[feature], return_counts=True)
                normalizer = counts.sum()

                for ind, count in zip(inds, counts):
                    data.append(count / normalizer)
                    row_ind.append(i)
                    col_ind.append(pref + ind)

                # for ind, count in zip(inds, counts):
                #     data.append(count / normalizer)
                #     row_ind.append(i)
                #     col_ind.append(pref + feature_count + ind)

            # pref += 2 * feature_count
            pref += feature_count
        # print(data)
        print(max(row_ind))
        print(max(col_ind))
        print(len(dataset), pref)
        return csc_matrix((data, (row_ind, col_ind)), shape=(len(dataset), pref))

    def __feature_count(self, feature: str):
        if feature == 'paths':
            return self._loader.paths().size
        if feature == 'starts' or feature == 'ends':
            return self._loader.tokens().size
        return 0

    def __create_samples(self, fold_ind: Union[int, Tuple[int, int]] = 0):
        """
        Prepares training and testing data in a form of sparse feature matrices
        """
        train_dataset, test_dataset = self._split_train_test(self._loader, fold_ind)

        X_train = self.__build_sparse_matrix(train_dataset, self.config.features())
        y_train = train_dataset.labels()
        X_test = self.__build_sparse_matrix(test_dataset, self.config.features())
        y_test = test_dataset.labels()
        if self.config.feature_count() is not None:
            if isinstance(fold_ind, int) or fold_ind[0] not in self.mis:
                mi = compute_mi(X_train, train_dataset.labels())

                if not isinstance(fold_ind, int):
                    self.mis[fold_ind[0]] = mi
            else:
                mi = self.mis[fold_ind[0]]
            # token_strings = self._loader.tokens()
            # print(token_strings)
            # path_strings = list(map(
            #     lambda path: path.prettyprint(self._loader.node_types())
            #     if path is not None else None,
            #     self._loader.paths())
            # )
            # print(path_strings)
            X_train = limit_features(X_train, mi, self.config.feature_count())
            X_test = limit_features(X_test, mi, self.config.feature_count())

        print(X_train.shape, y_train.shape, X_test.shape, y_test.shape)
        return X_train, X_test, y_train, y_test

    def run(self, fold_indices: Union[List[int], List[Tuple[int, int]]]) \
            -> Tuple[float, float, List[ClassificationResult]]:
        """
        Runs experiments for multiple folds and reports averaged metrics as well as metrics for each experiment.
        :param fold_indices: fold indices used for testing
        """
        print("Begin cross validation")
        scores = []
        for fold_ind in fold_indices:
            X_train, X_test, y_train, y_test = self.__create_samples(fold_ind)
            scores.append(self.__run_classifier(X_train, X_test, y_train, y_test, fold_ind))
            print(scores[-1])
        print(scores)
        mean = float(np.mean([score.accuracy for score in scores]))
        std = float(np.std([score.accuracy for score in scores]))
        return mean, std, scores

    def __run_classifier(self, X_train, X_test, y_train, y_test, fold_ind, single=True) -> \
            Union[ClassificationResult, List[ClassificationResult]]:
        """
        Run classification for a single fold given sparse feature matrices.
        """
        params = self.config.params()
        if isinstance(fold_ind, int) or fold_ind[0] not in self.models:
            model = RandomForestClassifier(**params)
            print("Fitting classifier")
            model.fit(X_train, y_train)
            if not isinstance(fold_ind, int):
                self.models[fold_ind[0]] = model
        else:
            model = self.models[fold_ind[0]]

        print("Making predictions")
        if single:
            predictions = model.predict(X_test)
            return compute_classification_result(y_test, predictions, fold_ind)
        else:
            return [compute_classification_result(y, model.predict(X), fold_ind) for X, y in zip(X_test, y_test)]

    def _create_datasets(self, loader, train_indices, test_indices, pad) -> Tuple[PathMinerDataset, PathMinerDataset]:
        if self.config.mode() != "snapshot":
            return super(RFClassifier, self)._create_datasets(loader, train_indices, test_indices, pad)
        return PathMinerDataset.from_rf_loader(loader, train_indices), \
               PathMinerDataset.from_rf_loader(loader, test_indices)

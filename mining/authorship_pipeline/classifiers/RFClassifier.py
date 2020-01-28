import os
from typing import List, Tuple, Union, Dict

import numpy as np
import pandas as pd
from scipy.sparse import csc_matrix
from sklearn.ensemble import RandomForestClassifier
from sklearn.feature_selection import mutual_info_classif
from sklearn.metrics import accuracy_score

from classifiers.BaseClassifier import BaseClassifier, ClassificationResult
from classifiers.config import Config
from data_loading.PathMinerDataset import PathMinerDataset
from util import ProcessedFolder


class RFClassifier(BaseClassifier):
    def __init__(self, config: Config, project_folder: ProcessedFolder, change_entities: pd.Series,
                 change_to_time_bucket: Dict, min_max_count: Tuple[int, int]):
        super(RFClassifier, self).__init__(config, project_folder, change_entities, change_to_time_bucket,
                                           min_max_count)
        self.__feature_scores = None

    def __build_sparse_matrix(self, dataset: PathMinerDataset, features: List[str]) -> csc_matrix:
        print("Building sparse matrix")
        feature_counts = [self.__feature_count(f) for f in features]
        data = []
        row_ind, col_ind = [], []
        pref = 0
        for feature, feature_count in zip(features, feature_counts):
            for i, item in enumerate(dataset):
                inds, counts = np.unique(item[feature], return_counts=True)

                for ind, count in zip(inds, counts):
                    data.append(count)
                    row_ind.append(i)
                    col_ind.append(pref + ind)

            pref += feature_count

        return csc_matrix((data, (row_ind, col_ind)), shape=(len(dataset), sum(feature_counts)))

    def __feature_count(self, feature: str):
        if feature == 'paths':
            return self._loader.paths().size
        if feature == 'starts' or feature == 'ends':
            return self._loader.tokens().size
        return 0

    def __create_samples(self, fold_ind: Union[int, Tuple[int, int]] = 0):
        train_dataset, test_dataset = self._split_train_test(self._loader, fold_ind)

        X_train = self.__build_sparse_matrix(train_dataset, self.config.features())
        y_train = train_dataset.labels()
        X_test = self.__build_sparse_matrix(test_dataset, self.config.features())
        y_test = test_dataset.labels()
        if self.config.feature_count() is not None:
            X_train = self.__top_features(X_train, train_dataset.labels(), self.config.feature_count())
            X_test = self.__top_features(X_test, test_dataset.labels(), self.config.feature_count())

        print(X_train.shape, y_train.shape, X_test.shape, y_test.shape)
        return X_train, X_test, y_train, y_test

    # def __create_contextsplit_samples(self, depth: int = 0, random: bool = False):
    #     train_dataset, test_dataset = self._context_split(self._loader, depth, random)
    #     # scores = load_feature_scores(train_dataset, args.features, feature_counts, args.score_file, debug=debug)
    #     # valid_features = []
    #     # pref = 0
    #     # for total_count, feature_count in zip(feature_counts, args.feature_counts):
    #     #     valid_features.append(top_scores_inds(scores[pref:pref + total_count], feature_count))
    #     #     pref += total_count
    #
    #     X_train = self.__build_sparse_matrix(train_dataset, self.config.features())
    #     y_train = train_dataset.labels()
    #     X_test = self.__build_sparse_matrix(test_dataset, self.config.features())
    #     y_test = test_dataset.labels()
    #     if self.config.feature_count() is not None:
    #         X_train = self.__top_features(X_train, train_dataset.labels(), self.config.feature_count())
    #         X_test = self.__top_features(X_test, test_dataset.labels(), self.config.feature_count())
    #
    #     print(X_train.shape, y_train.shape, X_test.shape, y_test.shape)
    #     return X_train, X_test, y_train, y_test

    def run(self, fold_indices: Union[List[int], List[Tuple[int, int]]]) \
            -> Tuple[float, float, List[ClassificationResult]]:
        print("Begin cross validation")
        scores = []
        for fold_ind in fold_indices:
            X_train, X_test, y_train, y_test = self.__create_samples(fold_ind)
            scores.append(ClassificationResult(
                float(self.__run_classifier(X_train, X_test, y_train, y_test)),
                fold_ind
            ))
        print(scores)
        mean = float(np.mean([score.accuracy for score in scores]))
        std = float(np.std([score.accuracy for score in scores]))
        return mean, std, scores

    # def contextsplit_validate(self) -> Tuple[float, float, List[float]]:
    #     print("Begin cross validation")
    #     scores = []
    #     for depth in range(self.config.contextsplit_depth()):
    #         X_train, X_test, y_train, y_test = self.__create_contextsplit_samples(depth)
    #         scores.append(float(self.__run_classifier(X_train, X_test, y_train, y_test)))
    #     print(scores)
    #     return float(np.mean(scores)), float(np.std(scores)), scores
    #
    # def random_contextsplit(self) -> float:
    #     return self.__run_classifier(*self.__create_contextsplit_samples(random=True))

    def __run_classifier(self, X_train, X_test, y_train, y_test, single=True) -> Union[float, List[float]]:
        params = self.config.params()
        model = RandomForestClassifier(**params)
        print("Fitting classifier")
        model.fit(X_train, y_train)
        print("Making predictions")
        if single:
            predictions = model.predict(X_test)
            return accuracy_score(y_test, predictions)
        else:
            return [accuracy_score(y, model.predict(X)) for X, y in zip(X_test, y_test)]

    def __top_features(self, feature_matrix: Union[np.ndarray, csc_matrix], labels: np.ndarray,
                       n_count: int) -> Union[np.ndarray, csc_matrix]:
        print("Filtering")
        if feature_matrix.shape[1] <= n_count:
            return feature_matrix
        mutual_information = self.__mutual_information(feature_matrix, labels)
        indices = np.argsort(mutual_information)[-n_count:]
        return feature_matrix[:, indices]

    def __mutual_information(self, feature_matrix: Union[np.ndarray, csc_matrix], labels: np.ndarray) -> np.ndarray:
        if self.__feature_scores is None:
            save_filename = self.config.mutual_info_file()
            if save_filename is not None and os.path.isfile(save_filename):
                self.__feature_scores = np.fromfile(save_filename)
            else:
                print("Computing mutual information")
                mutual_information = mutual_info_classif(feature_matrix, labels, discrete_features=True)
                print(mutual_information)
                mutual_information.tofile(save_filename)
                self.__feature_scores = mutual_information
        return self.__feature_scores

from typing import List, Tuple, Union, Dict

import numpy as np
import pandas as pd
from scipy.sparse import csc_matrix
from sklearn.ensemble import RandomForestClassifier

from classifiers.BaseClassifier import ClassificationResult, compute_classification_result
from classifiers.config import Config
from preprocessing.compute_caliskan_features import compute_caliskan_features
from preprocessing.context_split import ContextSplit, PickType
from util import ProcessedFolder


class CaliskanClassifier():
    """
    A re-implementation supporting Java of the authorship attribution model described by Caliskan et al.
    """
    def __init__(self, config: Config, project_folder: ProcessedFolder, change_entities: pd.Series,
                 change_to_time_bucket: Dict, min_max_count: Tuple[int, int], context_splits: List[ContextSplit]):

        self.config = config
        self.dataset, self.mutual_info = compute_caliskan_features(project_folder)
        self.change_to_time_bucket = change_to_time_bucket
        self.context_splits = context_splits

        if config.feature_count() is not None:
            self.dataset.limit_features(mutual_information=self.mutual_info, n_features=config.feature_count())
        self.dataset.filter_authors(min_max_count)
        self.models = {}

    def __split_data(self, fold_ind: Union[int, Tuple[int, int]]) -> \
            Tuple[csc_matrix, np.ndarray, csc_matrix, np.ndarray]:
        """
        Prepares training and testing data in a form of sparse feature matrices
        """
        if self.config.mode() == 'time':
            train_fold, test_fold = fold_ind
            train_indices = [
                i
                for i, change_id in enumerate(self.dataset.change_ids)
                if self.change_to_time_bucket[change_id] == train_fold
            ]
            test_indices = [
                i
                for i, change_id in enumerate(self.dataset.change_ids)
                if self.change_to_time_bucket[change_id] == test_fold
            ]
        elif self.config.mode() == 'context':
            train_indices = [
                i
                for i, change_id in enumerate(self.dataset.change_ids)
                if change_id in self.context_splits[fold_ind].change_to_pick_type and
                   self.context_splits[fold_ind].change_to_pick_type[change_id] == PickType.TRAIN
            ]
            test_indices = [
                i
                for i, change_id in enumerate(self.dataset.change_ids)
                if change_id in self.context_splits[fold_ind].change_to_pick_type and
                   self.context_splits[fold_ind].change_to_pick_type[change_id] == PickType.TEST
            ]
        else:
            pass
        X_train, y_train = self.dataset.feature_values[train_indices, :], self.dataset.authors[train_indices]
        X_test, y_test = self.dataset.feature_values[test_indices, :], self.dataset.authors[test_indices]
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
            X_train, X_test, y_train, y_test = self.__split_data(fold_ind)
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

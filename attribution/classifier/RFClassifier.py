import numpy as np
import os
from classifier.BaseClassifier import BaseClassifier
from classifier.config import Config
from data_processing.PathMinerDataset import PathMinerDataset
from scipy.sparse import csc_matrix
from sklearn.ensemble import RandomForestClassifier
from sklearn.feature_selection import mutual_info_classif
from sklearn.metrics import accuracy_score
from typing import List, Tuple, Union


class RFClassifier(BaseClassifier):
    def __init__(self, config: Config):
        super(RFClassifier, self).__init__(config)
        self.__feature_scores = None
        # print(self.config.use_explicit_features())

    def __build_sparse_matrix(self, dataset: PathMinerDataset, features: List[str]) -> csc_matrix:
        # print("Building sparse matrix")
        feature_counts = [self.__feature_count(f) for f in features]
        data = []
        row_ind, col_ind = [], []
        pref = 0
        for feature, feature_count in zip(features, feature_counts):
            for i, item in enumerate(dataset):
                if feature != 'explicit':
                    inds, counts = np.unique(item[feature], return_counts=True)
                    # if valid_paths is not None:
                    #     _, intersection_inds = snp.intersect(path_inds, valid_paths, indices=True)
                    #     path_inds = intersection_inds[1]
                    #     path_counts = path_counts[intersection_inds[0]]

                    for ind, count in zip(inds, counts):
                        # if valid_paths is not None and path_ind not in valid_paths:
                        #     continue
                        data.append(count)
                        row_ind.append(i)
                        col_ind.append(pref + ind)
                else:
                    for k, val in enumerate(item[feature]):
                        data.append(val)
                        row_ind.append(i)
                        col_ind.append(pref + k)

            pref += feature_count

        return csc_matrix((data, (row_ind, col_ind)), shape=(len(dataset), sum(feature_counts)))

    def __feature_count(self, feature: str):
        if feature == 'paths':
            return self._loader.paths().size
        if feature == 'starts' or feature == 'ends':
            return self._loader.tokens().size
        if feature == 'explicit':
            return self._loader.explicit_features().shape[1]
        return 0

    def __create_samples(self, fold_ind: int = 0):
        train_dataset, test_dataset = self._split_train_test(self._loader, fold_ind)
        # scores = load_feature_scores(train_dataset, args.features, feature_counts, args.score_file, debug=debug)
        # valid_features = []
        # pref = 0
        # for total_count, feature_count in zip(feature_counts, args.feature_counts):
        #     valid_features.append(top_scores_inds(scores[pref:pref + total_count], feature_count))
        #     pref += total_count

        X_train = self.__build_sparse_matrix(train_dataset, self.config.features())
        y_train = train_dataset.labels()
        X_test = self.__build_sparse_matrix(test_dataset, self.config.features())
        y_test = test_dataset.labels()
        if self.config.feature_count() is not None:
            X_train = self.__top_features(X_train, train_dataset.labels(), self.config.feature_count())
            X_test = self.__top_features(X_test, test_dataset.labels(), self.config.feature_count())

        # print(X_train.shape, y_train.shape, X_test.shape, y_test.shape)
        return X_train, X_test, y_train, y_test

    def __create_contextsplit_samples(self, depth: int = 0, random: bool = False):
        train_dataset, test_dataset = self._context_split(self._loader, depth, random)
        # scores = load_feature_scores(train_dataset, args.features, feature_counts, args.score_file, debug=debug)
        # valid_features = []
        # pref = 0
        # for total_count, feature_count in zip(feature_counts, args.feature_counts):
        #     valid_features.append(top_scores_inds(scores[pref:pref + total_count], feature_count))
        #     pref += total_count

        X_train = self.__build_sparse_matrix(train_dataset, self.config.features())
        y_train = train_dataset.labels()
        X_test = self.__build_sparse_matrix(test_dataset, self.config.features())
        y_test = test_dataset.labels()
        if self.config.feature_count() is not None:
            X_train = self.__top_features(X_train, train_dataset.labels(), self.config.feature_count())
            X_test = self.__top_features(X_test, test_dataset.labels(), self.config.feature_count())

        print(X_train.shape, y_train.shape, X_test.shape, y_test.shape)
        return X_train, X_test, y_train, y_test

    def cross_validate(self) -> Tuple[float, float, List[float]]:
        print("Begin cross validation")
        scores = []
        for n_fold in range(self._n_folds()):
            print(f"Fold {n_fold + 1}")
            X_train, X_test, y_train, y_test = self.__create_samples(n_fold)
            scores.append(float(self.__run_classifier(X_train, X_test, y_train, y_test)))
            print(f"Accuracy at fold {n_fold + 1}: {scores[-1]}")
        print(scores)
        return float(np.mean(scores)), float(np.std(scores)), scores

    def contextsplit_validate(self) -> Tuple[float, float, List[float]]:
        print("Begin cross validation")
        scores = []
        for depth in range(self.config.contextsplit_depth()):
            X_train, X_test, y_train, y_test = self.__create_contextsplit_samples(depth)
            scores.append(float(self.__run_classifier(X_train, X_test, y_train, y_test)))
        print(scores)
        return float(np.mean(scores)), float(np.std(scores)), scores

    def random_contextsplit(self) -> float:
        return self.__run_classifier(*self.__create_contextsplit_samples(random=True))

    def __run_classifier(self, X_train, X_test, y_train, y_test, single=True) -> Union[float, List[float]]:
        params = self.config.params()
        model = RandomForestClassifier(**params)
        print("Fitting classifier...")
        model.fit(X_train, y_train)
        print("Making predictions...")
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

    def timesplit_validate(self) -> List[List[float]]:
        print("Begin timesplit validation")
        datasets = [PathMinerDataset.from_timesplit_loader(self._loader, fold)
                    for fold in range(self.config.time_folds())]
        labels = [dataset.labels() for dataset in datasets]
        matrices = []
        for dataset in datasets:
            matrix = self.__build_sparse_matrix(dataset, self.config.features())
            print(matrix.shape)
            matrices.append(matrix)

        if self.config.feature_count() is not None:
            # print("Stacking matrices")
            # lengths = [matrix.shape[0] for matrix in matrices]
            # stacked_matrices = vstack(matrices)
            # stacked_labels = np.concatenate(labels)
            # filtered_matrix = self.__top_features(stacked_matrices, stacked_labels, self.config.feature_count())
            # cur = 0
            # for i, length in enumerate(lengths):
            #     matrices[i] = filtered_matrix[cur:cur+length]
            #     cur += length
            for i, (matrix, label) in enumerate(zip(matrices, labels)):
                matrices[i] = self.__top_features(matrix, label, self.config.feature_count())
            print("Finished filtering")
        scores = []
        for train_fold_ind in range(self.config.time_folds() - 1):
            scores.append(self.__run_classifier(
                matrices[train_fold_ind],
                [m for i, m in enumerate(matrices) if i != train_fold_ind],
                labels[train_fold_ind],
                [m for i, m in enumerate(labels) if i != train_fold_ind],
                single=False
            ))
            print(scores[-1])
        return scores

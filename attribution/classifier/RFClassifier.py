from typing import List, Tuple

import numpy as np
from scipy.sparse import csr_matrix
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score

from classifier.BaseClassifier import BaseClassifier
from classifier.config import Config
from data_processing.PathMinerDataset import PathMinerDataset


class RFClassifier(BaseClassifier):
    def __init__(self, config: Config):
        super(RFClassifier, self).__init__(config)

    def __build_sparse_matrix(self, dataset: PathMinerDataset, features: List[str]) -> csr_matrix:
        feature_counts = [self.__feature_count(f) for f in features]
        data = []
        row_ind, col_ind = [], []
        pref = 0
        for feature, feature_count in zip(features, feature_counts):
            for i, item in enumerate(dataset):
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
            pref += feature_count

        return csr_matrix((data, (row_ind, col_ind)), shape=(len(dataset), sum(feature_counts)))

    def __feature_count(self, feature: str):
        if feature == 'paths':
            return self._loader.paths().size
        if feature == 'starts' or feature == 'ends':
            return self._loader.tokens().size
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

        return X_train, X_test, y_train, y_test

    def cross_validate(self) -> Tuple[float, float, List[float]]:
        print("Begin cross validation")
        scores = []
        for n_fold in range(self._n_folds()):
            X_train, X_test, y_train, y_test = self.__create_samples(n_fold)
            scores.append(float(self.__run_classifier(X_train, X_test, y_train, y_test)))
        print(scores)
        return float(np.mean(scores)), float(np.std(scores)), scores

    def __run_classifier(self, X_train, X_test, y_train, y_test) -> float:
        params = self.config.params()
        model = RandomForestClassifier(**params)
        model.fit(X_train, y_train)
        predictions = model.predict(X_test)
        return accuracy_score(y_test, predictions)

from math import ceil
from typing import Tuple

import numpy as np

from classifier.config import Config
from data_processing.PathMinerDataset import PathMinerDataset
from data_processing.PathMinerLoader import PathMinerLoader


class BaseClassifier:

    def __init__(self, config: Config):
        self.config = config
        self.__fix_random()
        self._loader = self.__get_loader(config.source_folder())
        self.__indices_per_class, self._n_classes = self.__split_into_classes(self._loader)
        self.update_chosen_classes()

    def __fix_random(self):
        np.random.seed(self.config.seed())
        self.__seed = self.config.seed()

    def __get_loader(self, folder: str) -> PathMinerLoader:
        print("Waiting for loader")
        return PathMinerLoader.from_folder(folder, transform=self.__label_contexts,
                                           use_explicit_features=self.config.use_explicit_features())

    # Add labels with project info to path contexts.
    def __label_contexts(self, project_paths: np.ndarray) -> np.ndarray:
        project_paths = np.array(list(map(lambda path: path.split('/')[self.config.label_position()], project_paths)),
                                 dtype=np.object)
        projects, labels, counts = np.unique(project_paths, return_inverse=True, return_counts=True)
        return labels

    def __split_into_classes(self, loader: PathMinerLoader) -> Tuple[np.ndarray, int]:
        print("Splitting into classes")
        index = loader.labels()
        n_classes = np.unique(index).size
        indices_per_class = [[] for _ in range(n_classes)]
        for i, ind in enumerate(index):
            indices_per_class[ind].append(i)
        indices_per_class = np.array([np.array(inds, dtype=np.int32) for inds in indices_per_class])
        # for k in range(n_classes):
        #     np.random.shuffle(indices_per_class[k])
        return indices_per_class, n_classes

    def update_chosen_classes(self):
        chosen_classes = np.random.choice(self._n_classes, self.config.n_classes(), replace=False) \
            if self.config.n_classes() is not None \
            else np.arange(self._n_classes)
        self.__chosen_classes = chosen_classes

    # Create training and validation dataset.
    def _split_train_test(self, loader: PathMinerLoader, fold_ind: int, pad: bool = False) \
            -> Tuple[PathMinerDataset, PathMinerDataset]:
        chosen_classes = self.__chosen_classes
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

        return PathMinerDataset.from_loader(loader, np.array(train_indices, dtype=np.int32), pad,
                                            use_explicit_features=self.config.use_explicit_features()), \
               PathMinerDataset.from_loader(loader, np.array(test_indices, dtype=np.int32), pad,
                                            use_explicit_features=self.config.use_explicit_features())

    def _n_folds(self) -> int:
        test_size = self.config.test_size()
        if isinstance(test_size, float):
            return int(np.ceil(1. / test_size))
        else:
            return int(np.ceil(max([inds.size for inds in self.__indices_per_class]) / test_size))

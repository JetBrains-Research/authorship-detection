import os
from math import ceil
from typing import Tuple

import numpy as np
import pandas as pd

from classifier.config import Config
from data_processing.PathMinerDataset import PathMinerDataset
from data_processing.PathMinerLoader import PathMinerLoader


class BaseClassifier:

    def __init__(self, config: Config):
        self.config = config
        self.__fix_random()
        if config.split_folder() is not None:
            self._loader = self._get_timesplit_loader()
        elif config.contextsplit_depth() is not None:
            self._loader = self._get_contextsplit_loader()
            self._train_indices_depth = []
            self._test_indices_depth = []
            for k in range(config.contextsplit_depth()):
                self._train_indices_depth.append(pd.read_csv(
                    os.path.join(self.config.change_ids_folder(), f'train_at_{k+1}.csv'),
                    index_col=0,
                    squeeze=True
                ))
                self._test_indices_depth.append(pd.read_csv(
                    os.path.join(self.config.change_ids_folder(), f'test_at_{k+1}.csv'),
                    index_col=0,
                    squeeze=True
                ))
        else:
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

    def _get_timesplit_loader(self) -> PathMinerLoader:
        print("Waiting for loader")
        return PathMinerLoader.from_timesplit(self.config.source_folder(), self.config.split_folder(),
                                              list(range(1, self.config.time_folds() + 1)), self.config.n_classes())

    def _get_contextsplit_loader(self) -> PathMinerLoader:
        print("Waiting for loader")
        return PathMinerLoader.from_contextsplit(self.config.source_folder(), self.config.entities_folder(),
                                                 self.config.entities())

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

    def _context_split(self, loader: PathMinerLoader, depth: int, random: bool = False, pad: bool = False):
        print("Splitting indices")
        train_indices, test_indices = [], []
        for ind, change_id in enumerate(loader.change_ids()):
            if self._train_indices_depth[depth][change_id] == 1:
                train_indices.append(ind)
            if self._test_indices_depth[depth][change_id] == 1:
                test_indices.append(ind)
        if random:
            all_indices = np.array(train_indices + test_indices)
            n_train = len(train_indices)
            np.random.shuffle(all_indices)
            train_indices = all_indices[:n_train]
            test_indices = all_indices[n_train:]

        print(f'train: {len(train_indices)}, test: {len(test_indices)}')
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

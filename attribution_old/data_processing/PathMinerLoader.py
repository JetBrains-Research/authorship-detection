from os import path
from os.path import isfile
from typing import Tuple, Callable, Union, List

import numpy as np
import pandas as pd
import parse

from data_processing.UtilityEntities import PathContext, Path, NodeType, PathContexts, concat_path_contexts


# Loads all .csv files generated by AstMiner in np.ndarray
class PathMinerLoader:

    def __init__(self,
                 tokens_file: str, paths_file: str, node_types_file: str,
                 path_contexts_file: Union[str, List[List[str]]],
                 transform: Callable = None, explicit_features_files: str = None):
        self._tokens = self._load_tokens(tokens_file)
        self._node_types = self._load_node_types(node_types_file)
        self._paths = self._load_paths(paths_file)
        if type(path_contexts_file) is str:
            self._indices, self._path_contexts = self._load_path_contexts_file(path_contexts_file)
            self._labels = transform(self._indices) if transform is not None \
                else self._indices
            self._n_classes = np.unique(self._labels).size
        else:
            self._n_splits = len(path_contexts_file)
            self._n_classes = len(path_contexts_file[0])
            self._path_contexts = []
            self._indices = []
            self._change_ids = []
            for time_fold, fold_files in enumerate(path_contexts_file):
                indices, path_contexts, change_ids = [], [], []
                for i, pc_file in enumerate(fold_files):
                    num, pcs, cis = self._load_timesplit_file(pc_file)
                    indices.append(np.full(num, i, dtype=np.int32))
                    change_ids.append(cis)
                    path_contexts.append(pcs)

                indices = np.concatenate(indices, axis=0)
                change_ids = np.concatenate(change_ids, axis=0)
                path_contexts = concat_path_contexts(path_contexts)
                self._indices.append(indices)
                self._path_contexts.append(path_contexts)
                self._change_ids.append(change_ids)

            if len(self._indices) == 1:
                self._indices = self._indices[0]
                self._path_contexts = self._path_contexts[0]
                self._change_ids = self._change_ids[0]

            print(self._change_ids)
            self._labels = self._indices

        self._use_explicit_features = explicit_features_files is not None
        if self._use_explicit_features:
            # features_file, paths_file = explicit_features_files
            # self._explicit_features = self._load_explicit_features(features_file, paths_file)
            self._explicit_features = self._load_explicit_features(explicit_features_files)
        else:
            self._explicit_features = None

    @classmethod
    def from_folder(cls, dataset_folder: str, transform: Callable = None, use_explicit_features: bool = False):
        # explicit_features_files = (path.join(dataset_folder, 'extracted_features.npy'),
        #                            path.join(dataset_folder, 'paths_updated.npy')) if use_explicit_features else None
        explicit_features_files = path.join(dataset_folder, 'explicit.csv') if use_explicit_features else None
        return cls(path.join(dataset_folder, 'tokens.csv'),
                   path.join(dataset_folder, 'paths.csv'),
                   path.join(dataset_folder, 'node_types.csv'),
                   path.join(dataset_folder, 'path_contexts.csv'),
                   transform,
                   explicit_features_files=explicit_features_files)

    @classmethod
    def from_timesplit(cls, dataset_folder: str, splits_folder: str, time_folds: List[int], n_classes: int):
        return cls(path.join(dataset_folder, 'tokens.csv'),
                   path.join(dataset_folder, 'paths.csv'),
                   path.join(dataset_folder, 'node_types.csv'),
                   [[path.join(splits_folder, f'stamp_{time_fold:02d}/author_{author + 1:03d}.csv')
                     for author in range(n_classes)] for time_fold in time_folds]
                   )

    @classmethod
    def from_contextsplit(cls, dataset_folder: str, entities_folder: str, entities: List[int]):
        return cls(path.join(dataset_folder, 'tokens.csv'),
                   path.join(dataset_folder, 'paths.csv'),
                   path.join(dataset_folder, 'node_types.csv'),
                   [[path.join(entities_folder, f'changes_{entity}.csv') for entity in entities]]
                   )

    # Token loading is slightly complex because tokens can contain any symbols
    def _load_tokens(self, tokens_file: str) -> np.ndarray:
        fstring = '{:d},{}'
        with open(tokens_file, 'r') as f:
            indices = []
            tokens = []
            for line in f.readlines()[1:]:
                parsed = parse.parse(fstring, line)
                if parsed is not None:
                    ind, token = parsed
                    indices.append(ind)
                    tokens.append(token)
                else:
                    tokens[-1] += line

        # Remove line breaks at the end of tokens
        # for i in range(len(tokens)):
        #     tokens[i] = tokens[i][:-1]

        return self._series_to_ndarray(indices, tokens)

    def _load_paths(self, paths_file: str) -> np.ndarray:
        paths = pd.read_csv(paths_file, sep=',', index_col=0, squeeze=True)
        paths = paths.map(Path.fromstring)
        return self._series_to_ndarray(paths.index, paths.values)

    def _load_node_types(self, node_types_file: str) -> np.ndarray:
        node_types = pd.read_csv(node_types_file, sep=',', index_col=0, squeeze=True)
        node_types = node_types.map(NodeType.fromstring)
        return self._series_to_ndarray(node_types.index, node_types.values)

    @staticmethod
    def _load_path_contexts_file(path_contexts_file) -> Tuple[np.ndarray, PathContexts]:
        contexts = pd.read_csv(path_contexts_file, sep=',')
        path_contexts = contexts.path_contexts.fillna('').map(
            lambda ctx: np.array(list(map(PathContext.fromstring, ctx.split(';'))), dtype=np.object)
            if ctx
            else np.array([])
        )
        starts = path_contexts.map(
            lambda ctx_array: np.fromiter(map(lambda ctx: ctx.start_token, ctx_array), np.int32, count=ctx_array.size)
        ).values

        paths = path_contexts.map(
            lambda ctx_array: np.fromiter(map(lambda ctx: ctx.path, ctx_array), np.int32, count=ctx_array.size)
        ).values

        ends = path_contexts.map(
            lambda ctx_array: np.fromiter(map(lambda ctx: ctx.end_token, ctx_array), np.int32, count=ctx_array.size)
        ).values

        labels = contexts.id.values
        # print(labels)
        # print(starts, ends)
        return labels, PathContexts(starts, paths, ends)

    @staticmethod
    def _load_timesplit_file(timesplit_file) \
            -> Union[Tuple[int, PathContexts], Tuple[int, PathContexts, np.ndarray]]:
        if not isfile(timesplit_file):
            return 0, PathContexts([], [], []), np.array([])

        contexts = pd.read_csv(timesplit_file, sep=',')
        path_contexts = contexts.pathsAfter.fillna('').map(
            lambda ctx: np.array(list(map(PathContext.fromstring, ctx.split(';'))), dtype=np.object)
            if ctx
            else np.array([])
        )
        starts = path_contexts.map(
            lambda ctx_array: np.fromiter(map(lambda ctx: ctx.start_token, ctx_array), np.int32, count=ctx_array.size)
        ).values

        paths = path_contexts.map(
            lambda ctx_array: np.fromiter(map(lambda ctx: ctx.path, ctx_array), np.int32, count=ctx_array.size)
        ).values

        ends = path_contexts.map(
            lambda ctx_array: np.fromiter(map(lambda ctx: ctx.end_token, ctx_array), np.int32, count=ctx_array.size)
        ).values
        change_ids = contexts.changeId
        return len(path_contexts), PathContexts(starts, paths, ends), change_ids.values

    @staticmethod
    def _series_to_ndarray(indices, values) -> np.ndarray:
        converted_values = np.empty(max(indices) + 1, dtype=np.object)
        for ind, val in zip(indices, values):
            converted_values[ind] = val
        return converted_values

    # def _load_explicit_features(self, features_file: str, paths_file: str) -> np.ndarray:
    #     features = np.load(features_file)
    #     paths = np.load(paths_file)
    #     inds = {path: k for k, path in enumerate(paths)}
    #     return np.array([features[inds[ind.replace(' ', '_')]] for ind in self._indices])

    def _load_explicit_features(self, features_file: str) -> np.ndarray:
        features = pd.read_csv(features_file)
        inds = {path: k for k, path in zip(features.index, features['project'])}
        features = features.drop('project', axis=1)
        return np.array([features.iloc[inds[ind]].values for ind in self._indices])

    def tokens(self) -> np.ndarray:
        return self._tokens

    def paths(self) -> np.ndarray:
        return self._paths

    def node_types(self) -> np.ndarray:
        return self._node_types

    def labels(self) -> Union[np.ndarray, List[np.ndarray]]:
        return self._labels

    def path_contexts(self) -> Union[PathContexts, List[PathContexts]]:
        return self._path_contexts

    def n_classes(self) -> np.int32:
        return self._n_classes

    def explicit_features(self) -> np.ndarray:
        return self._explicit_features

    def change_ids(self) -> np.ndarray:
        return self._change_ids
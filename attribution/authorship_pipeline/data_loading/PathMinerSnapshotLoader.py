import pandas as pd
import numpy as np
import os

from typing import Tuple
from sklearn.preprocessing import LabelEncoder

from data_loading.UtilityEntities import Path, NodeType, PathContexts, PathContext
from util import ProcessedSnapshotFolder


class PathMinerSnapshotLoader:

    def __init__(self, project_folder: ProcessedSnapshotFolder):
        self._tokens = self._load_tokens(project_folder.tokens_file)
        self._node_types = self._load_node_types(project_folder.node_types_file)
        self._paths = self._load_paths(project_folder.paths_file)
        self._original_labels, self._path_contexts = self._load_path_contexts_files(project_folder.path_contexts_file)
        self._original_labels, self._labels = self.enumerate_labels(self._original_labels)

        entities, counts = np.unique(self._labels, return_counts=True)
        # ec = [(c, e) for e, c in zip(entities, counts)]
        # for i, (c, e) in enumerate(sorted(ec)):
        #     print(f'{i}: {e} -> {c} | {c / len(self._labels):.4f}')
        self._n_classes = len(entities)

    def _load_tokens(self, tokens_file: str) -> np.ndarray:
        # return self._series_to_ndarray(
        #     pd.read_csv(tokens_file, sep=',', index_col='id', usecols=['id', 'token'], squeeze=True)
        # )
        tokens = self._load_stub(tokens_file, 'token')
        return self._series_to_ndarray(tokens)

    def _load_paths(self, paths_file: str) -> np.ndarray:
        # paths = pd.read_csv(paths_file, sep=',', index_col='id', usecols=['id', 'path'], squeeze=True)
        paths = self._load_stub(paths_file, 'path')
        paths = paths.map(
            lambda nt: Path(
                list(map(int, nt.split()))
            )
        )
        return self._series_to_ndarray(paths)

    def _load_node_types(self, node_types_file: str) -> np.ndarray:
        # node_types = pd.read_csv(node_types_file, sep=',', index_col='id', usecols=['id', 'node_type'], squeeze=True)
        node_types = self._load_stub(node_types_file, 'node_type')
        node_types = node_types.map(lambda nt: NodeType(*nt.split()))
        return self._series_to_ndarray(node_types)

    @staticmethod
    def _load_stub(filename: str, col_name: str) -> pd.Series:
        df = pd.read_csv(filename, sep=',')
        df = df.set_index('id')
        return df[col_name]

    @staticmethod
    def _load_path_contexts_files(path_contexts_file: str) -> Tuple[np.ndarray, PathContexts]:

        raw_data = [line.strip().split(' ', 1) for line in open(path_contexts_file, 'r').readlines()]

        labels = np.array([d[0] for d in raw_data if len(d) == 2])
        raw_contexts = [d[1] for d in raw_data if len(d) == 2]

        path_contexts = [
            np.array(list(map(
                lambda ctx: PathContext.fromstring(ctx, sep=','),
                raw_context.split(' ')
            )), dtype=np.object)
            for raw_context in raw_contexts
        ]

        starts = np.array(list(map(
            lambda ctx_array: np.fromiter(map(lambda ctx: ctx.start_token, ctx_array), np.int32,
                                          count=ctx_array.size),
            path_contexts
        )), dtype=np.object)

        paths = np.array(list(map(
            lambda ctx_array: np.fromiter(map(lambda ctx: ctx.path, ctx_array), np.int32, count=ctx_array.size),
            path_contexts
        )), dtype=np.object)

        ends = np.array(list(map(
            lambda ctx_array: np.fromiter(map(lambda ctx: ctx.end_token, ctx_array), np.int32, count=ctx_array.size),
            path_contexts
        )), dtype=np.object)

        return labels, PathContexts(starts, paths, ends)

    @staticmethod
    def enumerate_labels(labels: np.ndarray) -> Tuple[np.ndarray, np.ndarray]:
        label_encoder = LabelEncoder()
        label_encoder.fit(labels)
        return label_encoder.classes_, label_encoder.transform(labels)

    @staticmethod
    def _series_to_ndarray(series: pd.Series) -> np.ndarray:
        converted_values = np.empty(max(series.index) + 1, dtype=np.object)
        for ind, val in zip(series.index, series.values):
            converted_values[ind] = val
        return converted_values

    def tokens(self) -> np.ndarray:
        return self._tokens

    def paths(self) -> np.ndarray:
        return self._paths

    def node_types(self) -> np.ndarray:
        return self._node_types

    def original_labels(self) -> np.ndarray:
        return self._original_labels

    def labels(self) -> np.ndarray:
        return self._labels

    def path_contexts(self) -> PathContexts:
        return self._path_contexts

    def n_classes(self) -> int:
        return self._n_classes

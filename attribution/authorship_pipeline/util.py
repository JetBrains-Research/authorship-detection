import os

import pandas as pd


class ProcessedFolder:
    def __init__(self, folder: str):
        self.folder = folder
        self.generated_folder = os.path.join(folder, "generated_data")
        if not os.path.exists(self.generated_folder):
            os.mkdir(self.generated_folder)

        self.change_metadata_file = os.path.join(folder, "change_metadata.csv")
        self.method_ids_file = os.path.join(folder, "method_ids.csv")
        self.node_types_file = os.path.join(folder, "node_types.csv")
        self.path_ids_file = os.path.join(folder, "path_ids.csv")
        self.tokens_file = os.path.join(folder, "tokens.csv")
        self.entity_dict = os.path.join(self.generated_folder, "entity_dict.pkl")
        self.reversed_entity_dict = os.path.join(self.generated_folder, "reversed_entity_dict.pkl")
        self.resolved_entities = os.path.join(self.generated_folder, "resolved_entities.csv")
        self.author_occurrences = os.path.join(self.generated_folder, "author_occurrences.pkl")
        self.change_occurrences = os.path.join(self.generated_folder, "change_occurrences.pkl")
        self.author_to_changes = os.path.join(self.generated_folder, "author_to_changes.pkl")
        self.unknown_entities = os.path.join(self.generated_folder, "unknown_entities.txt")
        self.readable_entities = os.path.join(self.generated_folder, "readable_entities.csv")
        self.file_changes = [os.path.join(folder, f) for f in os.listdir(folder) if f.startswith("file_changes")]

        self.code_folder = os.path.join(self.folder, 'out_code')

        def list_folder(code_folder):
            return sorted([os.path.join(code_folder, f) for f in os.listdir(code_folder)])

        self.modifications_folder = os.path.join(self.code_folder, 'modifications')
        self.modifications_files = list_folder(self.modifications_folder)
        self.creations_folder = os.path.join(self.code_folder, 'creations')
        self.creations_files = list_folder(self.creations_folder)
        self.deletions_folder = os.path.join(self.code_folder, 'deletions')
        self.deletions_files = list_folder(self.deletions_folder)
        self.caliskan_dataset = os.path.join(self.generated_folder, 'caliskan_dataset.pkl')
        self.caliskan_mutual_info = os.path.join(self.generated_folder, 'caliskan_mutual_info.pkl')

        self._time_buckets_split = "time_buckets_split_{}.pkl"
        self._time_buckets_range = "time_buckets_range_{}.csv"
        self._context_split = "context_split_{}_{}.pkl"
        self._n_tokens = None
        self._n_paths = None

    def time_buckets_split(self, n_buckets: int) -> str:
        return os.path.join(self.generated_folder, self._time_buckets_split.format(n_buckets))

    def time_buckets_range(self, n_buckets: int) -> str:
        return os.path.join(self.generated_folder, self._time_buckets_range.format(n_buckets))

    def context_split(self, min_train: float, max_train: float):
        return os.path.join(self.generated_folder,
                            self._context_split.format(int(min_train * 100), int(max_train * 100)))

    def n_tokens(self) -> int:
        if self._n_tokens is None:
            tokens = pd.read_csv(self.tokens_file, index_col=0)
            self._n_tokens = len(tokens)
        return self._n_tokens

    def n_paths(self) -> int:
        if self._n_paths is None:
            paths = pd.read_csv(self.path_ids_file, index_col=0)
            self._n_paths = len(paths)
        return self._n_paths


class ProcessedSnapshotFolder:
    def __init__(self, folder: str):
        self.folder = folder
        self.node_types_file = os.path.join(folder, "node_types.csv")
        self.paths_file = os.path.join(folder, "paths.csv")
        self.tokens_file = os.path.join(folder, "tokens.csv")
        self.path_contexts_file = os.path.join(folder, "path_contexts.csv")

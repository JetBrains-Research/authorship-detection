import os
import pathlib
import pickle
from argparse import ArgumentParser
from typing import Tuple

import numpy as np
from joblib import Parallel, delayed
from scipy.sparse import csc_matrix
from sklearn.feature_selection import mutual_info_classif
from tqdm import tqdm

from caliskan.features import calculate_features_for_files, build_dataset
from preprocessing.resolve_entities import resolve_entities
from util import ProcessedFolder


class CaliskanDataset:
    def __init__(self, feature_values: csc_matrix, feature_names: np.ndarray, files: np.ndarray, change_ids: np.ndarray,
                 authors: np.ndarray):
        self.feature_values = feature_values
        self.feature_names = np.array(feature_names)
        self.files = files
        self.change_ids = change_ids
        self.authors = authors

    def limit_features(self, mutual_information: np.ndarray, n_features: int):
        indices = np.argsort(mutual_information)[-n_features:]
        self.feature_values = self.feature_values[:, indices]
        self.feature_names = self.feature_names[indices]

    def filter_authors(self, min_max_count: Tuple[int, int]):
        author, count = np.unique(self.authors, return_counts=True)
        author_count = {a: c for a, c in zip(author, count)}
        indices = [i for i, a in enumerate(self.authors) if min_max_count[0] <= author_count[a] <= min_max_count[1]]
        self.feature_values = self.feature_values[indices, :]
        self.files = self.files[indices]
        self.change_ids = self.change_ids[indices]
        self.authors = self.authors[indices]


def compute_caliskan_features(processed_folder: ProcessedFolder) -> Tuple[CaliskanDataset, np.ndarray]:
    if os.path.exists(processed_folder.caliskan_dataset) and \
            os.path.exists(processed_folder.caliskan_mutual_info):
        print("Loading precomputed features and mutual info")
        dataset = pickle.load(open(processed_folder.caliskan_dataset, 'rb'))
        mutual_info = pickle.load(open(processed_folder.caliskan_mutual_info, 'rb'))
        return dataset, mutual_info

    files = processed_folder.creations_files

    print(f"Extracting features from {len(files)} files")
    files, features = calculate_features_for_files(files)

    print("Building dataset")
    feature_values, feature_names = build_dataset(features)
    change_entities = resolve_entities(processed_folder)
    dataset = CaliskanDataset(
        feature_values,
        np.array(feature_names),
        np.array(files),
        np.array(list(map(lambda path: int(pathlib.Path(path).name.split('_')[0]), files))),
        np.array(list(map(lambda path: change_entities[int(pathlib.Path(path).name.split('_')[0])], files)))
    )
    print("Computing mutual info")
    print(feature_values.shape)

    with Parallel(n_jobs=-1) as pool:
        part_size = 1000
        m = dataset.feature_values.shape[1]
        mutual_info_parts = pool(
            delayed(mutual_info_classif)(dataset.feature_values[:, i:i + part_size], dataset.authors, random_state=0)
            for i in tqdm(range(0, m, part_size))
        )
    mutual_info = np.concatenate(mutual_info_parts)
    mutual_info /= np.max(mutual_info)

    pickle.dump(dataset, open(processed_folder.caliskan_dataset, 'wb'))
    pickle.dump(mutual_info, open(processed_folder.caliskan_mutual_info, 'wb'))
    print("Extracted data dumped on disk")

    return dataset, mutual_info


if __name__ == '__main__':
    parser = ArgumentParser()
    parser.add_argument("--data_folder", type=str, required=True)
    args = parser.parse_args()
    compute_caliskan_features(ProcessedFolder(args.data_folder))

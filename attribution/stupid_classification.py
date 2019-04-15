import os
import numpy as np
import sortednp as snp
import argparse
import torch
from typing import List

from scipy.sparse import csr_matrix
from sklearn.feature_selection import mutual_info_classif

from data_processing.PathMinerDataset import PathMinerDataset
from data_processing.PathMinerLoader import PathMinerLoader
from util import timeit

SEED = 239


# Add labels with project info to path contexts.
def label_contexts(project_paths):
    project_paths = np.array(list(map(lambda path: path.split('/')[4], project_paths)), dtype=np.object)
    projects, labels, counts = np.unique(project_paths, return_inverse=True, return_counts=True)
    return labels


@timeit
def get_loader(folder):
    return PathMinerLoader.from_folder(folder, transform=label_contexts)


# Create training and validation dataset from path contexts.
@timeit
def split_train_test(loader: PathMinerLoader, batch_num: int, n_persons: int = None, debug: bool = True):
    index = loader.labels()
    n_classes = np.unique(index).size
    batch_per_class = np.zeros(n_classes, dtype=np.int32)
    train_indices = []
    test_indices = []
    for i, ind in enumerate(index):
        if n_persons is not None and ind >= n_persons:
            continue
        batch_per_class[ind] += 1
        if (isinstance(batch_num, int) and batch_per_class[ind] == batch_num) or \
                (isinstance(batch_num, List) and batch_per_class[ind] in batch_num):
            test_indices.append(i)
        else:
            train_indices.append(i)
    if debug:
        print(len(train_indices), len(test_indices))
    return PathMinerDataset.from_loader(loader, np.array(train_indices, dtype=np.int32), False), \
           PathMinerDataset.from_loader(loader, np.array(test_indices, dtype=np.int32), False)


@timeit
def build_sparse_path_matrix(dataset: PathMinerDataset,
                             n_paths: int = None, valid_paths: np.ndarray = None, debug: bool = True) -> csr_matrix:

    if n_paths is None and valid_paths is None:
        raise ValueError('Either n_paths or valid_paths should be passed')
    if valid_paths is not None:
        n_paths = valid_paths.size
        valid_paths = np.sort(valid_paths)
        if debug:
            print(n_paths)
    data = []
    row_ind, col_ind = [], []
    for i, item in enumerate(dataset):
        path_inds, path_counts = np.unique(item['paths'], return_counts=True)
        if valid_paths is not None:
            _, intersection_inds = snp.intersect(path_inds, valid_paths, indices=True)
            path_inds = intersection_inds[1]
            path_counts = path_counts[intersection_inds[0]]

        for path_ind, path_count in zip(path_inds, path_counts):
            if valid_paths is not None and path_ind not in valid_paths:
                continue
            data.append(path_count)
            row_ind.append(i)
            col_ind.append(path_ind)
    return csr_matrix((data, (row_ind, col_ind)), shape=(len(dataset), n_paths))


@timeit
def score_mutual_information(bag_of_paths: csr_matrix, labels: np.ndarray):
    scores = mutual_info_classif(bag_of_paths, labels, discrete_features=True, random_state=SEED)
    for score, cnt in zip(*np.unique(scores, return_counts=True)):
        print(score, cnt)
    scores.tofile(f'saved_scores.numpy')
    return scores


def load_feature_scores(train_dataset: PathMinerDataset, n_paths: int, debug: bool = True):
    if os.path.isfile(f'saved_scores.numpy'):
        if debug:
            print("Loading scores from file")
        return np.fromfile(f'saved_scores.numpy')
    else:
        if debug:
            print("Building sparse matrix of paths")
        bag_of_paths = build_sparse_path_matrix(train_dataset, n_paths)
        if debug:
            print("Computing mutual information scores")
        return score_mutual_information(bag_of_paths, train_dataset.labels())


def top_scores_inds(scores: np.ndarray, limit: int):
    sorted_args = np.flip(np.argsort(scores), axis=0)
    if scores.size <= limit:
        return sorted_args
    else:
        return sorted_args[:limit]


def create_samples(args, loader: PathMinerLoader = None, debug: False = True):
    if debug:
        print("Loading generated data")
    if loader is None:
        loader = get_loader(args.source_folder)
    n_paths = loader.paths().size
    if debug:
        print("Creating datasets")
    train_dataset, test_dataset = split_train_test(loader, args.fold, args.n_persons, debug=debug)
    scores = load_feature_scores(train_dataset, n_paths, debug=debug)
    valid_paths = top_scores_inds(scores, args.top_paths)
    if debug:
        print("Building train matrix")
    X_train = build_sparse_path_matrix(train_dataset, valid_paths=valid_paths, debug=debug)
    y_train = train_dataset.labels()
    if debug:
        print("Building test matrix")
    X_test = build_sparse_path_matrix(test_dataset, valid_paths=valid_paths, debug=debug)
    y_test = test_dataset.labels()
    return X_train, X_test, y_train, y_test


def main(args):
    X_train, X_test, y_train, y_test = create_samples(args)
    print(X_train.shape)
    print(X_test.shape)
    print(y_train.shape)
    print(y_test.shape)


if __name__ == '__main__':
    np.random.seed(SEED)
    torch.manual_seed(SEED)

    parser = argparse.ArgumentParser()
    parser.add_argument('--source_folder', type=str, default='processed_data/',
                        help='Folder containing output of PathMiner')
    parser.add_argument('--top_paths', type=int, default=500, help='Top paths by mutual information to use')
    parser.add_argument('--folds', nargs='+', type=int, default=1, help='Fold number')
    parser.add_argument('--n_persons', type=int, default=None, help='Number of classes')

    args = parser.parse_args()
    main(args)

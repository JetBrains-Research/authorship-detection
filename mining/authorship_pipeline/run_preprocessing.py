from argparse import ArgumentParser

import numpy as np

from preprocessing.compute_occurrences import compute_occurrences
from preprocessing.context_split import context_split
from preprocessing.merge_aliases_bipartite import merge_aliases_bipartite
from preprocessing.time_split import time_split
from util import ProcessedFolder


def fix_seed(seed: int):
    np.random.seed(seed)


def process_folder(project_folder: ProcessedFolder, n_time_buckets: int, min_context_train: float,
                   max_context_train: float, min_count: int):
    merge_aliases_bipartite(project_folder)
    compute_occurrences(project_folder)
    time_split(project_folder, n_time_buckets, uniform_distribution=True)
    context_split(project_folder, min_train=min_context_train, max_train=max_context_train, min_count=min_count)


def run_preprocessing(n_time_buckets: int, min_context_train: float, max_context_train: float, min_count: int,
                      random_seed: int = 239, projects_file: str = None, project_folder: str = None):
    fix_seed(random_seed)
    if project_folder is not None:
        process_folder(ProcessedFolder(project_folder), n_time_buckets,
                       min_context_train, max_context_train, min_count)
    elif projects_file is not None:
        projects = [l.strip() for l in open(projects_file, "r").readlines()]
        for p in projects:
            process_folder(ProcessedFolder("../gitminer/out/" + p + "/"), n_time_buckets,
                           min_context_train, max_context_train, min_count)
    else:
        raise ValueError("One of projects folder or projects file should be set")


if __name__ == '__main__':
    parser = ArgumentParser()
    parser.add_argument("--n_time_buckets", type=int, required=True)
    parser.add_argument("--min_context_train", type=float, default=0.7)
    parser.add_argument("--max_context_train", type=float, default=0.8)
    parser.add_argument("--min_count", type=int, default=100)
    parser.add_argument("--random_seed", type=int, default=239)
    parser.add_argument("--projects_file", type=str, default="../projects.txt")
    parser.add_argument("--project_folder", type=str)
    args = parser.parse_args()

    run_preprocessing(args.n_time_buckets, args.min_context_train, args.max_context_train, args.min_count,
                      args.random_seed, args.projects_file, args.project_folder)

from argparse import ArgumentParser

import numpy as np

from preprocessing.compute_occurrences import compute_occurrences
from preprocessing.merge_aliases_bipartite import merge_aliases_bipartite
from preprocessing.time_split import time_split
from preprocessing.util import ProcessedFolder


def fix_seed(seed: int):
    np.random.seed(seed)


def process_folder(project_folder: ProcessedFolder, n_time_buckets: int):
    merge_aliases_bipartite(project_folder)
    compute_occurrences(project_folder)
    time_split(project_folder, n_time_buckets, uniform_distribution=True)


def run_preprocessing(n_time_buckets: int, random_seed: int = 239, projects_file: str = None,
                      project_folder: str = None):
    fix_seed(random_seed)
    if project_folder is not None:
        process_folder(ProcessedFolder(project_folder), n_time_buckets)
    elif projects_file is not None:
        projects = [l.strip() for l in open(projects_file, "r").readlines()]
        for p in projects:
            process_folder(ProcessedFolder("../gitminer/out/" + p + "/"), n_time_buckets)
    else:
        raise ValueError("One of projects folder or projects file should be set")


if __name__ == '__main__':
    parser = ArgumentParser()
    parser.add_argument("--n_time_buckets", type=int, required=True)
    parser.add_argument("--random_seed", type=int, default=239)
    parser.add_argument("--projects_file", type=str, default="../projects.txt")
    parser.add_argument("--project_folder", type=str)
    args = parser.parse_args()

    run_preprocessing(args.n_time_buckets, args.random_seed, args.projects_file, args.project_folder)

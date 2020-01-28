import os
import pickle
from argparse import ArgumentParser
from collections import Counter
from datetime import datetime
from typing import Dict, Tuple

import pandas as pd

from preprocessing.compute_occurrences import compute_occurrences
from preprocessing.resolve_entities import resolve_entities
from util import ProcessedFolder


def continuous_distribution(change_metadata: pd.DataFrame, change_occurrences: Counter, n_time_buckets: int,
                            total_count: int) -> Tuple[Dict, pd.DataFrame]:
    bucket_size = total_count // n_time_buckets + 1
    change_to_time_bucket = {}
    cur_changes = 0
    cur_bucket = 0

    bucket_indices = [i for i in range(1, n_time_buckets + 1)]
    bucket_start_times = [0 for _ in range(n_time_buckets)]
    bucket_finish_times = [0 for _ in range(n_time_buckets)]

    for change_id in change_metadata.index:
        cur_changes += change_occurrences[change_id]
        change_to_time_bucket[change_id] = cur_bucket

        if bucket_start_times[cur_bucket] is None:
            bucket_start_times[cur_bucket] = change_metadata.loc[change_id]
        bucket_finish_times[cur_bucket] = change_metadata.loc[change_id]

        while cur_changes >= bucket_size:
            cur_bucket += 1
            cur_changes -= bucket_size

    bucket_to_timestamps = pd.DataFrame(data={
        'start_time': bucket_start_times,
        'finish_time': bucket_finish_times,
        'bucket_ind': bucket_indices
    })
    bucket_to_timestamps['start_date'] = bucket_to_timestamps['start_time'].map(
        lambda tstamp: datetime.fromtimestamp(tstamp)
    )
    bucket_to_timestamps['finish_date'] = bucket_to_timestamps['finish_time'].map(
        lambda tstamp: datetime.fromtimestamp(tstamp)
    )
    return change_to_time_bucket, bucket_to_timestamps


def uni_distribution(author_occurrences: Counter, change_metadata: pd.DataFrame, change_occurrences: Counter,
                     n_time_buckets: int, change_entities: pd.Series) -> Tuple[Dict, pd.DataFrame]:
    change_to_time_bucket = {}

    entity_bucket_size = {author: count // n_time_buckets + 1 for author, count in author_occurrences.items()}

    bucket_start_times = {author: [None for _ in range(n_time_buckets)] for author, _ in author_occurrences.items()}
    bucket_finish_times = {author: [None for _ in range(n_time_buckets)] for author, _ in author_occurrences.items()}

    entity_cur_counts = Counter()

    for change_id in change_metadata.index:
        if change_occurrences[change_id] == 0:
            continue

        entity = change_entities[change_id]
        bucket = entity_cur_counts[entity] // entity_bucket_size[entity]
        change_to_time_bucket[change_id] = bucket

        if bucket_start_times[entity][bucket] is None:
            bucket_start_times[entity][bucket] = change_metadata.loc[change_id]
        bucket_finish_times[entity][bucket] = change_metadata.loc[change_id]

        entity_cur_counts[entity] += change_occurrences[change_id]

    start_time = []
    finish_time = []
    entity = []
    bucket_ind = []
    for author, _ in author_occurrences.items():
        for i in range(n_time_buckets):
            entity.append(author)
            bucket_ind.append(i)
            if bucket_start_times[author][i] is None:
                bucket_start_times[author][i] = bucket_finish_times[author][i - 1]
                bucket_finish_times[author][i] = bucket_finish_times[author][i - 1]
            start = bucket_start_times[author][i]
            finish = bucket_finish_times[author][i]
            start_time.append(start)
            finish_time.append(finish)

    bucket_to_timestamps = pd.DataFrame(data={
        'entity': entity,
        'bucket_ind': bucket_ind,
        'start_time': start_time,
        'finish_time': finish_time,
    })

    bucket_to_timestamps['start_date'] = bucket_to_timestamps['start_time'].map(
        lambda tstamp: datetime.fromtimestamp(tstamp) if pd.notna(tstamp) else tstamp
    )
    bucket_to_timestamps['finish_date'] = bucket_to_timestamps['finish_time'].map(
        lambda tstamp: datetime.fromtimestamp(tstamp) if pd.notna(tstamp) else tstamp
    )

    return change_to_time_bucket, bucket_to_timestamps


def time_split(processed_folder: ProcessedFolder, n_time_buckets: int, uniform_distribution: bool) -> Dict:
    if os.path.exists(processed_folder.time_buckets_split(n_time_buckets)):
        print("Loading split into time-separated buckets")
        return pickle.load(open(processed_folder.time_buckets_split(n_time_buckets), 'rb'))

    print("Splitting into time-separated buckets")
    change_metadata = pd.read_csv(
        processed_folder.change_metadata_file,
        index_col="id",
        usecols=["id", "authorTime"],
        squeeze=True
    )
    change_metadata.sort_values(inplace=True)

    author_occurrences, change_occurrences, author_to_changes, total_count = compute_occurrences(processed_folder)
    change_entities = resolve_entities(processed_folder)

    change_to_time_bucket, bucket_to_timestamps = \
        continuous_distribution(change_metadata, change_occurrences, n_time_buckets, total_count) \
            if not uniform_distribution else \
            uni_distribution(author_occurrences, change_metadata, change_occurrences, n_time_buckets, change_entities)

    bucket_to_timestamps.to_csv(processed_folder.time_buckets_range(n_time_buckets), index=False)

    pickle.dump(change_to_time_bucket, open(processed_folder.time_buckets_split(n_time_buckets), 'wb'))
    print("Buckets saved on disk")
    return change_to_time_bucket


if __name__ == '__main__':
    parser = ArgumentParser()
    parser.add_argument("--data_folder", type=str, required=True)
    parser.add_argument("--n_time_buckets", type=int, required=True)
    parser.add_argument("--uniform_distr", action='store_true')
    args = parser.parse_args()
    print(time_split(ProcessedFolder(args.data_folder), args.n_time_buckets, args.uniform_distr))

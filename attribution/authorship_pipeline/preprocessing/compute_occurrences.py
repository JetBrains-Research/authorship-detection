import os
import pickle
from argparse import ArgumentParser
from collections import Counter
from typing import Tuple, Dict

import pandas as pd

from preprocessing.resolve_entities import resolve_entities
from util import ProcessedFolder


def compute_occurrences(processed_folder: ProcessedFolder) -> Tuple[Counter, Counter, Dict, int]:
    if os.path.exists(processed_folder.author_occurrences) and \
            os.path.exists(processed_folder.change_occurrences) and \
            os.path.exists(processed_folder.author_to_changes):
        print("Loading precomputed occurrences")
        author_occurrences = pickle.load(open(processed_folder.author_occurrences, 'rb'))
        change_occurrences = pickle.load(open(processed_folder.change_occurrences, 'rb'))
        author_to_changes = pickle.load(open(processed_folder.author_to_changes, 'rb'))
        total_count = sum(author_occurrences.values())
        return author_occurrences, change_occurrences, author_to_changes, total_count

    print("Computing occurrences for changes and authors")
    resolved_entities = resolve_entities(processed_folder)
    author_occurrences = Counter()
    change_occurrences = Counter()
    author_to_changes = {}
    total_count = 0

    for change_file in processed_folder.file_changes:
        change_ids = pd.read_csv(change_file, usecols=["changeId", "pathsCountBefore", "pathsCountAfter"])
        for ind, row in change_ids.iterrows():
            if row['pathsCountBefore'] > 0 or row['pathsCountAfter'] == 0:
                continue

            author = resolved_entities.loc[row['changeId']]
            author_occurrences[author] += 1
            change_occurrences[row['changeId']] += 1

            if author not in author_to_changes:
                author_to_changes[author] = []
            author_to_changes[author].append(total_count)
            total_count += 1

    for i, (author, count) in enumerate(author_occurrences.most_common()):
        print(f"#{i + 1} entity: {author} -> {count}")

    pickle.dump(author_occurrences, open(processed_folder.author_occurrences, 'wb'))
    pickle.dump(change_occurrences, open(processed_folder.change_occurrences, 'wb'))
    pickle.dump(author_to_changes, open(processed_folder.author_to_changes, 'wb'))
    print("Occurrences saved on disk")
    return author_occurrences, change_occurrences, author_to_changes, total_count


if __name__ == '__main__':
    parser = ArgumentParser()
    parser.add_argument("--data_folder", type=str, required=True)
    args = parser.parse_args()
    compute_occurrences(ProcessedFolder(args.data_folder))

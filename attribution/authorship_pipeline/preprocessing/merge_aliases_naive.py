import os
import pickle
from argparse import ArgumentParser

import pandas as pd

from util import ProcessedFolder


class NaiveEntityMerger:
    def __init__(self):
        self.entity_dict = {}
        self.reverse_dict = {}
        self.entity_count = 0

    def add_entity(self, name: str, email: str):
        if (name, email) not in self.entity_dict:
            self.entity_dict[(name, email)] = self.entity_count
            self.reverse_dict[self.entity_count] = {'names': set(), 'emails': set()}
            self.entity_count += 1

        entity = self.entity_dict[(name, email)]
        self.reverse_dict[entity]['names'].add(name)
        self.reverse_dict[entity]['emails'].add(email)

    def dump(self, processed_folder: ProcessedFolder):
        pickle.dump(self.entity_dict, open(processed_folder.entity_dict, 'wb'))
        pickle.dump(self.reverse_dict, open(processed_folder.reversed_entity_dict, 'wb'))


def merge_aliases_naive(processed_folder: ProcessedFolder) -> dict:
    if os.path.exists(processed_folder.entity_dict):
        print("Loading merged entities")
        return pickle.load(open(processed_folder.entity_dict, 'rb'))

    print("Naively merging entities...")
    naive_merger = NaiveEntityMerger()
    change_metadata = pd.read_csv(
        processed_folder.change_metadata_file,
        index_col="id",
        usecols=["id", "authorName", "authorEmail"]
    )
    for index, row in change_metadata.iterrows():
        naive_merger.add_entity(row["authorName"], row["authorEmail"])
    naive_merger.dump(processed_folder)
    print("Merged entities saved on disk")

    return naive_merger.entity_dict


if __name__ == '__main__':
    parser = ArgumentParser()
    parser.add_argument("--data_folder", type=str, required=True)
    args = parser.parse_args()
    merge_aliases_naive(ProcessedFolder(args.data_folder))

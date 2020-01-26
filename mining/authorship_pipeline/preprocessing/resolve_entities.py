import os
import pickle
from argparse import ArgumentParser

import pandas as pd

from preprocessing.util import ProcessedFolder


class EntityResolver:
    def __init__(self, path_to_entity_dict: str):
        self.entity_dict = pickle.load(open(path_to_entity_dict, 'rb'))
        self.unknown_count = 0
        self.unknowns = []

    def get_entity(self, name: str, email: str) -> int:
        if (name, email) not in self.entity_dict:
            #    raise ValueError("Alias [{}, {}] was not found".format(name, email))
            self.unknown_count += 1
            self.entity_dict[(name, email)] = 100000 + self.unknown_count
            self.unknowns.append((name, email))

        return self.entity_dict[(name, email)]


def dump_unknowns(unknowns, path):
    with open(path, 'w', encoding="utf-8") as f:
        f.write("name,email\n")
        for (name, email) in unknowns:
            f.write("{},{}\n".format(name, email))
    print("Dumped " + str(len(unknowns)) + " entities to " + path)


def resolve_entities(processed_folder: ProcessedFolder) -> pd.Series:
    if os.path.exists(processed_folder.resolved_entities):
        print("Loading resolved entities")
        return pd.read_csv(processed_folder.resolved_entities, index_col=0, squeeze=True)

    if not os.path.exists(processed_folder.entity_dict):
        raise ValueError("You should provide dictionary of entities for resolving: {}"
                         .format(processed_folder.entity_dict))

    print("Resolving entities for individual changes")
    entity_resolver = EntityResolver(processed_folder.entity_dict)
    change_metadata = pd.read_csv(
        processed_folder.change_metadata_file,
        index_col="id",
        usecols=["id", "authorName", "authorEmail"]
    )
    change_entities = change_metadata.apply(
        lambda row: entity_resolver.get_entity(row["authorName"], row["authorEmail"]),
        axis=1
    )
    change_entities.to_csv(processed_folder.resolved_entities, header=True)
    print("Resolved entities saved on disk")

    print("{} unknown aliases in EntityResolver".format(entity_resolver.unknown_count))
    dump_unknowns(entity_resolver.unknowns, processed_folder.unknown_entities)

    return change_entities


if __name__ == '__main__':
    parser = ArgumentParser()
    parser.add_argument("--data_folder", type=str, required=True)
    args = parser.parse_args()
    resolve_entities(ProcessedFolder(args.data_folder))

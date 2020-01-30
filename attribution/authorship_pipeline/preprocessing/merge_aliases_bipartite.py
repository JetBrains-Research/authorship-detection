import os
import pickle
import re
from argparse import ArgumentParser
from collections import Counter

import pandas as pd

from util import ProcessedFolder


class BipartiteEntityMerger:
    def __init__(self):
        self.pairs = set()
        self.entity_dict = {}
        self.reverse_dict = {}
        self.entity_count = 0

    def add_entity(self, name: str, email: str):
        self.pairs.add((name, email))

    def run_matching(self):
        n_count = Counter()
        e_count = Counter()

        for n, e in self.pairs:
            n_count[n] += 1
            e_count[e] += 1

        for n, cnt in n_count.most_common(10):
            print(n, cnt)
        print()

        for e, cnt in e_count.most_common(10):
            print(e, cnt)
        print()

        # banned_names = [n_count.most_common()[0][0]]
        # banned_emails = [e_count.most_common()[0][0]]
        banned_names = []
        banned_emails = []
        print(banned_names, banned_emails)
        print()

        vertices = set()
        for n, _ in n_count.most_common():
            if n not in banned_names:
                vertices.add((n, 'n'))
        for e, _ in e_count.most_common():
            if e not in banned_emails:
                vertices.add((e, 'e'))

        def normalize(s):
            s = s.split('@')[0]
            s = s.lower()
            return re.sub("[^a-zA-Z0-9]+", "", s)

        edges = {v: [] for v in vertices}

        for n1, _ in n_count.most_common():
            for n2, _ in n_count.most_common():
                if n1 in banned_names or n2 in banned_names:
                    continue
                if normalize(n1) == normalize(n2):
                    edges[(n1, 'n')].append((n2, 'n'))
                    edges[(n2, 'n')].append((n1, 'n'))

        for e1, _ in e_count.most_common():
            for e2, _ in e_count.most_common():
                if e1 in banned_emails or e2 in banned_emails:
                    continue
                if normalize(e1) == normalize(e2):
                    edges[(e1, 'e')].append((e2, 'e'))
                    edges[(e2, 'e')].append((e1, 'e'))

        for n, e in self.pairs:
            if n not in banned_names and e not in banned_emails:
                edges[(n, 'n')].append((e, 'e'))
                edges[(e, 'e')].append((n, 'n'))

        entity = {v: 0 for v in vertices}

        def color_comp(v, color):
            entity[v] = color
            for to in edges[v]:
                if entity[to] == 0:
                    color_comp(to, color)

        entity_cnt = 0
        for v in vertices:
            if entity[v] == 0:
                entity_cnt += 1
                color_comp(v, entity_cnt)

        for ent in range(1, entity_cnt + 1):
            names = [v[0] for v in vertices if entity[v] == ent and v[1] == 'n']
            emails = [v[0] for v in vertices if entity[v] == ent and v[1] == 'e']
            self.reverse_dict[ent] = {'names': names, 'emails': emails}

        def resolve_entity(n, e):
            if (n, 'n') in entity:
                return entity[(n, 'n')]
            if (e, 'e') in entity:
                return entity[(e, 'e')]
            return 0

        for n, e in self.pairs:
            self.entity_dict[(n, e)] = resolve_entity(n, e)

    def dump(self, processed_folder: ProcessedFolder):
        pickle.dump(self.entity_dict, open(processed_folder.entity_dict, 'wb'))
        pickle.dump(self.reverse_dict, open(processed_folder.reversed_entity_dict, 'wb'))
        with open(processed_folder.readable_entities, 'w', encoding='utf-8') as fout:
            fout.write("entity,names,emails\n")
            for ent, maps in self.reverse_dict.items():
                fout.write("{},{},{}\n".format(
                    ent,
                    "|".join(maps["names"]),
                    "|".join(maps["emails"])
                ))


def merge_aliases_bipartite(processed_folder: ProcessedFolder) -> dict:
    if os.path.exists(processed_folder.entity_dict):
        print("Loading merged entities")
        return pickle.load(open(processed_folder.entity_dict, 'rb'))

    print("Merging entities by bipartite strategy...")
    bipartite_merger = BipartiteEntityMerger()
    change_metadata = pd.read_csv(
        processed_folder.change_metadata_file,
        index_col="id",
        usecols=["id", "authorName", "authorEmail"]
    )
    for index, row in change_metadata.iterrows():
        bipartite_merger.add_entity(row["authorName"], row["authorEmail"])

    bipartite_merger.run_matching()
    bipartite_merger.dump(processed_folder)
    print("Merged entities saved on disk")

    return bipartite_merger.entity_dict


if __name__ == '__main__':
    parser = ArgumentParser()
    parser.add_argument("--data_folder", type=str, required=True)
    args = parser.parse_args()
    merge_aliases_bipartite(ProcessedFolder(args.data_folder))

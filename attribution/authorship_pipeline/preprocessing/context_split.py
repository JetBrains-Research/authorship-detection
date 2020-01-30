import os
import pickle
from argparse import ArgumentParser
from collections import Counter, namedtuple
from enum import Enum
from typing import Dict, Tuple, Callable, List

import numpy as np
import pandas as pd
from tqdm import tqdm

from preprocessing.compute_occurrences import compute_occurrences
from preprocessing.resolve_entities import resolve_entities
from util import ProcessedFolder


class PickType(Enum):
    TRAIN = 1
    TEST = 2
    IGNORED = 3


ContextSplit = namedtuple(
    'ContextSplit',
    ('split_depth', 'change_to_pick_type')
)


class Node:
    def __init__(self, path: str = '', text: str = '', parent: 'Node' = None, depth: int = 0) -> None:
        self.parent = parent
        self.path = path
        self.text = text
        self.children = {}
        self.count = 0
        self.depth = depth
        self.entity_counts = Counter()
        self.change_ids = set()

    def add_path(self, path: List[str], entity: int, change_id: int, count: int) -> None:
        self.change_ids.add(change_id)
        self.count += count
        self.entity_counts[entity] += count
        if len(path) > 0:
            nxt = path[0]
            if nxt not in self.children:
                self.children[nxt] = Node(self.path + '/' + nxt, nxt, parent=self, depth=self.depth + 1)
            self.children[nxt].add_path(path[1:], entity, change_id, count)

    def print_tree(self, n_tabs: int = 0, assert_rule: Callable[['Node'], bool] = None) -> None:
        if assert_rule is not None and assert_rule(self):
            return
        print(f'{"--" * n_tabs}{self.text} : {self.count}')
        for text, node in self.children.items():
            node.print_tree(n_tabs + 1, assert_rule)


def _compress_tree(node: Node) -> None:
    for child in node.children.values():
        child.parent = node
        _compress_tree(child)

    if len(node.children) == 1:
        text, next_node = next(iter(node.children.items()))
        node.path = node.path + '/' + text
        node.text = node.text + '/' + text
        node.children = next_node.children


def _compute_depth(node: Node, depth: int = 0) -> None:
    node.depth = depth
    for child in node.children.values():
        _compute_depth(child, depth + 1)


def _build_tree(change_metadata: pd.Series, change_entities: pd.Series, change_occurrences: Counter,
                is_valid_change: Callable[[int], bool]) -> Node:
    project_root = Node()

    for change_id, path in change_metadata.items():
        if pd.isna(path):
            continue

        entity = change_entities[change_id]
        count = change_occurrences[change_id]
        if is_valid_change(change_id):
            project_root.add_path(path.split('/'), entity, change_id, count)

    _compress_tree(project_root)
    _compute_depth(project_root)
    return project_root


def _max_depth(node: Node) -> int:
    depth = node.depth
    if len(node.children) > 0:
        depth = max(depth, max([_max_depth(c) for c in node.children.values()]))
    return depth


def _get_all_nodes_at_depth(node: Node, nodes_at_depth: List[List[Node]]) -> None:
    nodes_at_depth[node.depth].append(node)
    if len(node.children) > 0:
        for child in node.children.values():
            _get_all_nodes_at_depth(child, nodes_at_depth)
    else:
        for i in range(node.depth + 1, len(nodes_at_depth)):
            nodes_at_depth[i].append(node)


def _detect_min_max_depth(project_root: Node, nodes_at_depth: List[List[Node]], max_train: float) -> Tuple[int, int]:
    min_depth, max_depth = None, None
    total_size = project_root.count

    for d, nodes in enumerate(nodes_at_depth):
        size = sum([n.count for n in nodes])
        max_count = max([n.count for n in nodes])

        total_ratio = size / total_size
        dominant_ratio = max_count / size

        print(f'At {d}: {size} changes, {total_ratio * 100:.1f}%, dominant is {dominant_ratio * 100:.1f}%')

        if dominant_ratio < max_train and min_depth is None:
            min_depth = d
        if total_ratio > 0.05:
            max_depth = d

    return min_depth, max_depth


def _merge_splits(split1: List[ContextSplit], split2: List[ContextSplit]) -> None:
    for s1, s2 in zip(split1, split2):
        assert s1.split_depth == s2.split_depth
        s1.change_to_pick_type.update(s2.change_to_pick_type)


def _compute_mutual_information(prev_split: Dict, cur_split: Dict) -> float:
    total = len(prev_split)
    if total == 0:
        return 0
    pick_types = [PickType.TRAIN, PickType.TEST]
    splits = [prev_split, cur_split]

    p = [[
        sum(1 for change_id, pick_type in split.items() if pick_type is pick) / total
        for pick in pick_types
    ] for split in splits]

    p_mutual = [[
        sum(1 for change_id, pick_type in prev_split.items()
            if pick_type is pick_prev and cur_split[change_id] is pick_cur) / total
        for pick_cur in pick_types
    ] for pick_prev in pick_types]

    if np.min(p) == 0 or np.min(p_mutual) == 0:
        return 1000.

    mutual_info = sum(
        p_mutual[prev][cur] * np.log(p_mutual[prev][cur] / (p[0][prev] * p[1][cur]))
        for prev in range(2) for cur in range(2)
    )
    return mutual_info


def _find_split_at_depth(entity: int, nodes: List[Node], iters: int, prev_split: Dict,
                         min_train: float, max_train: float, change_entities: pd.Series) -> Tuple[Dict, bool, int]:
    mean_train = (min_train + max_train) / 2
    node_counts = [(node.entity_counts[entity], node)
                   for node in nodes
                   if entity in node.entity_counts]

    got_success = False
    best_split = {}
    best_size = 0
    best_mutual_information = None

    for t in range(iters):
        train_count = 0
        test_count = 0
        train_nodes = []
        test_nodes = []
        np.random.shuffle(node_counts)

        for count, n in node_counts:
            if train_count <= (test_count + train_count) * mean_train:
                train_count += count
                train_nodes.append(n)
            else:
                test_count += count
                test_nodes.append(n)

        if train_count + test_count == 0:
            return {}, False, 0

        train_size = train_count / (train_count + test_count)
        if min_train <= train_size <= max_train:
            change_to_pick_type = {}

            for node in train_nodes:
                for change_id in node.change_ids:
                    if change_entities[change_id] == entity:
                        change_to_pick_type[change_id] = PickType.TRAIN

            for node in test_nodes:
                for change_id in node.change_ids:
                    if change_entities[change_id] == entity:
                        change_to_pick_type[change_id] = PickType.TEST

            # print(entity, train_size)
            mutual_information = _compute_mutual_information(prev_split, change_to_pick_type)
            # print(mutual_information, best_mutual_information)
            if best_mutual_information is None or mutual_information < best_mutual_information:
                got_success = True
                best_split = change_to_pick_type
                best_size = train_count + test_count
                best_mutual_information = mutual_information

    return best_split, got_success, best_size


def _find_split(entity: int, change_entities: pd.Series, min_depth: int, max_depth: int,
                min_train: float, max_train: float,
                nodes_at_depth: List[List[Node]], iters: int) -> Tuple[List[ContextSplit], bool, int]:
    resulting_split = [ContextSplit(d, {}) for d in range(min_depth, max_depth + 1)]
    cur_size = 0
    prev_split = {}
    for i, depth in enumerate(range(min_depth, max_depth + 1)):
        split_at_depth, success, size = \
            _find_split_at_depth(entity, nodes_at_depth[depth], iters, prev_split, min_train, max_train,
                                 change_entities)
        if cur_size == 0:
            cur_size = size
        if success:
            resulting_split[i].change_to_pick_type.update(split_at_depth)
            prev_split = split_at_depth
        else:
            return [], False, 0

    return resulting_split, True, cur_size


def context_split(processed_folder: ProcessedFolder, min_count: int = 100, max_count: int = 10 ** 9,
                  min_train: float = 0.7, max_train: float = 0.8) -> List[ContextSplit]:
    if os.path.exists(processed_folder.context_split(min_train, max_train, min_count)):
        print("Loading context-split data")
        return pickle.load(open(processed_folder.context_split(min_train, max_train, min_count), 'rb'))

    print("Splitting changes by context")
    change_metadata = pd.read_csv(
        processed_folder.change_metadata_file,
        index_col="id",
        usecols=["id", "newPath"],
        squeeze=True
    )

    author_occurrences, change_occurrences, author_to_changes, total_count = compute_occurrences(processed_folder)
    change_entities = resolve_entities(processed_folder)

    project_root = _build_tree(change_metadata, change_entities, change_occurrences,
                               lambda change_id:
                               change_occurrences[change_id] > 0 and
                               min_count <= author_occurrences[change_entities[change_id]] <= max_count
                               )

    project_root.print_tree(n_tabs=0, assert_rule=lambda n: n.depth > 3)

    depth = _max_depth(project_root)

    nodes_at_depth = [[] for _ in range(depth + 1)]
    _get_all_nodes_at_depth(project_root, nodes_at_depth)
    print(depth)

    min_depth, max_depth = _detect_min_max_depth(project_root, nodes_at_depth, max_train)

    print(f'Trying to find splits for depth from {min_depth} to {max_depth}')

    authors = {author for author, count in author_occurrences.items() if count >= min_count}

    resulting_split = [ContextSplit(d, {}) for d in range(min_depth, max_depth + 1)]
    success_size = 0
    author_success = 0
    for author in tqdm(authors):
        author_split, success, size = _find_split(author, change_entities, min_depth, max_depth, min_train, max_train,
                                                  nodes_at_depth, iters=10)
        if success:
            success_size += size
            author_success += 1
            _merge_splits(resulting_split, author_split)

    print(f"Kept {success_size / project_root.count * 100:.2f}% of changes by {author_success}/{len(authors)} authors")
    pickle.dump(resulting_split, open(processed_folder.context_split(min_train, max_train, min_count), 'wb'))
    print("Buckets saved on disk")
    return resulting_split


if __name__ == '__main__':
    parser = ArgumentParser()
    parser.add_argument("--data_folder", type=str, required=True)
    # parser.add_argument("--n_time_buckets", type=int, required=True)
    # parser.add_argument("--uniform_distr", action='store_true')
    args = parser.parse_args()
    print(context_split(ProcessedFolder(args.data_folder)))

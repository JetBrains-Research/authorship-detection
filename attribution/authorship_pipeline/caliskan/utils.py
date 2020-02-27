"""
MIT License
Copyright (c) 2020 Yurii Rebryk
"""

from typing import Dict
from typing import Iterable
from typing import List

from javalang.tokenizer import JavaToken, Identifier, Keyword, Literal
from javalang.tree import Node
from tqdm import tqdm


def identifiers(tokens: List[JavaToken]) -> List[Identifier]:
    return [it for it in tokens if isinstance(it, Identifier)]


def keywords(tokens: List[JavaToken]) -> List[Keyword]:
    return [it for it in tokens if isinstance(it, Keyword)]


def literals(tokens: List[JavaToken]) -> List[Literal]:
    return [it for it in tokens if isinstance(it, Literal)]


def children(node: Node) -> List:
    nodes = []

    for it in node.children:
        if isinstance(it, List):
            nodes += it
        else:
            nodes.append(it)

    return nodes


def non_empty_lines(code: str) -> List[str]:
    return [line for line in code.split('\n') if line.strip() != '']


def get_nodes(node, node_type) -> List:
    result = []

    if isinstance(node, node_type):
        result.append(node)

    for it in children(node):
        if isinstance(it, Node):
            result += get_nodes(it, node_type)

    return result


def get_nodes_count(node, node_type) -> int:
    return len(get_nodes(node, node_type))


def build_mapping_to_ids(values: Iterable) -> Dict:
    print("Building mapping to ids")
    values = sorted(set(values))
    return {key: value for key, value in tqdm(zip(values, range(len(values))))}

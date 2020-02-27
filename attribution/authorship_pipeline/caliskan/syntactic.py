"""
MIT License
Copyright (c) 2020 Yurii Rebryk
"""

from collections import Counter
from typing import Dict, List

from javalang.tokenizer import JavaToken
from javalang.tokenizer import Keyword
from javalang.tree import Node

from .feature import Feature
from .utils import children
from .utils import get_nodes


class MaxDepthASTNode(Feature):
    @staticmethod
    def get_max_depth(node) -> int:
        if not isinstance(node, Node):
            return 0

        max_depth = 0
        for it in children(node):
            max_depth = max(max_depth, MaxDepthASTNode.get_max_depth(it))

        return max_depth + 1

    @staticmethod
    def calculate(tree: Node) -> Dict:
        return {'MaxDepthASTNode': MaxDepthASTNode.get_max_depth(tree)}


class ASTNodeBigramsTF(Feature):
    @staticmethod
    def get_bigrams(node) -> List:
        result = []

        for it in children(node):
            if isinstance(it, Node):
                result.append(f'{node.__class__.__name__}_{it.__class__.__name__}')
                result += ASTNodeBigramsTF.get_bigrams(it)

        return result

    @staticmethod
    def calculate(tree: Node) -> Dict:
        bigrams = ASTNodeBigramsTF.get_bigrams(tree)
        count = Counter(bigrams)

        features = {}
        total_count = sum(count.values())
        for key, value in count.items():
            features[f'ASTNodeBigramsTF_{key}'] = value / total_count

        return features


class ASTNodeTypesTF(Feature):
    @staticmethod
    def calculate(tree: Node) -> Dict:
        nodes = get_nodes(tree, Node)
        types = [node.__class__.__name__ for node in nodes]
        count = Counter(types)

        features = {}
        total_count = sum(count.values())
        for key, value in count.items():
            features[f'ASTNodeTypesTF_{key}'] = value / total_count

        return features


class JavaKeywords(Feature):
    @staticmethod
    def calculate(tokens: List[JavaToken]) -> Dict:
        values = [token.value for token in tokens if isinstance(token, Keyword)]
        count = Counter(values)

        features = {}
        total_count = sum(count.values())
        for key, value in count.items():
            features[f'javaKeywords_{key}'] = value / total_count

        return features

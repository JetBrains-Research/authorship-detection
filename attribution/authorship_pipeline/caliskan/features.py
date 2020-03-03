"""
MIT License
Copyright (c) 2020 Yurii Rebryk
"""

import warnings
from time import time
from typing import Dict, Union, Tuple
from typing import List

import javalang
import numpy as np
from joblib import Parallel, delayed
from scipy.sparse import csc_matrix
from tqdm import tqdm

from .layout import NewLineBeforeOpenBrace
from .layout import NumEmptyLines
from .layout import NumSpaces
from .layout import NumTabs
from .layout import TabsLeadLines
from .layout import WhiteSpaceRatio
from .lexical import AvgLineLength
from .lexical import AvgParams
from .lexical import NumFunctions
from .lexical import NumKeyword
from .lexical import NumKeywords
from .lexical import NumLiterals
from .lexical import NumTernary
from .lexical import NumTokens
from .lexical import StdDevLineLength
from .lexical import StdDevNumParams
from .lexical import WordUnigramTF
from .syntactic import ASTNodeBigramsTF
from .syntactic import ASTNodeTypesTF
from .syntactic import JavaKeywords
from .syntactic import MaxDepthASTNode

warnings.filterwarnings('ignore')


def calculate_features(path: str) -> Union[Dict, None]:
    """
    Calculates a set of features for the given source file.

    :param path: path to the file
    :return: dictionary with features
    """
    with open(path, 'r', errors='ignore') as file:
        code = 'class A {' + '\n' + file.read() + '\n' + '}'

    file_length = len(code)

    try:
        tokens = list(javalang.tokenizer.tokenize(code))
        tree = javalang.parse.parse(code)

        features = {}

        # LEXICAL FEATURES
        features.update(WordUnigramTF.calculate(tokens))
        features.update(NumKeyword.calculate(tokens, file_length))
        features.update(NumTokens.calculate(tokens, file_length))
        features.update(NumLiterals.calculate(tokens, file_length))
        features.update(NumKeywords.calculate(tokens, file_length))
        features.update(NumFunctions.calculate(tree, file_length))
        features.update(NumFunctions.calculate(tree, file_length))
        features.update(NumTernary.calculate(tree, file_length))
        features.update(AvgLineLength.calculate(code))
        features.update(StdDevLineLength.calculate(code))
        features.update(AvgParams.calculate(tree))
        features.update(StdDevNumParams.calculate(tree))

        # LAYOUT FEATURES
        features.update(NumTabs.calculate(code))
        features.update(NumSpaces.calculate(code))
        features.update(NumEmptyLines.calculate(code))
        features.update(WhiteSpaceRatio.calculate(code))
        features.update(NewLineBeforeOpenBrace.calculate(code))
        features.update(TabsLeadLines.calculate(code))

        # SYNTACTIC FEATURES
        features.update(MaxDepthASTNode.calculate(tree))
        features.update(ASTNodeBigramsTF.calculate(tree))
        features.update(ASTNodeTypesTF.calculate(tree))
        features.update(JavaKeywords.calculate(tokens))

        return features
    except Exception as e:
        return None


def calculate_features_for_files(files: List[str], n_jobs: int = 4) -> Tuple[List[str], List[Dict]]:
    """
    Calculates sets of features for the given source files.

    :param files: list of files
    :param n_jobs: number of jobs
    :return: list with features for each source file
    """

    with Parallel(n_jobs=n_jobs) as pool:
        features = pool(delayed(calculate_features)(path) for path in tqdm(files))
    parsed_files, features = zip(*[(path, f) for path, f in zip(files, features) if f is not None])
    return parsed_files, features


def build_sample(sample: Dict, feature_to_id: Dict, row_number: int) -> List[Tuple[float, Tuple[int, int]]]:
    data = []
    for key, value in sample.items():
        data.append((value, (row_number, feature_to_id[key])))
    return data


def build_dataset(samples: List[Dict], n_jobs: int = 7) -> Tuple[csc_matrix, List[str]]:
    """
    Builds a pandas data frame from the given list of feature sets.

    :param samples: list of features
    :param n_jobs: number of jobs
    :return: data frame with all features
    """
    print("Building sparse dataset")
    print("Collecting feature-value pairs")
    feature_values = [
        (feature, row, value)
        for row, sample in enumerate(tqdm(samples))
        for feature, value in sample.items()
    ]
    print(f"Collected {len(feature_values)} pairs")
    print("Sorting feature-value pairs")
    time_before = time()
    feature_values = sorted(feature_values)
    time_after = time()
    print(f"Sorted in {time_after - time_before:.1f} sec")

    print("Building sparse matrix and mapping")
    data = np.empty(len(feature_values), dtype=np.float)
    row = np.empty(len(feature_values), dtype=np.int)
    col = np.empty(len(feature_values), dtype=np.int)

    cur_col = -1
    feature_to_id = {}
    feature_names = []
    last_feature = None
    for i, (feature, r, value) in enumerate(tqdm(feature_values)):
        if last_feature != feature:
            cur_col += 1
            last_feature = feature
            feature_to_id[feature] = cur_col
            feature_names.append(feature)

        data[i] = value if not np.isnan(value) else 0.
        row[i] = r
        col[i] = cur_col

    feature_values = csc_matrix((data, (row, col)))

    return feature_values, feature_names

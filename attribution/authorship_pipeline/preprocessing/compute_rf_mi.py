import numpy as np
from joblib import Parallel, delayed
from scipy.sparse import csc_matrix
from sklearn.feature_selection import mutual_info_classif
from tqdm import tqdm


def limit_features(features: csc_matrix, mutual_information: np.ndarray, n_features: int):
    indices = np.argsort(mutual_information)[-n_features:]
    # print(len(mutual_information))
    # indices = mutual_information > 1e-5
    # print(sum(indices))
    features = features[:, indices]
    return features


def compute_mi(features: csc_matrix, authors: np.ndarray) -> np.ndarray:
    print("Computing mutual info")
    with Parallel(n_jobs=-1) as pool:
        part_size = 1000
        m = features.shape[1]
        mutual_info_parts = pool(
            delayed(mutual_info_classif)(features[:, i:i + part_size], authors, random_state=0)
            for i in tqdm(range(0, m, part_size))
        )

    mutual_info = np.concatenate(mutual_info_parts)
    mutual_info /= np.max(mutual_info)

    return mutual_info

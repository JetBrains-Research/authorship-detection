import numpy as np
import torch
import torch.nn.functional as F
from torch.utils.data import Dataset

from data_processing import PathMinerLoader
from data_processing.UtilityEntities import PathContexts, path_contexts_from_index


# Transforms data, loaded from PathMiner's output, to format suitable for model training.
class PathMinerDataset(Dataset):

    # Converts data to PyTorch Tensors for further usage in the model
    def __init__(self, path_contexts: PathContexts, labels: np.ndarray, should_pad: bool = True,
                 explicit_features: np.ndarray = None):
        self._size = labels.size
        self._labels = torch.LongTensor(labels)
        self._contexts = path_contexts
        self._explicit_features = explicit_features
        self._should_pad = should_pad
        if should_pad:
            # self._pad_length = max([len(arr) for arr in self.contexts.starts])
            self._pad_length = 200
            # print(self._pad_length)

    @classmethod
    def from_loader(cls, loader: PathMinerLoader, indices: np.ndarray = None, should_pad: bool = True,
                    use_explicit_features: bool = False):
        contexts, labels = (path_contexts_from_index(loader.path_contexts(), indices), loader.labels()[indices]) \
            if indices is not None \
            else (loader.path_contexts(), loader.labels())
        if use_explicit_features:
            explicit_features = loader.explicit_features()[indices] \
                if indices is not None \
                else loader.explicit_features()
        else:
            explicit_features = None

        return cls(contexts, labels, should_pad, explicit_features=explicit_features)

    @classmethod
    def from_timesplit_loader(cls, loader: PathMinerLoader, time_fold_ind: int, should_pad: bool = True):
        return cls(loader.path_contexts()[time_fold_ind], loader.labels()[time_fold_ind], should_pad)

    def __len__(self):
        return self._size

    def __getitem__(self, index):
        cur_len = len(self._contexts.starts[index])
        starts = torch.LongTensor(self._contexts.starts[index])
        paths = torch.LongTensor(self._contexts.paths[index])
        ends = torch.LongTensor(self._contexts.ends[index])
        explicit = self._explicit_features[index] if self._explicit_features is not None else None
        if self._should_pad:
            if cur_len < self._pad_length:
                return {
                    'starts': F.pad(starts, [0, self._pad_length - cur_len], mode='constant', value=0),
                    'paths': F.pad(paths, [0, self._pad_length - cur_len], mode='constant', value=0),
                    'ends': F.pad(ends, [0, self._pad_length - cur_len], mode='constant', value=0),
                    'labels': self._labels[index],
                    # 'explicit': explicit
                }
            else:
                return {
                    'starts': starts[:self._pad_length],
                    'paths': paths[:self._pad_length],
                    'ends': ends[:self._pad_length],
                    'labels': self._labels[index],
                    # 'explicit': explicit
                }
        else:
            return {
                'starts': starts,
                'paths': paths,
                'ends': ends,
                'labels': self._labels[index],
                # 'explicit': explicit
            }

    def labels(self) -> np.ndarray:
        return self._labels.numpy()

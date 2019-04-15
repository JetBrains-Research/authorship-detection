import numpy as np
import torch
from torch.utils.data import Dataset
import torch.nn.functional as F

from data_processing import PathMinerLoader
from data_processing.UtilityEntities import PathContexts, path_contexts_from_index


# Transforms data, loaded from PathMiner's output, to format suitable for model training.
class PathMinerDataset(Dataset):

    # Converts data to PyTorch Tensors for further usage in the model
    def __init__(self, path_contexts: PathContexts, labels: np.ndarray, should_pad: bool = True):
        self._size = labels.size
        self._labels = torch.LongTensor(labels)
        self._contexts = path_contexts
        self._should_pad = should_pad
        if should_pad:
            # self._pad_length = max([len(arr) for arr in self.contexts.starts])
            self._pad_length = 2000
            print(self._pad_length)

    @classmethod
    def from_loader(cls, loader: PathMinerLoader, indices: np.ndarray = None, should_pad: bool = True):
        contexts, labels = (path_contexts_from_index(loader.path_contexts(), indices), loader.labels()[indices]) \
            if indices is not None \
            else (loader.path_contexts(), loader.labels())
        return cls(contexts, labels, should_pad)

    def __len__(self):
        return self._size

    def __getitem__(self, index):
        cur_len = len(self._contexts.starts[index])
        starts = torch.LongTensor(self._contexts.starts[index])
        paths = torch.LongTensor(self._contexts.paths[index])
        ends = torch.LongTensor(self._contexts.ends[index])
        if self._should_pad and cur_len < self._pad_length:
            return {
                'starts': F.pad(starts, [0, self._pad_length - cur_len], mode='constant', value=0),
                'paths': F.pad(paths, [0, self._pad_length - cur_len], mode='constant', value=0),
                'ends': F.pad(ends, [0, self._pad_length - cur_len], mode='constant', value=0),
                'labels': self._labels[index]
            }
        else:
            return {
                'starts': starts,
                'paths': paths,
                'ends': ends,
                'labels': self._labels[index]
            }

    def labels(self) -> np.ndarray:
        return self._labels.numpy()
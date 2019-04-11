import numpy as np
import torch
from torch.utils.data import Dataset
import torch.nn.functional as F

from data_processing import PathMinerLoader
from data_processing.UtilityEntities import PathContexts, path_contexts_from_index


# Transforms data, loaded from PathMiner's output, to format suitable for model training.
class PathMinerDataset(Dataset):

    # Converts data to PyTorch Tensors for further usage in the model
    def __init__(self, path_contexts: PathContexts, labels: np.ndarray):
        self.size = labels.size
        self.labels = torch.LongTensor(labels)
        self.contexts = path_contexts
        # self._pad_length = max([len(arr) for arr in self.contexts.starts])
        self._pad_length = 2000
        print(self._pad_length)

    @classmethod
    def from_loader(cls, loader: PathMinerLoader, indices: np.ndarray = None):
        contexts, labels = (path_contexts_from_index(loader.path_contexts(), indices), loader.labels()[indices]) \
            if indices is not None \
            else (loader.path_contexts(), loader.labels())
        return cls(contexts, labels)

    def __len__(self):
        return self.size

    def __getitem__(self, index):
        cur_len = len(self.contexts.starts[index])
        starts = torch.LongTensor(self.contexts.starts[index][:min(self._pad_length, cur_len)])
        paths = torch.LongTensor(self.contexts.paths[index][:min(self._pad_length, cur_len)])
        ends = torch.LongTensor(self.contexts.ends[index][:min(self._pad_length, cur_len)])
        if cur_len < self._pad_length:
            return {
                'starts': F.pad(starts, [0, self._pad_length - cur_len], mode='constant', value=0),
                'paths': F.pad(paths, [0, self._pad_length - cur_len], mode='constant', value=0),
                'ends': F.pad(ends, [0, self._pad_length - cur_len], mode='constant', value=0),
                'labels': self.labels[index]
            }
        else:
            return {
                'starts': starts,
                'paths': paths,
                'ends': ends,
                'labels': self.labels[index]
            }

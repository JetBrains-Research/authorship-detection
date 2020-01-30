import argparse
import time

import numpy as np
import torch
import torch.nn as nn
import torch.optim as optim
from sklearn.metrics import accuracy_score
from torch.utils.data import DataLoader

from data_processing.PathMinerDataset import PathMinerDataset
from data_processing.PathMinerLoader import PathMinerLoader
from model.ProjectClassifier import ProjectClassifier


# Add labels with project info to path contexts.
def label_contexts(project_paths):
    project_paths = np.array(list(map(lambda path: path.split('/')[4], project_paths)), dtype=np.object)
    projects, labels, counts = np.unique(project_paths, return_inverse=True, return_counts=True)
    return labels


# Create training and validation dataset from path contexts.
def split_train_test(loader: PathMinerLoader, batch_num: int):
    index = loader.labels()
    n_classes = np.unique(index).size
    batch_per_class = np.zeros(n_classes, dtype=np.int32)
    train_indices = []
    test_indices = []
    for i, ind in enumerate(index):
        if ind >= 10:
            continue
        batch_per_class[ind] += 1
        if batch_per_class[ind] == batch_num:
            test_indices.append(i)
        else:
            train_indices.append(i)

    print(len(train_indices), len(test_indices))
    return PathMinerDataset.from_loader(loader, np.array(train_indices, dtype=np.int32)), \
           PathMinerDataset.from_loader(loader, np.array(test_indices, dtype=np.int32))


# Train a model, print training summary and results on test dataset after every epoch.
def train(train_loader, test_loader, model, optimizer, loss_function, n_epochs, log_batches, batch_size):
    print("Start training")
    for epoch in range(n_epochs):
        print("Epoch #{}".format(epoch + 1))
        current_loss = 0
        start_time = time.time()
        for n_batch, sample in enumerate(train_loader):
            starts, paths, ends, labels = sample['starts'], sample['paths'], sample['ends'], sample['labels']
            optimizer.zero_grad()

            predictions = model((starts, paths, ends))
            loss = loss_function(predictions, labels)
            loss.backward()
            optimizer.step()

            current_loss += loss.item()
            if (n_batch + 1) % log_batches == 0:
                print("After {} batches: average loss {}".format(n_batch + 1, current_loss / log_batches))
                print(f"Throughput {log_batches * batch_size / (time.time() - start_time)} examples / sec")
                current_loss = 0
                start_time = time.time()

        with torch.no_grad():
            total = len(test_loader.dataset)
            predictions = np.zeros(total)
            targets = np.zeros(total)
            cur = 0
            for sample in test_loader:
                starts, paths, ends, labels = sample['starts'], sample['paths'], sample['ends'], sample['labels']
                batched_predictions = model((starts, paths, ends))
                # binarize the prediction
                # batched_predictions = (batched_predictions > 0.5).numpy()
                batched_predictions = np.argmax(batched_predictions, axis=1)
                # batched_targets = (labels > 0.5).numpy()
                batched_targets = labels
                predictions[cur:cur + len(batched_predictions)] = batched_predictions
                targets[cur:cur + len(batched_targets)] = batched_targets
                cur += len(batched_predictions)
            # print("accuracy: {:.3f}, precision: {:.3f}, recall: {:.3f}".format(
            #     accuracy_score(targets, predictions),
            #     precision_score(targets, predictions),
            #     recall_score(targets, predictions)
            # ))
            print(predictions)
            print(targets)
            print(f"accuracy: {accuracy_score(targets, predictions)}")

        with torch.no_grad():
            total = len(train_loader.dataset)
            predictions = np.zeros(total)
            targets = np.zeros(total)
            cur = 0
            for sample in train_loader:
                starts, paths, ends, labels = sample['starts'], sample['paths'], sample['ends'], sample['labels']
                batched_predictions = model((starts, paths, ends))
                # binarize the prediction
                # batched_predictions = (batched_predictions > 0.5).numpy()
                batched_predictions = np.argmax(batched_predictions, axis=1)
                # batched_targets = (labels > 0.5).numpy()
                batched_targets = labels
                predictions[cur:cur + len(batched_predictions)] = batched_predictions
                targets[cur:cur + len(batched_targets)] = batched_targets
                cur += len(batched_predictions)
            # print("accuracy: {:.3f}, precision: {:.3f}, recall: {:.3f}".format(
            #     accuracy_score(targets, predictions),
            #     precision_score(targets, predictions),
            #     recall_score(targets, predictions)
            # ))
            print(predictions)
            print(targets)
            print(f"train accuracy: {accuracy_score(targets, predictions)}")
    print("Training completed")


def main(args):
    print("Loading generated data")
    loader = PathMinerLoader.from_folder(args.source_folder, transform=label_contexts)
    # print("Creating datasets")
    train_dataset, test_dataset = split_train_test(loader, 1)
    train_loader = DataLoader(train_dataset, args.batch_size, shuffle=True)
    test_loader = DataLoader(test_dataset, args.batch_size)

    model = ProjectClassifier(loader.tokens().size, loader.paths().size, dim=6, n_classes=loader.n_classes())
    optimizer = optim.Adam(model.parameters(), lr=0.01)
    loss_function = nn.CrossEntropyLoss()

    train(train_loader, test_loader, model, optimizer, loss_function, n_epochs=1000, log_batches=1, batch_size=args.batch_size)


if __name__ == '__main__':
    np.random.seed(239)

    parser = argparse.ArgumentParser()
    parser.add_argument('--source_folder', type=str, default='processed_data/',
                        help='Folder containing output of PathMiner')
    parser.add_argument('--batch_size', type=int, default=32, help='Batch size for training')

    args = parser.parse_args()
    main(args)

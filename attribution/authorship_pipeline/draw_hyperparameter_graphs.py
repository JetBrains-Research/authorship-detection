import itertools
import os
from argparse import ArgumentParser
from typing import Dict

import matplotlib.pyplot as plt
import yaml
from matplotlib.ticker import MultipleLocator

plt.rcParams['figure.figsize'] = [20, 10]
markersize = 16


def draw_rf(results: Dict, dataset: str, title, xlabel, filename, locator, picker, rotate):
    fig, ax = plt.subplots()

    marker = itertools.cycle(('o', '^', 's', 'D', '*', 'X', 'v', 'p', 'P'))

    x, y, yerr = [], [], []
    for (features, depth, trees, length, width, suffix), (mean, std) in results.items():
        xval = picker(features, depth, trees, length, width, suffix)
        if xval is None:
            continue
        x.append(xval)
        y.append(mean)
        yerr.append(std)

    ax.errorbar(x, y, yerr, fmt='o', label=f'PbRF accuracy', marker=next(marker), markersize=markersize)
    ax.legend(loc=0, prop={'size': 24})
    ax.tick_params(labelsize=28)
    ax.xaxis.set_major_locator(MultipleLocator(locator))
    ax.yaxis.set_minor_locator(MultipleLocator(0.01))
    ax.set_xlabel(xlabel, fontsize=36)
    if rotate:
        plt.xticks(rotation=90)
    ax.set_ylabel('Accuracy', fontsize=36)
    ax.set_title(title, fontsize=36)

    fig.savefig(f"../../figures/{dataset}/{filename}.pdf", bbox_inches='tight')
    fig.savefig(f"../../figures/{dataset}/{filename}.png", bbox_inches='tight')

    # plt.show()


def draw_nn(results: Dict, dataset: str):
    fig, ax = plt.subplots()

    marker = itertools.cycle(('o', '^', 's', 'D', '*', 'X', 'v', 'p', 'P'))

    x, y, yerr = [], [], []
    for dim, (mean, std) in results.items():
        x.append(int(dim))
        y.append(mean)
        yerr.append(std)

    ax.errorbar(x, y, yerr, fmt='o', label=f'PbNN accuracy', marker=next(marker), markersize=markersize)
    ax.legend(loc=0, prop={'size': 24})
    ax.tick_params(labelsize=28)
    # plt.xscale('log')
    ax.xaxis.set_major_locator(MultipleLocator(32))
    # ax.xaxis.set_minor_locator(MultipleLocator(4))
    ax.yaxis.set_minor_locator(MultipleLocator(0.01))
    ax.set_xlabel('Hidden dimension in PbNN', fontsize=36)
    ax.set_ylabel('Accuracy', fontsize=36)
    ax.set_title(f'Dependency of the accuracy on {dataset.upper()} dataset', fontsize=36)

    fig.savefig(f"../../figures/{dataset}/nn-dims.pdf", bbox_inches='tight')
    fig.savefig(f"../../figures/{dataset}/nn-dims.png", bbox_inches='tight')

    # plt.show()


output_folder = os.path.join("output", "configs")


def get_suffix(s: str):
    if '_' in s:
        width, s = s.split('_')
        if s.startswith('tokens'):
            return width, 'tokens'
        if s.startswith('paths'):
            return width, 'paths'
    width, s = s.split('.')
    return width, ''


def collect_rf(dataset: str):
    folder = os.path.join(output_folder, dataset, "dependencies")
    yaml_files = os.listdir(folder)
    results = {}
    for file in yaml_files:
        result = yaml.load(open(os.path.join(folder, file), 'r'), Loader=yaml.Loader)
        print(file)
        _, _, features, _, depth, _, trees, _, length, suffix = file.split("_", 9)
        width, suffix = get_suffix(suffix)
        results[(features, depth, trees, length, width, suffix)] = (result['mean'], result['std'])
    return results


def collect_nn(dataset: str):
    folder = os.path.join(output_folder, dataset, "nn")
    yaml_files = os.listdir(folder)
    results = {}
    for file in yaml_files:
        result = yaml.load(open(os.path.join(folder, file), 'r'), Loader=yaml.Loader)
        print(file)
        _, dim, _, _, _ = file.split("_")
        results[dim] = (result['mean'], result['std'])
    return results


def pick_depth(features, depth, trees, length, width, suffix):
    if features == "10000" and trees == "300" and length == "8" and width == "2" and suffix == "":
        if depth == "no":
            return 80
        return int(depth)
    return None


def pick_features(features, depth, trees, length, width, suffix):
    if depth == "no" and trees == "300" and length == "8" and width == "2" and suffix == "":
        return int(features)
    return None


def pick_trees(features, depth, trees, length, width, suffix):
    if depth == "no" and features == "10000" and length == "8" and width == "2" and suffix == "":
        return int(trees)
    return None


def pick_length(features, depth, trees, length, width, suffix):
    if depth == "no" and features == "10000" and trees == "300" and width == "2" and suffix == "":
        return int(length)
    return None


for dataset in ['gcj', 'java40']:
    rf_results = collect_rf(dataset)
    nn_results = collect_nn(dataset)
    draw_nn(nn_results, dataset)
    draw_rf(
        rf_results,
        dataset,
        title=f'Dependency of the accuracy on {dataset.upper()} dataset',
        xlabel='Maximal tree depth in PbRF',
        filename='rf-depth',
        locator=10,
        picker=pick_depth,
        rotate=False
    )
    draw_rf(
        rf_results,
        dataset,
        title=f'Dependency of the accuracy on {dataset.upper()} dataset',
        xlabel='Number of features in PbRF',
        filename='rf-features',
        locator=1000,
        picker=pick_features,
        rotate=True
    )
    draw_rf(
        rf_results,
        dataset,
        title=f'Dependency of the accuracy on {dataset.upper()} dataset',
        xlabel='Number of trees in PbRF',
        filename='rf-trees',
        locator=50,
        picker=pick_trees,
        rotate=False
    )
    draw_rf(
        rf_results,
        dataset,
        title=f'Dependency of the accuracy on {dataset.upper()} dataset',
        xlabel='Length of paths in PbRF',
        filename='rf-length',
        locator=1,
        picker=pick_length,
        rotate=False
    )

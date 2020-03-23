import itertools
import os
from argparse import ArgumentParser

import matplotlib.pyplot as plt
import yaml
from matplotlib.ticker import MultipleLocator

plt.rcParams['figure.figsize'] = [20, 10]
method_mapping = {'nn': 'PbNN', 'rf': 'PbRF', 'caliskan': 'JCaliskan'}
markersize = 16

parser = ArgumentParser()
parser.add_argument("--project", type=str, required=True)
args = parser.parse_args()


def draw_context(min_count, max_count, results):
    fig, ax = plt.subplots()

    marker = itertools.cycle(('o', '^', 's', 'D', '*', 'X', 'v', 'p', 'P'))

    for classification_results, label in results:
        accuracy = [cr.accuracy for cr in classification_results]
        accuracy = accuracy[:9]
        ax.plot(range(1, len(accuracy) + 1), accuracy, 'o', label=label, marker=next(marker), markersize=markersize)

    ax.legend(loc=0, prop={'size': 24})
    ax.tick_params(labelsize=28)
    ax.xaxis.set_major_locator(MultipleLocator(1))
    ax.yaxis.set_minor_locator(MultipleLocator(0.01))
    ax.set_xlabel('Split depth', fontsize=36)
    ax.set_ylabel('Accuracy', fontsize=36)

    fig.savefig(
        f"../../figures/{args.project}/contextsplit-graph-{min_count}-{max_count}.pdf",
        bbox_inches='tight')

    fig.savefig(
        f"../../figures/{args.project}/contextsplit-graph-{min_count}-{max_count}.png",
        bbox_inches='tight')

    # plt.show()


def draw_time(min_count, max_count, label, results):
    fig, ax = plt.subplots()

    marker = itertools.cycle(('o', '^', 's', 'D', '*', 'X', 'v', 'p', 'P'))

    fold_results = [[] for _ in range(10)]

    for cr in results:
        fold_results[cr.fold_ind[0]].append(cr.accuracy)

    for k in range(len(fold_results)):
        ax.plot(range(k + 2, k + 2 + len(fold_results[k])), fold_results[k], '--o', label=f'Fold {k + 1}',
                marker=next(marker), markersize=markersize)
        # ax.plot(range(2, len(accuracy) + 2), accuracy, '--o', label=label, marker=next(marker), markersize=markersize)

    ax.legend(loc=0, prop={'size': 20})
    ax.tick_params(labelsize=28)
    ax.xaxis.set_major_locator(MultipleLocator(1))
    ax.yaxis.set_minor_locator(MultipleLocator(0.01))
    if int(max_count) > 10000:
        ax.set_ylim(0.05, 0.35)
    else:
        ax.set_ylim(0.05, 0.30)
    ax.set_xlabel('Evaluation fold number', fontsize=36)
    ax.set_ylabel('Accuracy', fontsize=36)

    fig.savefig(
        f"../../figures/{args.project}/timesplit-graph-{label}-{min_count}-{max_count}.pdf",
        bbox_inches='tight')

    fig.savefig(
        f"../../figures/{args.project}/timesplit-graph-{label}-{min_count}-{max_count}.png",
        bbox_inches='tight')
    # plt.show()


output_folder = os.path.join("output", "configs")
projects = os.listdir(output_folder)
if args.project not in projects:
    raise ValueError(f"The project {args.project} does not have output files")

project_folder = os.path.join(output_folder, args.project)
classifiers = os.listdir(project_folder)
context_results = {}
time_results = {}

for classifier in ['nn', 'rf', 'caliskan']:
    folder = os.path.join(project_folder, classifier)
    yaml_files = os.listdir(folder)
    for file in yaml_files:
        result = yaml.load(open(os.path.join(folder, file), 'r'), Loader=yaml.Loader)

        mode, c1, c2, _ = file.split("_")
        if mode == "context":
            if (c1, c2) not in context_results:
                context_results[(c1, c2)] = list()
            context_results[(c1, c2)].append((result['scores'], method_mapping[classifier]))
        elif mode == "time":
            time_results[(c1, c2, method_mapping[classifier])] = result['scores']

for (c1, c2), results in context_results.items():
    draw_context(c1, c2, results)

for (c1, c2, classifier), results in time_results.items():
    draw_time(c1, c2, classifier, results)

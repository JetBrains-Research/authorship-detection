import argparse
import os

import yaml

from classifiers.CaliskanClassifier import CaliskanClassifier
from classifiers.NNClassifier import NNClassifier
from classifiers.RFClassifier import RFClassifier
from classifiers.config import Config
from preprocessing.context_split import context_split
from preprocessing.resolve_entities import resolve_entities
from preprocessing.time_split import time_split
from util import ProcessedFolder


def output_filename(input_file):
    if not os.path.exists('output'):
        os.mkdir('output')
    return 'output/' + input_file


def output_file(input_file):
    if not os.path.exists(os.path.dirname(input_file)):
        os.makedirs(os.path.dirname(input_file))
    return open(output_filename(input_file), 'w')


def main(args):
    # if os.path.isfile(output_filename(args.config_file)):
    #     print("Already processed")
    #     exit(0)

    config = Config.fromyaml(args.config_file)
    project_folder = ProcessedFolder(config.source_folder())
    change_entities = resolve_entities(project_folder)
    if config.mode() == 'time':
        change_to_time_bucket = time_split(project_folder, config.time_folds(), uniform_distribution=True)
    else:
        change_to_time_bucket = None
    if config.mode() == 'context':
        context_splits = context_split(project_folder, *config.min_max_count(), *config.min_max_train())
    else:
        context_splits = None

    if config.classifier_type() == 'nn':
        classifier = NNClassifier(config, project_folder, change_entities, change_to_time_bucket,
                                  config.min_max_count(), context_splits)
    elif config.classifier_type() == 'rf':
        classifier = RFClassifier(config, project_folder, change_entities, change_to_time_bucket,
                                  config.min_max_count(), context_splits)
    elif config.classifier_type() == 'caliskan':
        classifier = CaliskanClassifier(config, project_folder, change_entities, change_to_time_bucket,
                                        config.min_max_count(), context_splits)
    else:
        raise ValueError('Classifier type should be set in config')

    if config.mode() == 'time':
        fold_indices = [(i, j) for i in range(config.time_folds()) for j in range(i + 1, config.time_folds())]
    elif config.mode() == 'context':
        fold_indices = [i for i in range(len(context_splits))]
    else:
        fold_indices = classifier.cross_validation_folds()

    mean, std, scores = classifier.run(fold_indices)
    print(f'{mean:.3f}+-{std:.3f}')
    yaml.dump({
        'mean': mean,
        'std': std,
        'scores': scores
    }, output_file(args.config_file), default_flow_style=False)


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument(dest='config_file', type=str, help='Configuration file in YAML format')
    args = parser.parse_args()
    main(args)

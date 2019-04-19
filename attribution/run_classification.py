import argparse
import os
import numpy as np
import yaml

from classifier.RFClassifier import RFClassifier
from classifier.config import Config


def output_filename(input_file):
    return 'output/' + input_file


def output_file(input_file):
    return open(output_filename(input_file), 'w')


def main(args):
    if os.path.isfile(output_filename(args.config_file)):
        print("Already processed")
        exit(0)

    config = Config.fromyaml(args.config_file)
    classifier = RFClassifier(config)
    if config.n_runs() is not None:
        scores = []
        for _ in range(config.n_runs()):
            _, _, new_scores = classifier.cross_validate()
            scores.extend(new_scores)
            classifier.update_chosen_classes()
        mean = float(np.mean(scores))
        std = float(np.std(scores))
    else:
        mean, std, scores = classifier.cross_validate()
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

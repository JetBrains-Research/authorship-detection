import argparse

import yaml

from classifier.RFClassifier import RFClassifier
from classifier.config import Config


def output_file(input_file):
    return open('output/' + input_file, 'w')


def main(args):
    config = Config.fromyaml(args.config_file)
    classifier = RFClassifier(config)
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

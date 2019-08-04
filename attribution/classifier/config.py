from typing import Union

import yaml


class Config:

    def __init__(self, config):
        self.config = config

    @classmethod
    def fromyaml(cls, filename: str):
        return cls(yaml.load(open(filename, 'r'), Loader=yaml.Loader))

    def classifier_type(self):
        return self.__get('classifier_type')

    def source_folder(self):
        return self.__get('source_folder')

    def features(self):
        return self.__get('features')

    def feature_count(self):
        return self.__get('feature_count')

    # Index of author's folder in path
    def label_position(self):
        return self.__get('label_position')

    def seed(self):
        return self.__get('seed')

    def n_classes(self):
        return self.__get('n_classes')

    def test_size(self) -> Union[int, float]:
        return self.__get('test_size')

    def params(self):
        return self.__get('params')

    def n_runs(self):
        return self.__get('n_runs')

    def mutual_info_file(self):
        return self.__get('mutual_info_file')

    def use_explicit_features(self):
        return self.features() is not None and 'explicit' in self.features()

    def epochs(self):
        return self.__get('epochs')

    def batch_size(self):
        return self.__get('batch_size')

    def hidden_dim(self):
        return self.__get('hidden_dim')

    def learning_rate(self):
        return self.__get('learning_rate')

    def log_batches(self):
        return self.__get('log_batches')

    def __get(self, param):
        try:
            return self.config[param]
        except KeyError:
            return None

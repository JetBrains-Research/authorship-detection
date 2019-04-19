import yaml


class Config:

    def __init__(self, config):
        self.config = config

    @classmethod
    def fromyaml(cls, filename: str):
        return cls(yaml.load(open(filename, 'r'), Loader=yaml.Loader))

    def source_folder(self):
        return self.__get('source_folder')

    def features(self):
        return self.__get('features')

    def feature_counts(self):
        return self.__get('feature_counts')

    # Index of author's folder in path
    def label_position(self):
        return self.__get('label_position')

    def seed(self):
        return self.__get('seed')

    def n_classes(self):
        return self.__get('n_classes')

    def test_size(self):
        return self.__get('test_size')

    def params(self):
        return self.__get('params')

    def n_runs(self):
        return self.__get('n_runs')

    def __get(self, param):
        try:
            return self.config[param]
        except KeyError:
            return None

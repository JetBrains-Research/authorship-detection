class PathSettings:
    def __init__(self, repo_name):
        self.repo_path = "data/repos/{}".format(repo_name)
        self.data_dir = "data/exploded/{}".format(repo_name)
        self.uast_dir = "{}/uast_zipped".format(self.data_dir)
        self.blob_dir = "{}/blobs".format(self.data_dir)

        self.full_repo_data_file = "{}/infos_full.csv".format(self.data_dir)
        self.incomplete_repo_data_file = "{}/infos_incomplete.csv".format(self.data_dir)

        self.processed_commits_filename = "{}/processed_commits.csv".format(self.data_dir)

        self.complete_parse_status_filename = "{}/parse_status.csv".format(self.data_dir)
        self.incomplete_parse_status_filename = "{}/parse_status_incomplete.csv".format(self.data_dir)

    def get_blob_path(self, blob_id):
        return "{}/{}".format(self.blob_dir, blob_id)

    def get_uast_path(self, blob_id):
        return "{}/{}.uast".format(self.uast_dir, blob_id)

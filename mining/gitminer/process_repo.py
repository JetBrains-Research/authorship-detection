import os
import pandas as pd
import time
from git import Repo
from pandas import DataFrame
from path_settings import PathSettings


class RepositoryProcessor:
    def __init__(self, repo_name):
        self.path_settings = PathSettings(repo_name)

    @staticmethod
    def is_valid(entry):
        if entry.change_type == 'T':
            return False
        path = entry.a_path if entry.change_type == 'D' else entry.b_path
        return path.endswith(".java")

    @staticmethod
    def remove_commas(content):
        return content.replace(",", "")

    def extract_change_info(self, commit, entry):
        old_blob_id = None
        new_blob_id = None
        old_path = None
        new_path = None

        if not self.is_valid(entry):
            return None

        def do_dump_blob(blob_id, contents):
            filename = "{}/{}".format(self.path_settings.blob_dir, blob_id)
            self.dump_blob(filename, contents)

        if entry.change_type != 'A':
            old_contents = entry.a_blob.data_stream.read()
            old_path = entry.a_path
            old_blob_id = str(entry.a_blob)
            do_dump_blob(old_blob_id, old_contents)

        if entry.change_type != 'D':
            new_contents = entry.b_blob.data_stream.read()
            new_path = entry.b_path
            new_blob_id = str(entry.b_blob)
            do_dump_blob(new_blob_id, new_contents)

        info = {'change_type': entry.change_type,
                'old_path': old_path,
                'new_path': new_path,
                'old_content': old_blob_id,
                'new_content': new_blob_id,
                'commit_id': str(commit),
                'author_name': self.remove_commas(commit.author.name),
                'author_email': commit.author.email,
                'committer_name': self.remove_commas(commit.committer.name),
                'committer_email': commit.committer.email,
                'author_time': commit.authored_date,
                'committer_time': commit.committed_date}

        return info

    @staticmethod
    def dump_blob(filename, contents):
        if os.path.exists(filename):
            return filename

        with open(filename, 'wb') as output:
            output.write(contents)

        return filename

    def get_changes(self, commit, parent):
        diff_index = parent.diff(commit)

        change_infos = []

        for entry in diff_index:
            info = self.extract_change_info(commit, entry)
            if info is not None:
                change_infos.append(info)

        return change_infos

    def process_commit(self, commit):
        parents = commit.parents
        if len(parents) != 1:
            return []

        parent = parents[0]
        return self.get_changes(commit, parent)

    def explode_repo(self):
        repo = Repo(self.path_settings.repo_path)

        total_commits = 0
        for _ in repo.iter_commits():
            total_commits += 1
            if total_commits % 10000 == 0:
                print("Counting commits: {}".format(total_commits))

        df = None
        processed_commits = set()

        os.makedirs(self.path_settings.blob_dir, exist_ok=True)

        if os.path.exists(self.path_settings.incomplete_repo_data_file):
            print("Found an incomplete dataset: {}".format(self.path_settings.incomplete_repo_data_file))
            df = pd.read_csv(self.path_settings.incomplete_repo_data_file, index_col=None, na_values='')
            print(df.info(memory_usage='deep', verbose=False))
            entries = df.to_dict('records')
            for e in entries:
                cid = e['commit_id']
                processed_commits.add(cid)

            # Not all commits are present in the dataframe (some contain no interesting changes).
            # To speed up incremental processing, we keep all commit ids that we've encountered on full processing
            if os.path.exists(self.path_settings.processed_commits_filename):
                print("Found a list of encountered commits: {}".format(self.path_settings.processed_commits_filename))
                processed_commits_df = pd.read_csv(self.path_settings.processed_commits_filename, index_col=None,
                                                   na_values='')
                commit_entries = processed_commits_df.to_dict('records')
                for ce in commit_entries:
                    processed_commits.add(ce['id'])
            print("Will ignore {} already processed commits".format(len(processed_commits)))

        limit = 1000000

        commits_to_process = min(limit, total_commits - len(processed_commits))
        print("{} commits in the repository. Processing {}".format(total_commits, commits_to_process))

        processed_count = 0

        prev_time = time.time()
        change_infos_chunk = []
        for c in repo.iter_commits():
            if str(c) in processed_commits:
                continue

            change_infos_chunk += self.process_commit(c)
            processed_count += 1
            processed_commits.add(str(c))

            if processed_count % 5000 == 0:
                print("Processed {} of {} commits\n".format(processed_count, commits_to_process))

                cur_time = time.time()
                print("Last 5000 processed in {} seconds".format(time.time() - prev_time))
                prev_time = cur_time

                new_data = False

                if df is None and len(change_infos_chunk) > 0:
                    df = DataFrame.from_records(change_infos_chunk)
                elif len(change_infos_chunk) > 0:
                    df = df.append(DataFrame.from_records(change_infos_chunk))
                    new_data = True

                if (df is not None) and new_data:
                    df.to_csv(self.path_settings.incomplete_repo_data_file, index=False)

                change_infos_chunk = []
                print(df.info(memory_usage='deep', verbose=False))
            if processed_count >= limit:
                break

        if len(change_infos_chunk) > 0:
            if df is not None:
                df = df.append(DataFrame.from_records(change_infos_chunk))
            else:
                df = DataFrame.from_records(change_infos_chunk)

        if df is not None:
            print(df.info(memory_usage='deep', verbose=False))
            df.to_csv(self.path_settings.full_repo_data_file, index=False)

        print("Dumping a list of {} already processed commits".format(len(processed_commits)))
        processed_commits_list = []
        for c in processed_commits:
            processed_commits_list.append({'id': c})

        commits_df = DataFrame.from_records(processed_commits_list)
        commits_df.to_csv(self.path_settings.processed_commits_filename, index=False)


def read_repo_names():
    with open("../git_projects.txt") as f:
        projects = f.readlines()
        return [p.strip() for p in projects]

# reponames = read_repo_names()

# for reponame in reponames:
#     repo_processor = RepositoryProcessor(reponame)
#     repo_processor.explode_repo()

import os
import time

import pandas as pd
from git import Repo
from joblib import Parallel, delayed, cpu_count
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
            return commit, []

        parent = parents[0]
        return commit, self.get_changes(commit, parent)

    @staticmethod
    def split_into_chunks(commits, chunk_size):
        return [commits[i:i + chunk_size] for i in range(0, len(commits), chunk_size)]

    def process_chunk(self, ind, n_jobs, processed_commits, repo):
        commits = [
            commit
            for i, commit in enumerate(repo.iter_commits())
            if str(commit) not in processed_commits and i % n_jobs == ind
        ]
        print("Thread {}: processing chunk of {} commits".format(ind, len(commits)))
        processed_batches = []
        for i, commit in enumerate(commits):
            processed_batches.append(self.process_commit(commit))
            if (i + 1) % 1000 == 0:
                print("Thread {}: processed {} of {} commits".format(ind, i + 1, len(commits)))
        processed_commits = set(str(commit) for commit, _ in processed_batches)
        change_infos = [change_info for _, batch in processed_batches for change_info in batch]
        return processed_commits, change_infos

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

        n_commits_to_process = min(limit, total_commits - len(processed_commits))
        print("{} commits in the repository. Processing {}".format(total_commits, n_commits_to_process))

        processed_count = 0

        prev_time = time.time()
        n_jobs = cpu_count() - 2
        with Parallel(n_jobs=n_jobs) as pool:
            processed_chunks = pool(
                delayed(self.process_chunk)(ind, n_jobs, processed_commits, repo) for ind in range(n_jobs)
            )
        cur_time = time.time()
        print("Finished processing in {} sec".format(cur_time - prev_time))

        for chunk in processed_chunks:
            processed_commits_chunk, change_infos_chunk = chunk
            processed_commits.update(processed_commits_chunk)
            processed_count += len(processed_commits_chunk)

            if df is None and len(change_infos_chunk) > 0:
                df = DataFrame.from_records(change_infos_chunk)
            elif len(change_infos_chunk) > 0:
                df = df.append(DataFrame.from_records(change_infos_chunk))

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

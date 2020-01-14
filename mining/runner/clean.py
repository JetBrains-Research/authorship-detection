import os
import shutil


def rm_folder(path):
    shutil.rmtree(path, ignore_errors=True)


def clean_repos():
    repos_root = "../pythonminer/data/repos"
    print("Cleaning repos in", repos_root)
    for filename in os.listdir(repos_root):
        path = os.path.join(repos_root, filename)
        if os.path.isdir(path):
            print("Removing", path)
            rm_folder(path)


def clean_exploded():
    print("Cleaning the exploded repos dir")
    rm_folder("../pythonminer/data/exploded")


def clean_out():
    print("Cleaning the processed data dir")
    rm_folder("../pythonminer/out")


clean_repos()
clean_exploded()
clean_out()

import os
import shutil


def rm_folder(path):
    shutil.rmtree(path, ignore_errors=True)


def clean_generated_data():
    out_root = "../gitminer/out"
    for filename in os.listdir(out_root):
        path = os.path.join(out_root, filename)
        if os.path.isdir(path):
            print("Cleaning generated data in", path)
            generated_root = os.path.join(path, "generated_data")
            if os.path.exists(generated_root):
                rm_folder(generated_root)


clean_generated_data()

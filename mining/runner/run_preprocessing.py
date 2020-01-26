import os
import subprocess

# At this point all necessary data should be in gitminer/out
os.chdir("../authorship_pipeline")
subprocess.run(["python3", "run_preprocessing.py", "--n_time_buckets", "3"])

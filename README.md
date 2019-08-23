# Authorship detection
This project contains files that reproduce evaluation of PbNN and PbRF models on the Java dataset suggested by [Yang et al.](https://pdfs.semanticscholar.org/f44a/e6e557d2532d1e2d95cf2b972a28c406ad4c.pdf)

For the sake of anonymity, we dropped part of the project that extracts path-based representations from code. 

Upon the publication we will release the reusable version of data collection pipeline along with the other datasets mentioned in the paper.

## Contents

* `datasets` folder contains raw Java code of 40 single-authored projects that form a dataset
* `processed` folder contains extracted tokens, paths, and path-contexts extracted from the dataset
* `attribution` folder contains Python code that reproduces our experiments
* `attribution/configurations` is a folder with configurations of PbRF and PbNN models used for evaluation
* `attribution/output` contains corresponding results for all the configurations
* `environment.yml` is a file used to create conda environment
* `attribution/run.sh` is a script that runs models on specified configurations from `attribution/configurations`

## Usage

To install conda, follow the [instructions](https://docs.conda.io/projects/conda/en/latest/user-guide/install/) for your system.

To create the environment run  `conda env create -f environment.yml`.

To run the model, go to `attribution` folder and run `./run.sh path_to_config`, for example:
```
./run.sh configurations/java40/rf/config_40_sqrt_300_10.yaml
```

If you want to evaluate multiple configurations, choose them with a regex:
```
./run.sh `configurations/java40/rf/config_40_sqrt_top_*.yaml`
```

Please note, that when `mutual_info_file` parameter is specified in the configuration, the script performs feature selection. Selection step may take several minutes.

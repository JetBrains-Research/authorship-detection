#!/usr/bin/env bash

for config in $1
do
    echo $config
    python3 run_classification.py $config
done
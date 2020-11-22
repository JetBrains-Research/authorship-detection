#!/usr/bin/env bash

for config in $1
do
    echo $config
    python -m run_classification $config
done
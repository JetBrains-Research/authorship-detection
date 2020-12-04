#!/usr/bin/env bash

for config in $@
do
    echo $config
    python -m run_classification $config
done
#!/usr/bin/env bash

for config in configurations/*
do
    echo $config
    python3 run_classification.py $config
done
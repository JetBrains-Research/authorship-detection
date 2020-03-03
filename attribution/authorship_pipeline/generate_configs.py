import os

import yaml

min_counts = {
    'ecs-logging-java': 1
}
max_counts = {
    'ecs-logging-java': 1000
}

configs_by_classifier = {
    'rf': [
        {
            'features': [
                'paths',
                'starts'
            ],
            'params': {
                'n_estimators': n_estimators,
                'n_jobs': -1,
                'min_samples_leaf': 1,
                'min_samples_split': 2,
                'max_features': max_features,
                'random_state': 239
            }
        }
        for n_estimators in [100, 300, 500]
        for max_features in ['log2', 'sqrt']
    ],
    'nn': [
        {
            'log_batches': 10,
            'learning_rate': lr,
            'hidden_dim': hidden_dim,
            'batch_size': 1024,
            'epochs': 20
        }
        for lr in [0.01, 0.005]
        for hidden_dim in [16, 42, 64]
    ],
    'caliskan': [
        {
            'feature_count': feature_count,
            'params': {
                'n_estimators': n_estimators,
                'n_jobs': -1,
                'min_samples_leaf': 1,
                'min_samples_split': 2,
                'max_features': max_features,
                'random_state': 239
            }
        }
        for feature_count in [2500, 5000, 10000]
        for n_estimators in [100, 300, 500]
        for max_features in ['log2', 'sqrt']
    ]
}


def generate_configs(p):
    path = os.path.join("..", "gitminer", "out", p)

    output_dir = os.path.join("configs", p)
    classifier_types = ["nn", "rf", "caliskan"]
    seed = 239
    modes = ["time", "context"]

    for classifier_type in classifier_types:
        for mode in modes:
            output_path = os.path.join(output_dir, classifier_type)
            if not os.path.exists(output_path):
                os.makedirs(output_path)

            basic_config = {
                'source_folder': path,
                'seed': seed,
                'classifier_type': classifier_type,
                'mode': mode,
                'min_count': min_counts[p],
                'max_count': max_counts[p]
            }
            if mode == 'time':
                basic_config['time_folds'] = 10

            for i, classifier_config in enumerate(configs_by_classifier[classifier_type]):
                final_config = {**basic_config, **classifier_config}
                yaml.dump(
                    final_config,
                    open(os.path.join(output_path, f'config_{i + 1}.yaml'), 'w'),
                    default_flow_style=False
                )


projects_file = os.path.join("..", "projects.txt")
projects = [l.strip() for l in open(projects_file, "r").readlines()]
for p in projects:
    generate_configs(p)

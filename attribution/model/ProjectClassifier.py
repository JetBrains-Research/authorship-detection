from torch import nn

from model.CodeVectorizer import CodeVectorizer


class ProjectClassifier(nn.Module):

    def __init__(self, n_tokens, n_paths, dim, n_classes):
        super(ProjectClassifier, self).__init__()
        self.vectorization = CodeVectorizer(n_tokens, n_paths, dim)
        self.classifier = nn.Linear(dim, n_classes)

    def forward(self, contexts):
        vectorized_contexts = self.vectorization(contexts)
        predictions = self.classifier(vectorized_contexts)
        # predictions = predictions.squeeze(-1)
        return predictions

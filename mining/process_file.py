import ast
import ruamel.yaml
import sys

filename = sys.argv[1]
f = open(filename, 'r')
tree = ast.parse(f.read(), filename)
ruamel.yaml.dump(tree, open(sys.argv[2], 'w'), default_flow_style=False)

import networkx
from collections import defaultdict
from networkx import Graph
from harmonic_centrality import harmonic_centrality

def harmonic_centrality_set(G, max_distance=6):
    harmonic = defaultdict(lambda: 0.)
    t_steps_set = defaultdict(lambda: defaultdict(lambda: set()))
    for node in G.nodes_iter():
        t_steps_set[node][0].add(str(node))
    for distance in range(1, max_distance + 1):
        for node in G.nodes_iter():
            t_steps_set[node][distance].update(t_steps_set[node][distance-1])
            for next_node in networkx.all_neighbors(G, node):
                t_steps_set[node][distance].update(t_steps_set[next_node][distance - 1])
            harmonic[node] += float((len(t_steps_set[node][distance]) - len(t_steps_set[node][distance - 1])))/distance
    return dict(harmonic)

G = Graph()
G.add_edge(1,2)
G.add_edge(1,3)
G.add_edge(1,4)
G.add_edge(2,3)
harmonic = harmonic_centrality_set(G)
harmonic_hll = harmonic_centrality(G)


print "set"
for node in harmonic:
    print node, harmonic[node]

print "\nhll"
for node in harmonic_hll:
    print node, harmonic[node]

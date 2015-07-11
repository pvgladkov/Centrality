from networkx import Graph
from networkx.algorithms.centrality import harmonic_centrality
from harmonic_centrality import harmonic_centrality as hc_hll


def comp(graph):
    harmonic = harmonic_centrality(graph)
    harmonic_hll = hc_hll.harmonic_centrality(graph)
    print("networkx")
    for node in harmonic:
        print(node, harmonic[node])

    print("\nhll")
    for node in harmonic_hll:
        print(node, harmonic[node])

if __name__ == "__main__":

    G = Graph()
    G.add_edge(1, 2)
    G.add_edge(1, 3)
    G.add_edge(1, 4)
    G.add_edge(2, 3)
    comp(G)

    G = Graph()
    G.add_edge(1, 2)
    G.add_edge(2, 3)
    G.add_edge(3, 4)
    G.add_edge(2, 3)
    G.add_edge(2, 4)
    G.add_edge(5, 1)
    comp(G)


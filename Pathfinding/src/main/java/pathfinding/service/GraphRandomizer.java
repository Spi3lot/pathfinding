package pathfinding.service;

import pathfinding.domain.Graph;

import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Randomizes edges between vertices of a graph.
 *
 * @param <T> the type of the value each vertex holds
 */
public class GraphRandomizer<T> {

    private static final Random RANDOM = new Random();
    private final List<T> vertices;

    /**
     * @param vertices the vertices to randomize edges between
     */
    public GraphRandomizer(Collection<T> vertices) {
        this.vertices = List.copyOf(vertices);
    }

    /**
     * Randomizes edges between vertices of a graph.
     *
     * @param directed        whether the graph should be directed or not
     * @param edgeProbability the probability of an edge being created between two vertices
     * @return the randomized graph
     */
    public Graph<T> randomizeEdges(boolean directed,
                                   double edgeProbability) {
        var graph = createFilledGraph(directed);

        return (directed)
                ? randomizeDirectedEdges(graph, edgeProbability)
                : randomizeUndirectedEdges(graph, edgeProbability);
    }

    private Graph<T> createFilledGraph(boolean directed) {
        var graph = new Graph<T>(directed);
        graph.addVertices(vertices);
        return graph;
    }

    private Graph<T> randomizeUndirectedEdges(Graph<T> graph,
                                              double edgeProbability) {
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                if (random() < edgeProbability) {
                    graph.addEdge(vertices.get(i), vertices.get(j));
                }
            }
        }

        return graph;
    }

    private Graph<T> randomizeDirectedEdges(Graph<T> graph,
                                            double edgeProbability) {
        for (var source : vertices) {
            for (var destination : vertices) {
                if (source != destination && random() < edgeProbability) {
                    graph.addEdge(source, destination);
                }
            }
        }

        return graph;
    }

    private double random() {
        return RANDOM.nextDouble();
    }

}

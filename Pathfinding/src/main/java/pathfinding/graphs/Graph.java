package pathfinding.graphs;

import java.util.List;
import java.util.Map;

/**
 * An interface that helps to define the behavior of a graph.
 *
 * @param <T> the type of the vertices in the graph
 */
public interface Graph<T> {

    /**
     * @param vertex the vertex to get the neighbors of
     * @return a Map of the neighbors of the vertex and the weights of the edges between them
     */
    Map<T, Double> getNeighbors(T vertex);

    /**
     * @param source      the source vertex of the edge
     * @param destination the destination vertex of the edge
     * @return the weight of the edge between the two vertices
     */
    default double getEdgeWeight(T source, T destination) {
        return getNeighbors(source)
                .getOrDefault(destination, Double.POSITIVE_INFINITY);
    }

    /**
     * @param path the path to calculate the total weight of
     * @return the accumulated weight of the path
     */
    default double sumEdgeWeights(List<T> path) {
        double sum = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            sum += getEdgeWeight(path.get(i), path.get(i + 1));
        }

        return sum;
    }

}

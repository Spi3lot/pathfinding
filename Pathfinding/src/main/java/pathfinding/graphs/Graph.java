package pathfinding.graphs;

import java.util.List;
import java.util.Map;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 19.02.2024, Mo.
 **/
public interface Graph<T> {

    /**
     * @param path the path to calculate the total weight of
     * @return the accumulated weight of the path
     */
    default double sumEdgeWeights(List<T> path) {
        double weight = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            weight += getEdgeWeight(path.get(i), path.get(i + 1));
        }

        return weight;
    }

    /**
     * @param source      the source vertex of the edge
     * @param destination the destination vertex of the edge
     * @return the weight of the edge between the two vertices
     */
    default double getEdgeWeight(T source, T destination) {
        return getNeighbors(source).getOrDefault(destination, Double.POSITIVE_INFINITY);
    }

    Map<T, Double> getNeighbors(T vertex);

}

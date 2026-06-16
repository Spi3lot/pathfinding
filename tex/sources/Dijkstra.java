package pathfinding.algorithms;

import pathfinding.service.EndCondition;

import java.util.Map;

/**
 * Implementation of the Dijkstra algorithm for finding the shortest path.
 *
 * @param <T> the type of the vertices in the graph
 */
public class Dijkstra<T> extends AbstractBestFirstSearch<T> {

    @Override
    public double g(T vertex, Map<T, Double> distances) {
        return distances.getOrDefault(vertex, Double.POSITIVE_INFINITY);
    }

    @Override
    public double h(T vertex, EndCondition<T> endCondition) {
        return 0;
    }

}

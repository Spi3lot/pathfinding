package pathfinding.algorithms;

import pathfinding.graphs.Graph;
import pathfinding.service.EndCondition;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface for best-first search algorithms.
 *
 * @param <T> the type of the vertices in the graph
 */
public interface BestFirstSearch<T> extends PathfindingAlgorithm<T> {

    Map<T, Double> getDistances();

    Map<T, T> getPredecessors();

    T getCurrent();

    void closeCurrent();

    boolean nextOpen();

    boolean hasVisited(T vertex);

    boolean hasOpen();

    void initializeDataStructures(T start);

    Map<T, Double> expand(EndCondition<T> endCondition, Graph<T> graph);

    double g(T vertex, Map<T, Double> distances);

    double h(T vertex, EndCondition<T> endCondition);

    default boolean nextUnvisited() {
        while (nextOpen()) {
            if (!hasVisited(getCurrent())) {
                return true;
            }
        }

        return false;
    }

    default double g() {
        return g(getCurrent());
    }

    default double g(T vertex) {
        return g(vertex, getDistances());
    }

    default double f(T vertex, EndCondition<T> endCondition) {
        return g(vertex) + h(vertex, endCondition);
    }

}

package pathfinding.algorithms;

import pathfinding.graphs.Graph;
import pathfinding.service.EndCondition;

import java.util.Map;

/**
 * Interface for best-first search algorithms.
 *
 * @param <T> the type of the vertices in the graph
 */
public interface BestFirstSearch<T> extends PathfindingAlgorithm<T> {

    Map<T, T> getPredecessors();

    T getCurrent();

    void updateCurrent();

    void closeCurrent();

    void initializeDataStructures(T start);

    boolean hasOpen();

    boolean hasVisited(T vertex);

    void expand(EndCondition<T> endCondition, Graph<T> graph);

    double g(T vertex, Map<T, Double> distances);

    double h(T vertex, EndCondition<T> endCondition);

}

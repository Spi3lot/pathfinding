package pathfinding.algorithms;

import pathfinding.graphs.Graph;
import pathfinding.service.EndCondition;

import java.util.Collections;
import java.util.List;

public interface PathfindingAlgorithm<T> extends SearchAlgorithm<T> {

    /**
     * Finds the shortest path between two given vertices in a graph.
     *
     * @param start        the vertex the path starts at
     * @param endCondition the condition that has to
     *                     be met for the path to end
     * @param graph        the graph in which a path is to be found
     * @return the shortest path between the two vertices,
     * or {@link Collections#emptyList()} if no path exists.
     */
    List<T> findShortestPath(T start,
                             EndCondition<T> endCondition,
                             Graph<T> graph);

    default List<T> findAnyPath(T start,
                                EndCondition<T> endCondition,
                                Graph<T> graph) {
        return findShortestPath(start, endCondition, graph);
    }

}

package pathfinding.algorithms;

import pathfinding.graphs.Graph;
import pathfinding.service.EndCondition;

import java.util.List;
import java.util.Optional;

public interface PathfindingAlgorithm<T> {

    /**
     * Searches any path between two given vertices in a graph.
     *
     * @param start        the vertex the path starts at
     * @param endCondition the condition that has to be met for the path to end
     * @param graph        the graph in which a path is to be found
     * @return Any path between the two vertices, or {@link Optional#empty()} if no path exists.
     */
    default List<T> findAnyPath(T start,
                                EndCondition<T> endCondition,
                                Graph<T> graph) {
        return findShortestPath(start, endCondition, graph);
    }

    /**
     * Searches the shortest path between two given vertices in a graph.
     *
     * @param start        the vertex the path starts at
     * @param endCondition the condition that has to be met for the path to end
     * @return The shortest path between the two vertices, or {@link Optional#empty()} if no path exists.
     */
    List<T> findShortestPath(T start,
                             EndCondition<T> endCondition,
                             Graph<T> graph);


}

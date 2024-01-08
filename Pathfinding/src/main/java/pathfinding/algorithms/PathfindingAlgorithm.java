package pathfinding.algorithms;

import pathfinding.graphs.Graph;

import java.util.List;
import java.util.Optional;

public interface PathfindingAlgorithm<T> {

    /**
     * Finds any path between two given vertices in a graph.
     *
     * @param start the vertex the path starts at
     * @param end the vertex the path ends at
     * @param graph the graph in which a path is to be found
     * @return Any path between the two vertices, or <code>Optional.empty</code> if no path exists.
     */
    default Optional<List<T>> findAnyPath(T start,
                                          T end,
                                          Graph<T> graph) {
        return findShortestPath(start, end, graph);
    }

    /**
     * Finds the shortest path between two given vertices in a graph.
     *
     * @param start the vertex the path starts at
     * @param end   the vertex the path ends at
     * @param graph the graph in which the shortest path is to be found
     * @return The shortest path between the two vertices, or <code>Optional.empty</code> if no path exists.
     */
    Optional<List<T>> findShortestPath(T start,
                                       T end,
                                       Graph<T> graph);


}

package pathfinding.algorithms;

import pathfinding.graphs.Graph;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public interface PathfindingAlgorithm<T> {

    /**
     * Searches any path between two given vertices in a graph.
     * @param start the vertex the path starts at
     * @param end the vertex the path ends at
     * @param graph the graph in which a path is to be found
     * @return Any path between the two vertices, or {@link Optional#empty()} if no path exists.
     */
    default Optional<List<T>> findAnyPath(T start,
                                          T end,
                                          Graph<T> graph) {
        return findAnyPath(start, vertex -> vertex.equals(end), graph);
    }

    /**
     * Searches any path between two given vertices in a graph.
     *
     * @param start the vertex the path starts at
     * @param endCondition the condition that has to be met for the path to end
     * @param graph the graph in which a path is to be found
     * @return Any path between the two vertices, or {@link Optional#empty()} if no path exists.
     */
    default Optional<List<T>> findAnyPath(T start,
                                          Predicate<T> endCondition,
                                          Graph<T> graph) {
        return findShortestPath(start, endCondition, graph);
    }

    /**
     * Searches the shortest path between two given vertices in a graph.
     *
     * @param start the vertex the path starts at
     * @param end   the vertex the path ends at
     * @param graph the graph in which the shortest path is to be found
     * @return The shortest path between the two vertices, or {@link Optional#empty()} if no path exists.
     */
    default Optional<List<T>> findShortestPath(T start,
                                               T end,
                                               Graph<T> graph) {
        return findShortestPath(start, vertex -> vertex.equals(end), graph);
    }

    /**
     * Searches the shortest path between two given vertices in a graph.
     *
     * @param start        the vertex the path starts at
     * @param endCondition the condition that has to be met for the path to end
     * @return The shortest path between the two vertices, or {@link Optional#empty()} if no path exists.
     */
    Optional<List<T>> findShortestPath(T start,
                                       Predicate<T> endCondition,
                                       Graph<T> graph);


}

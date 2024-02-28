package pathfinding.algorithms;

import pathfinding.functions.Heuristic;
import pathfinding.graphs.Graph;
import pathfinding.service.EndCondition;

import java.util.List;

/**
 * Implementation of the Dijkstra algorithm for finding the shortest path.
 *
 * @param <T> the type of the vertices in the graph
 */
public class Dijkstra<T> implements PathfindingAlgorithm<T> {

    private final Heuristic<T> zero = (_, _) -> 0;
    private final PathfindingAlgorithm<T> algorithm = new AStar<>(zero);

    @Override
    public List<T> findAnyPath(T start,
                               EndCondition<T> endCondition,
                               Graph<T> graph) {
        return algorithm.findAnyPath(start, endCondition, graph);
    }

    @Override
    public List<T> findShortestPath(T start,
                                    EndCondition<T> endCondition,
                                    Graph<T> graph) {
        return algorithm.findShortestPath(start, endCondition, graph);
    }

}

package pathfinding.algorithms;

import pathfinding.graphs.Graph;
import pathfinding.service.EndCondition;

import java.util.List;

/**
 * Implementation of the Dijkstra algorithm for finding the shortest path.
 *
 * @param <T> the type of the vertices in the graph
 */
public class Dijkstra<T> implements PathfindingAlgorithm<T> {

    private final AStar<T> aStar = new AStar<>((_, _) -> 0.0);

    @Override
    public List<T> findAnyPath(T start,
                               EndCondition<T> endCondition,
                               Graph<T> graph) {
        return aStar.findAnyPath(start, endCondition, graph);
    }

    @Override
    public List<T> findShortestPath(T start,
                                    EndCondition<T> endCondition,
                                    Graph<T> graph) {
        return aStar.findShortestPath(start, endCondition, graph);
    }

}

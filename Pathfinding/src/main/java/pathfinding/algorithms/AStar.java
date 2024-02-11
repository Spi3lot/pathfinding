package pathfinding.algorithms;

import pathfinding.graphs.Graph;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 *
 * @param <T>
 */
public class AStar<T> implements PathfindingAlgorithm<T> {

    @Override
    public Optional<List<T>> findShortestPath(T start,
                                              T end,
                                              Graph<T> graph) {
        var distances = new HashMap<T, Double>();

    }

}

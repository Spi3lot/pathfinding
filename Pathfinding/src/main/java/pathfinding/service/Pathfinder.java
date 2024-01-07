package pathfinding.service;

import lombok.AllArgsConstructor;
import pathfinding.algorithms.PathfindingAlgorithm;
import pathfinding.graphs.Graph;

import java.util.List;
import java.util.Optional;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 06.01.2024, Sa.
 */
@AllArgsConstructor
public class Pathfinder<T> {

    private final Graph<T> graph;
    private final PathfindingAlgorithm<T> algorithm;

    public Optional<List<T>> findShortestPath(T start, T end) {
        return algorithm.findShortestPath(start, end, graph);
    }

}

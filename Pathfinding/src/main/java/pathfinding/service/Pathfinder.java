package pathfinding.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pathfinding.algorithms.PathfindingAlgorithm;
import pathfinding.graphs.Graph;

import java.util.List;
import java.util.Optional;

/**
 * Utility class so the graph doesn't have to be passed to the algorithm every time.
 * Also, the type T of the algorithm doesn't have to be specified, as it can be inferred from the graph.
 */
@AllArgsConstructor
@Getter
@Setter
public class Pathfinder<T> {

    private Graph<T> graph;
    private PathfindingAlgorithm<T> algorithm;

    public Optional<List<T>> findAnyPath(T start, T end) {
        return algorithm.findAnyPath(start, end, graph);
    }

    public Optional<List<T>> findShortestPath(T start, T end) {
        return algorithm.findShortestPath(start, end, graph);
    }

}

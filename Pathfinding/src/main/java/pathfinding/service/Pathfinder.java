package pathfinding.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pathfinding.algorithms.PathfindingAlgorithm;
import pathfinding.graphs.Graph;

import java.util.List;
import java.util.Optional;

/**
 * Utility class so the graph doesn't have
 * to be passed to the algorithm every time.
 * Also, the type T only has to be specified once,
 * as the objects not specifying it
 * can infer it from the object that specified it.
 */
@AllArgsConstructor
@Getter
@Setter
public class Pathfinder<T> {

    private Graph<T> graph;
    private PathfindingAlgorithm<T> algorithm;

    public Optional<List<T>> findAnyPath(T start, EndCondition<T> endCondition) {
        return algorithm.findAnyPath(start, endCondition, graph);
    }

    public Optional<List<T>> findShortestPath(T start, EndCondition<T> endCondition) {
        return algorithm.findShortestPath(start, endCondition, graph);
    }

}

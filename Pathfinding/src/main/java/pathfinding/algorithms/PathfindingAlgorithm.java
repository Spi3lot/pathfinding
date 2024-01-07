package pathfinding.algorithms;

import pathfinding.graphs.Graph;

import java.util.List;
import java.util.Optional;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 06.01.2024, Sa.
 */
public interface PathfindingAlgorithm<T> {

    Optional<List<T>> findShortestPath(T start, T end, Graph<T> graph);

}

package pathfinding.algorithms;

import pathfinding.graphs.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 06.01.2024, Sa.
 */
public class RecursiveDFS<T> implements PathfindingAlgorithm<T> {

    @Override
    public Optional<List<T>> findShortestPath(T start, T end, Graph<T> graph) {
        return findShortestPath(start, end, graph, List.of(start));
    }

    private Optional<List<T>> findShortestPath(T start, T end, Graph<T> graph, List<T> path) {
        if (start.equals(end)) {
            return Optional.of(path);
        }

        System.out.println("Current path: " + path);

        return graph
                .getNeighbors(start)
                .keySet()
                .stream()
                .filter(neighbor -> !path.contains(neighbor))
                .map(neighbor -> findShortestPath(neighbor, end, graph, append(path, neighbor)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min(Comparator.comparingDouble(graph::calcAccumulatedPathWeight));
    }

    private List<T> append(List<T> list, T element) {
        var appended = new ArrayList<>(list);
        appended.add(element);
        return appended;
    }

}

package pathfinding.algorithms;

import pathfinding.graphs.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DFS<T> implements PathfindingAlgorithm<T> {

    @Override
    public Optional<List<T>> findAnyPath(T start,
                                         T end,
                                         Graph<T> graph) {
        return findAnyPath(start, end, graph, List.of(start));
    }

    private Optional<List<T>> findAnyPath(T start,
                                          T end,
                                          Graph<T> graph,
                                          List<T> path) {
        if (start.equals(end)) {
            return Optional.of(path);
        }

        return graph.getNeighbors(start)
                .keySet()
                .stream()
                .filter(neighbor -> !path.contains(neighbor))
                .map(neighbor -> findAnyPath(neighbor, end, graph, append(path, neighbor)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findAny();
    }

    @Override
    public Optional<List<T>> findShortestPath(T start,
                                              T end,
                                              Graph<T> graph) {
        return findShortestPath(start, end, graph, List.of(start));
    }

    private Optional<List<T>> findShortestPath(T start,
                                               T end,
                                               Graph<T> graph,
                                               List<T> path) {
        if (start.equals(end)) {
            return Optional.of(path);
        }

        return graph
                .getNeighbors(start)
                .keySet()
                .stream()
                .filter(neighbor -> !path.contains(neighbor))
                .map(neighbor -> findShortestPath(neighbor, end, graph, append(path, neighbor)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min(Comparator.comparingDouble(graph::calculateAccumulatedPathWeight));
    }

    private List<T> append(List<T> list, T element) {
        var appended = new ArrayList<>(list);
        appended.add(element);
        return appended;
    }

}

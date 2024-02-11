package pathfinding.algorithms;

import pathfinding.graphs.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Recursive implementation of the Depth-First Search algorithm.
 * <p>
 * For the iterative implementation, see {@link DepthFirstSearch}.
 * <p>
 * The interal workings are much simpler to understand in
 * this implementation, but it is prone to stack overflow errors
 * for large graphs.
 *
 * @param <T> the type of the nodes in the graph to be searched
 */
public class RecursiveDFS<T> implements PathfindingAlgorithm<T> {


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
                .map(neighbor -> findAnyPath(
                        neighbor,
                        end,
                        graph,
                        append(path, neighbor)
                ))
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

        return graph.getNeighbors(start)
                .keySet()
                .stream()
                .filter(neighbor -> !path.contains(neighbor))
                .map(neighbor -> findShortestPath(
                        neighbor,
                        end, graph,
                        append(path, neighbor)
                ))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min(Comparator.comparingDouble(graph::sumEdgeWeights));
    }

    private List<T> append(List<T> list, T element) {
        var appended = new ArrayList<>(list);
        appended.add(element);
        return appended;
    }

}

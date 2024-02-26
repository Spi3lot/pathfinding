package pathfinding.algorithms;

import pathfinding.graphs.Graph;
import pathfinding.service.EndCondition;

import java.util.*;

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
    public List<T> findAnyPath(T start,
                                         EndCondition<T> endCondition,
                                         Graph<T> graph) {
        return findAnyPath(start, endCondition, graph, List.of(start));
    }

    private List<T> findAnyPath(T start,
                                          EndCondition<T> endCondition,
                                          Graph<T> graph,
                                          List<T> path) {
        if (endCondition.condition().test(start)) {
            return path;
        }

        return graph.getNeighbors(start)
                .keySet()
                .stream()
                .filter(neighbor -> !path.contains(neighbor))
                .map(neighbor -> findAnyPath(
                        neighbor,
                        endCondition,
                        graph,
                        append(path, neighbor)
                ))
                .filter(list -> !list.isEmpty())
                .findAny()
                .orElse(Collections.emptyList());
    }

    @Override
    public List<T> findShortestPath(T start,
                                    EndCondition<T> endCondition,
                                    Graph<T> graph) {
        return findShortestPath(start, endCondition, graph, List.of(start));
    }

    private List<T> findShortestPath(T start,
                                               EndCondition<T> endCondition,
                                               Graph<T> graph,
                                               List<T> path) {
        if (endCondition.condition().test(start)) {
            return path;
        }

        return graph.getNeighbors(start)
                .keySet()
                .stream()
                .filter(neighbor -> !path.contains(neighbor))
                .map(neighbor -> findShortestPath(
                        neighbor,
                        endCondition,
                        graph,
                        append(path, neighbor)
                ))
                .filter(list -> !list.isEmpty())
                .min(Comparator.comparingDouble(graph::sumEdgeWeights))
                .orElse(Collections.emptyList());
    }

    private List<T> append(List<T> list, T element) {
        var appended = new ArrayList<>(list);
        appended.add(element);
        return appended;
    }

}

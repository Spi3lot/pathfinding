package pathfinding.algorithms;

import pathfinding.graphs.Graph;
import pathfinding.service.EndCondition;
import pathfinding.service.PathTracer;

import java.util.*;

/**
 * Iterative implementation of the Breadth-First Search algorithm.
 *
 * @param <T> the type of the nodes in the graph to be searched
 */
public class BreadthFirstSearch<T> implements PathfindingAlgorithm<T> {

    @Override
    public Optional<List<T>> findShortestPath(T start,
                                              EndCondition<T> endCondition,
                                              Graph<T> graph) {
        var queue = new ArrayDeque<T>();
        var visited = new ArrayList<T>();
        var predecessors = new HashMap<T, T>();
        queue.add(start);

        while (!queue.isEmpty()) {
            var current = queue.poll();
            visited.add(current);

            if (endCondition.condition().test(current)) {
                var pathTracer = new PathTracer<>(predecessors);
                return Optional.of(pathTracer.unsafeTrace(start, current));
            }

            graph.getNeighbors(current)
                    .keySet()
                    .stream()
                    .filter(neighbor -> !visited.contains(neighbor))
                    .filter(neighbor -> !queue.contains(neighbor))
                    .forEach(neighbor -> {
                        queue.offer(neighbor);
                        predecessors.put(neighbor, current);
                    });
        }

        return Optional.empty();
    }

}

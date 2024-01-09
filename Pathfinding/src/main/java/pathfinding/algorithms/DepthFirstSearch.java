package pathfinding.algorithms;

import pathfinding.graphs.Graph;
import pathfinding.service.PathTracer;

import java.util.*;

/**
 * Iterative implementation of the Depth-First Search algorithm.
 * <p>
 * For the recursive implementation, see {@link RecursiveDFS}.
 * <p>
 * The internal workings are much more complex in this implementation,
 * but it is also much more performance and memory efficient for
 * large graphs. It does not suffer from stack overflow errors,
 * unlike the recursive implementation.
 *
 * @param <T> the type of the nodes in the graph to be searched
 */
public class DepthFirstSearch<T> implements PathfindingAlgorithm<T> {

    @Override
    public Optional<List<T>> findAnyPath(T start,
                                         T end,
                                         Graph<T> graph) {
        var predecessors = new HashMap<T, T>();
        var stack = new ArrayDeque<T>();
        var visited = new ArrayList<T>();
        boolean found = false;
        stack.push(start);

        while (!stack.isEmpty()) {
            var current = stack.pop();
            visited.add(current);

            if (current.equals(end)) {
                found = true;
                break;
            }

            graph.getNeighbors(current)
                    .keySet()
                    .stream()
                    .filter(neighbor -> !visited.contains(neighbor))
                    .filter(neighbor -> !stack.contains(neighbor))
                    .forEach(neighbor -> {
                        stack.push(neighbor);
                        predecessors.put(neighbor, current);
                    });
        }

        if (found) {
            var pathTracer = new PathTracer<>(predecessors);
            return Optional.of(pathTracer.trace(start, end));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<T>> findShortestPath(T start,
                                              T end,
                                              Graph<T> graph) {
        var predecessors = new HashMap<T, T>();
        var stack = new ArrayDeque<T>();
        var paths = new ArrayList<List<T>>();
        var pathTracer = new PathTracer<>(predecessors);
        stack.push(start);

        while (!stack.isEmpty()) {
            var current = stack.pop();
            var path = pathTracer.trace(start, current);

            if (current.equals(end)) {
                paths.add(path);
                continue;
            }

            graph.getNeighbors(current)
                    .keySet()
                    .stream()
                    .filter(neighbor -> !path.contains(neighbor))
                    .filter(neighbor -> !stack.contains(neighbor))
                    .forEach(neighbor -> {
                        stack.push(neighbor);
                        predecessors.put(neighbor, current);
                    });
        }

        return paths.stream()
                .min(Comparator.comparingDouble(
                        graph::calculateAccumulatedPathWeight
                ));
    }

}

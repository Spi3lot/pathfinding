package pathfinding.algorithms;

import pathfinding.graphs.Graph;
import pathfinding.service.PathTracer;

import java.util.*;

/**
 * Iterative implementation of the Depth-First Search algorithm.
 * @param <T> the type of the nodes in the graph to be searched
 */
public class DFS<T> implements PathfindingAlgorithm<T> {

    @Override
    public Optional<List<T>> findAnyPath(T start, T end, Graph<T> graph) {
        var stack = new ArrayDeque<T>();
        var visited = new ArrayList<T>();
        var predecessors = new HashMap<T, T>();
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
    public Optional<List<T>> findShortestPath(T start, T end, Graph<T> graph) {
        var stack = new ArrayDeque<T>();
        var visited = new ArrayList<T>();
        var predecessors = new HashMap<T, T>();
        var paths = new ArrayList<List<T>>();
        var pathTracer = new PathTracer<>(predecessors);
        stack.push(start);

        while (!stack.isEmpty()) {
            var current = stack.pop();
            visited.add(current);
            System.out.println(current);

            if (current.equals(end)) {
                paths.add(pathTracer.trace(start, end));
            }

            graph.getNeighbors(current)
                    .keySet()
                    .stream()
                    .filter(neighbor -> !visited.contains(neighbor))
                    .filter(neighbor -> !stack.contains(neighbor))
                    .forEach(neighbor -> {
                        stack.push(neighbor);
                        predecessors.put(neighbor, current);
                        System.out.println(stack);
                        System.out.println(predecessors);
                    });
        }

        System.out.println(paths);

        return paths.stream()
                .min(Comparator.comparingDouble(graph::calculateAccumulatedPathWeight));
    }

}

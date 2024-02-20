package pathfinding.algorithms;

import pathfinding.graphs.Graph;
import pathfinding.service.PathTracer;

import java.util.*;
import java.util.function.Predicate;

/**
 * Abstract class to make the implementation of best-first search algorithms easier.
 *
 * @param <T> the type of the vertices in the graph
 */
public abstract class BestFirstSearch<T> implements PathfindingAlgorithm<T> {

    protected final Map<T, Double> distances = new HashMap<>();
    private final Map<T, T> predecessors = new HashMap<>();
    private final PathTracer<T> pathTracer = new PathTracer<>(predecessors);

    @Override
    public Optional<List<T>> findShortestPath(T start,
                                              T end,
                                              Graph<T> graph) {
        var queue = new PriorityQueue<>(createComparator(end));
        setupDataStructures(start, queue);

        while (!queue.isEmpty()) {
            var current = queue.poll();

            if (current == end) {
                return Optional.of(pathTracer.unsafeTrace(start, end));
            }

            graph.getNeighbors(current)
                    .forEach((neighbor, weight) -> updateIfBetter(
                            neighbor,
                            current,
                            queue,
                            f(current, end) + weight,
                            f(neighbor, end)
                    ));
        }

        return Optional.empty();
    }

    @Override
    public Optional<List<T>> findShortestPath(T start,
                                              Predicate<T> endCondition,
                                              Graph<T> graph) {
        var queue = new PriorityQueue<>(createComparator());
        setupDataStructures(start, queue);

        while (!queue.isEmpty()) {
            var current = queue.poll();

            if (endCondition.test(current)) {
                return Optional.of(pathTracer.unsafeTrace(start, current));
            }

            graph.getNeighbors(current)
                    .forEach((neighbor, weight) -> updateIfBetter(
                            neighbor,
                            current,
                            queue,
                            g(current) + weight,
                            g(neighbor)
                    ));
        }

        return Optional.empty();
    }

    private Comparator<T> createComparator(T end) {
        return Comparator.<T>comparingDouble(vertex -> f(vertex, end))
                .thenComparingDouble(vertex -> h(vertex, end));
    }

    private Comparator<T> createComparator() {
        return Comparator.comparingDouble(this::g);
    }

    private void setupDataStructures(T start, Queue<T> queue) {
        predecessors.clear();
        distances.clear();
        distances.put(start, 0.0);
        queue.add(start);
    }

    private void updateIfBetter(T neighbor,
                                T current,
                                Queue<T> queue,
                                double newDistance,
                                double distance) {
        if (newDistance < distance) {
            distances.put(neighbor, newDistance);
            predecessors.put(neighbor, current);
            queue.remove(neighbor);
            queue.offer(neighbor);
        }
    }

    protected double f(T vertex, T end) {
        return g(vertex) + h(vertex, end);
    }

    protected abstract double g(T vertex);

    protected abstract double h(T vertex, T end);

}

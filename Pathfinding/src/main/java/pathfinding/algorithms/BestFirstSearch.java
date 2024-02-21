package pathfinding.algorithms;

import pathfinding.graphs.Graph;
import pathfinding.service.EndCondition;
import pathfinding.service.PathTracer;

import java.util.*;

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
                                              EndCondition<T> endCondition,
                                              Graph<T> graph) {
        var queue = new PriorityQueue<>(
                Comparator.<T>comparingDouble(vertex -> f(vertex, endCondition))
                        .thenComparingDouble(vertex -> h(vertex, endCondition))
        );

        predecessors.clear();
        distances.clear();
        distances.put(start, 0.0);
        queue.add(start);

        while (!queue.isEmpty()) {
            var current = queue.poll();

            if (endCondition.condition().test(current)) {
                return Optional.of(pathTracer.unsafeTrace(start, current));
            }

            graph.getNeighbors(current)
                    .forEach((neighbor, weight) -> {
                        double newDistance = f(current, endCondition) + weight;
                        double distance = f(neighbor, endCondition);

                        if (newDistance < distance) {
                            distances.put(neighbor, newDistance);
                            predecessors.put(neighbor, current);
                            queue.remove(neighbor);
                            queue.offer(neighbor);
                        }
                    });
        }

        return Optional.empty();
    }

    protected double f(T vertex, EndCondition<T> endCondition) {
        return g(vertex) + h(vertex, endCondition);
    }

    protected abstract double g(T vertex);

    protected abstract double h(T vertex, EndCondition<T> endCondition);

}

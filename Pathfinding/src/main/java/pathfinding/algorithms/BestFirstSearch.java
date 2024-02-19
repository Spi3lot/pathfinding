package pathfinding.algorithms;

import pathfinding.graphs.Graph;
import pathfinding.service.PathTracer;

import java.util.*;

/**
 * Abstract class to make the implementation of best-first search algorithms easier.
 *
 * @param <T> the type of the vertices in the graph
 */
public abstract class BestFirstSearch<T> implements PathfindingAlgorithm<T> {

    protected final Map<T, Double> distances = new HashMap<>();

    @Override
    public Optional<List<T>> findShortestPath(T start,
                                              T end,
                                              Graph<T> graph) {
        var queue = new PriorityQueue<>(
                Comparator.comparingDouble(this::f)
                        .thenComparingDouble(this::h)
        );

        distances.clear();
        distances.put(start, 0.0);
        queue.add(start);
        var predecessors = new HashMap<T, T>();

        while (!queue.isEmpty()) {
            var current = queue.poll();

            if (current == end) {
                break;
            }
            System.out.println(graph.getNeighbors(current));
            graph.getNeighbors(current)
                    .forEach((neighbor, weight) -> {
                        double newDistance = weight + f(current);

                        if (newDistance < f(neighbor)) {
                            distances.put(neighbor, newDistance);
                            predecessors.put(neighbor, current);
                            queue.remove(neighbor);
                            queue.offer(neighbor);
                        }
                    });
        }

        if (Double.isFinite(g(end))) {
            var pathTracer = new PathTracer<>(predecessors);
            return Optional.of(pathTracer.unsafeTrace(start, end));
        }

        return Optional.empty();
    }

    protected double f(T vertex) {
        return g(vertex) + h(vertex);
    }

    protected abstract double g(T vertex);

    protected abstract double h(T vertex);

}

package pathfinding.algorithms;

import pathfinding.graphs.Graph;
import pathfinding.service.PathTracer;

import java.util.*;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 18.02.2024, So.
 */
public abstract class BestFirstSearch<T> implements PathfindingAlgorithm<T> {

    protected final Map<T, Double> distances = new HashMap<>();

    @Override
    public Optional<List<T>> findShortestPath(T start,
                                              T end,
                                              Graph<T> graph) {
        var predecessors = new HashMap<T, T>();
        var queue = new PriorityQueue<>(Comparator.comparingDouble(this::f));
        distances.clear();

        for (T vertex : graph.getVertices()) {
            distances.put(vertex, Double.POSITIVE_INFINITY);
        }

        distances.put(start, 0.0);
        queue.add(start);

        while (!queue.isEmpty()) {
            var current = queue.poll();

            if (current == end) {
                break;
            }

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

        if (Double.isFinite(distances.get(end))) {
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

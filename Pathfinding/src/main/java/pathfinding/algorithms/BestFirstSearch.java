package pathfinding.algorithms;

import pathfinding.FifteenPuzzleGui;
import pathfinding.games.FifteenPuzzle;
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
                Comparator.<T>comparingDouble(current -> f(current, end))
                        .thenComparingDouble(current -> h(current, end))
        );

        var predecessors = new HashMap<T, T>();
        distances.clear();
        distances.put(start, 0.0);
        queue.add(start);

        while (!queue.isEmpty()) {
            var current = queue.poll();

            if (current == end) {
                var pathTracer = new PathTracer<>(predecessors);
                return Optional.of(pathTracer.unsafeTrace(start, end));
            }

            graph.getNeighbors(current)
                    .forEach((neighbor, weight) -> {
                        double newDistance = weight + f(current, end);

                        if (newDistance < f(neighbor, end)) {
                            distances.put(neighbor, newDistance);
                            predecessors.put(neighbor, current);
                            queue.remove(neighbor);
                            queue.offer(neighbor);
                        }
                    });
        }

        return Optional.empty();
    }

    protected double f(T current, T end) {
        return g(current) + h(current, end);
    }

    protected abstract double g(T current);

    protected abstract double h(T current, T end);

}

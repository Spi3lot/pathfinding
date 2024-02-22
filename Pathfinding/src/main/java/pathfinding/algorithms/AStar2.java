package pathfinding.algorithms;

import pathfinding.graphs.Graph;
import pathfinding.service.EndCondition;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.BiFunction;

/**
 * Implementation of the A* algorithm for finding the shortest path.
 *
 * @param <T> the type of the vertices in the graph
 */
public class AStar2<T> extends BestFirstSearch<T> {

    private final BiFunction<T, EndCondition<T>, Double> heuristic;

    @Override
    public Optional<List<T>> findShortestPath(T start,
                                              EndCondition<T> endCondition,
                                              Graph<T> graph) {
        var queue = new PriorityQueue<>(
                Comparator.<T>comparingDouble(
                                vertex -> f(vertex, endCondition)
                        )
                        .thenComparingDouble(
                                vertex -> h(vertex, endCondition)
                        )
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
                        if (!distances.containsKey(neighbor)) {
                            double g = g(current);
                            double h = heuristicValues.computeIfAbsent(neighbor, v -> h(current, endCondition));
                            double f = g + h + weight;
                            double distance = f(neighbor, endCondition);

                            if (f < distance) {
                                distances.put(neighbor, g + weight);
                                predecessors.put(neighbor, current);
                                queue.remove(neighbor);
                                queue.offer(neighbor);
                            }
                        }

                    });
        }

        return Optional.empty();
    }

    /**
     * Creates a new instance of the A* algorithm.
     *
     * @param heuristic the heuristic function to use
     */
    public AStar2(BiFunction<T, EndCondition<T>, Double> heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    protected double g(T vertex) {
        return distances.getOrDefault(vertex, Double.POSITIVE_INFINITY);
    }

    @Override
    protected double h(T vertex, EndCondition<T> endCondition) {
        return heuristic.apply(vertex, endCondition);
    }

}

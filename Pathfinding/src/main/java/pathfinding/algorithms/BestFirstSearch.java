package pathfinding.algorithms;

import pathfinding.datastructures.FibonacciHeap;
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
        var open = new FibonacciHeap<T>();
        var closed = new HashSet<T>();
        predecessors.clear();
        distances.clear();
        distances.put(start, 0.0);
        open.enqueue(start, 0);

        while (!open.isEmpty()) {
            T current = open.dequeueMin().getValue();
            closed.add(current);

            if (endCondition.condition().test(current)) {
                return Optional.of(pathTracer.unsafeTrace(start, current));
            }

            graph.getNeighbors(current)
                    .entrySet()
                    .stream()
                    .filter(neighbor -> !closed.contains(neighbor.getKey()))
                    .forEach(entry -> {
                        T neighbor = entry.getKey();
                        double weight = entry.getValue();
                        double oldStartDistance = g(neighbor);
                        double newStartDistance = g(current) + weight;
                        double heuristic = h(neighbor, endCondition);
                        double oldDistance = oldStartDistance + heuristic;
                        double newDistance = newStartDistance + heuristic;

                        if (newDistance < oldDistance) {
                            distances.put(neighbor, newStartDistance);
                            predecessors.put(neighbor, current);
                            open.enqueue(neighbor, newDistance);
                        }
                    });
        }

        return Optional.empty();
    }

    protected abstract double g(T vertex);

    protected abstract double h(T vertex, EndCondition<T> endCondition);

}

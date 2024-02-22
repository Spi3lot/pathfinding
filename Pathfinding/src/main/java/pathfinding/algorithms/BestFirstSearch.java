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

    @Override
    public Optional<List<T>> findShortestPath(T start,
                                              EndCondition<T> endCondition,
                                              Graph<T> graph) {
        var open = new FibonacciHeap<T>();
        var closed = new HashSet<T>();
        var distances = new HashMap<T, Double>();
        var predecessors = new HashMap<T, T>();
        distances.put(start, 0.0);
        open.enqueue(start, 0);

        while (!open.isEmpty()) {
            T current = open.dequeueMin().getValue();
            closed.add(current);

            if (endCondition.condition().test(current)) {
                var pathTracer = new PathTracer<>(predecessors);
                return Optional.of(pathTracer.unsafeTrace(start, current));
            }

            expandNode(
                    current,
                    endCondition,
                    graph,
                    open,
                    closed,
                    distances,
                    predecessors
            );
        }

        return Optional.empty();
    }

    protected void expandNode(T current,
                            EndCondition<T> endCondition,
                            Graph<T> graph,
                            FibonacciHeap<T> open,
                            Set<T> closed,
                            Map<T, Double> distances,
                            Map<T, T> predecessors) {
        graph.getNeighbors(current)
                .entrySet()
                .stream()
                .filter(neighbor -> !closed.contains(neighbor.getKey()))
                .forEach(entry -> {
                    T neighbor = entry.getKey();
                    double weight = entry.getValue();
                    double oldG = g(neighbor, distances);
                    double newG = g(current, distances) + weight;
                    double heuristic = h(neighbor, endCondition);
                    double oldF = oldG + heuristic;
                    double newF = newG + heuristic;

                    if (newF < oldF) {
                        distances.put(neighbor, newG);
                        predecessors.put(neighbor, current);
                        open.enqueue(neighbor, newF);
                    }
                });
    }

    protected abstract double g(T vertex, Map<T, Double> distances);

    protected abstract double h(T vertex, EndCondition<T> endCondition);

}

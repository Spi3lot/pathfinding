package pathfinding.algorithms;

import lombok.Getter;
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
@Getter
public abstract class BestFirstSearch<T> implements PathfindingAlgorithm<T> {

    protected final Map<T, Double> distances = new HashMap<>();
    private final Map<T, T> predecessors = new HashMap<>();
    private final Set<T> closed = new HashSet<>();
    private FibonacciHeap<T> open;
    private T current;

    @Override
    public Optional<List<T>> findShortestPath(T start,
                                              EndCondition<T> endCondition,
                                              Graph<T> graph) {
        initializeDataStructures(start);

        while (!open.isEmpty()) {
            updateCurrent();

            if (closed.contains(current)) {
                continue;
            }

            if (endCondition.condition().test(current)) {
                var pathTracer = new PathTracer<>(predecessors);
                return Optional.of(pathTracer.unsafeTrace(start, current));
            }

            closeCurrent();
            expand(endCondition, graph);
        }

        return Optional.empty();
    }

    protected void updateCurrent() {
        current = open.dequeueMin().getValue();
    }

    protected void closeCurrent() {
        closed.add(current);
    }

    protected void initializeDataStructures(T start) {
        predecessors.clear();
        distances.clear();
        distances.put(start, 0.0);
        closed.clear();
        open = new FibonacciHeap<>();
        open.enqueue(start, 0.0);
    }

    protected void expand(EndCondition<T> endCondition,
                          Graph<T> graph) {
        graph.getNeighbors(current)
                .entrySet()
                .stream()
                .filter(entry -> !closed.contains(entry.getKey()))
                .forEach(entry -> {
                    T neighbor = entry.getKey();
                    double weight = entry.getValue();
                    double oldG = g(neighbor);
                    double newG = g(current) + weight;

                    if (newG < oldG) {
                        distances.put(neighbor, newG);
                        predecessors.put(neighbor, current);
                        open.enqueue(neighbor, newG + h(neighbor, endCondition));
                    }
                });
    }

    protected abstract double g(T vertex);

    protected abstract double h(T vertex, EndCondition<T> endCondition);

}

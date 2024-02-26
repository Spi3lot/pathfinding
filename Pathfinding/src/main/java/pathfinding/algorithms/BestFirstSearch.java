package pathfinding.algorithms;

import lombok.Getter;
import pathfinding.datastructures.FibonacciHeap;
import pathfinding.graphs.Graph;
import pathfinding.service.EndCondition;
import pathfinding.service.PathTracer;

import java.util.*;

/**
 * Abstract class to make the implementation
 * of best-first search algorithms easier.
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
    public List<T> findShortestPath(T start,
                                    EndCondition<T> endCondition,
                                    Graph<T> graph) {
        initializeDataStructures(start);

        while (hasOpen()) {
            updateCurrent();

            if (hasVisited(current)) {
                continue;
            }

            if (endCondition.condition().test(current)) {
                var pathTracer = new PathTracer<>(predecessors);
                return pathTracer.unsafeTrace(start, current);
            }

            closeCurrent();
            expand(endCondition, graph);
        }

        return Collections.emptyList();
    }

    protected void updateCurrent() {
        current = open.dequeueMin().getValue();
    }

    protected void closeCurrent() {
        closed.add(current);
    }

    protected boolean hasVisited(T vertex) {
        return closed.contains(vertex);
    }

    protected boolean hasOpen() {
        return !open.isEmpty();
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
                .filter(entry -> !hasVisited(entry.getKey()))
                .forEach(entry -> {
                    T neighbor = entry.getKey();
                    double weight = entry.getValue();
                    double g = g(neighbor);
                    double tentativeG = g(current) + weight;

                    if (tentativeG < g) {
                        distances.put(neighbor, tentativeG);
                        predecessors.put(neighbor, current);
                        double heuristic = h(neighbor, endCondition);
                        open.enqueue(neighbor, tentativeG + heuristic);
                    }
                });
    }

    protected abstract double g(T vertex);

    protected abstract double h(T vertex, EndCondition<T> endCondition);

}

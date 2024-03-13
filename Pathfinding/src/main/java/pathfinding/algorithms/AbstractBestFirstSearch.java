package pathfinding.algorithms;

import lombok.Getter;
import pathfinding.datastructures.FibonacciHeap;
import pathfinding.graphs.Graph;
import pathfinding.service.EndCondition;
import pathfinding.service.PathTracer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Abstract class for best-first search algorithms.
 *
 * @param <T> the type of the vertices in the graph
 */
@Getter
public abstract class AbstractBestFirstSearch<T>
        implements BestFirstSearch<T> {

    private final Map<T, Double> distances = new HashMap<>();
    private final Map<T, T> predecessors = new HashMap<>();
    private final Set<T> closed = new HashSet<>();
    private FibonacciHeap<T> open;
    private T current;

    @Override
    public int getVisitedVertexCount() {
        return closed.size();
    }

    @Override
    public List<T> findShortestPath(T start,
                                    EndCondition<T> endCondition,
                                    Graph<T> graph) {
        initializeDataStructures(start);

        while (nextUnvisited()) {
            if (endCondition.condition().test(current)) {
                var pathTracer = new PathTracer<>(predecessors);
                return pathTracer.unsafeTrace(start, current);
            }

            closeCurrent();
            expand(endCondition, graph);
        }

        return Collections.emptyList();
    }

    @Override
    public boolean nextOpen() {
        if (hasOpen()) {
            current = open.dequeueMin().getValue();
            return true;
        }

        current = null;
        return false;
    }

    @Override
    public void closeCurrent() {
        closed.add(current);
    }

    @Override
    public boolean hasVisited(T vertex) {
        return closed.contains(vertex);
    }

    @Override
    public boolean hasOpen() {
        return !open.isEmpty();
    }

    @Override
    public void initializeDataStructures(T start) {
        predecessors.clear();
        distances.clear();
        distances.put(start, 0.0);
        closed.clear();
        open = new FibonacciHeap<>();
        open.enqueue(start, 0.0);
    }

    @Override
    public Set<T> expand(EndCondition<T> endCondition, Graph<T> graph) {
        var neighbors = graph.getNeighbors(current)
                .entrySet()
                .stream()
                .filter(entry -> !hasVisited(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        neighbors.forEach((neighbor, weight) -> {
                    double g = g(neighbor);
                    double tentativeG = g(current) + weight;

                    if (tentativeG < g) {
                        distances.put(neighbor, tentativeG);
                        predecessors.put(neighbor, current);
                        double heuristic = h(neighbor, endCondition);
                        open.enqueue(neighbor, tentativeG + heuristic);
                    }
                });

        return neighbors.keySet();
    }

}

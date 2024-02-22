package pathfinding.algorithms;

import pathfinding.datastructures.FibonacciHeap;
import pathfinding.graphs.Graph;
import pathfinding.service.EndCondition;
import pathfinding.service.PathTracer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * Implementation of the Meet-in-the-Middle algorithm built on top of A*.
 *
 * @param <T> the type of the vertices in the graph
 */
public class AStarMeetInTheMiddle<T> extends AStar<T> {

    public AStarMeetInTheMiddle(BiFunction<T, EndCondition<T>, Double> heuristic) {
        super(heuristic);
    }

    @Override
    public Optional<List<T>> findShortestPath(T start,
                                              EndCondition<T> endCondition,
                                              Graph<T> graph) {
        var open = new FibonacciHeap<T>();
        var openBackward = new FibonacciHeap<T>();
        var closed = new HashSet<T>();
        var closedBackward = new HashSet<T>();
        var predecessors = new HashMap<T, T>();
        var predecessorsBackward = new HashMap<T, T>();
        var distances = new HashMap<T, Double>();
        var distancesBackward = new HashMap<T, Double>();
        T end = endCondition.endVertex().orElseThrow();
        distances.put(start, 0.0);
        distancesBackward.put(end, 0.0);
        open.enqueue(start, 0);
        openBackward.enqueue(end, 0);

        while (!open.isEmpty() && !openBackward.isEmpty()) {
            T current = open.dequeueMin().getValue();
            T currentBackward = openBackward.dequeueMin().getValue();
            closed.add(current);
            closedBackward.add(currentBackward);

            if (closedBackward.contains(current)) {
                var pathTracer = new PathTracer<>(predecessors);
                var startToCurrent = pathTracer.unsafeTrace(start, current);
                pathTracer.setPredecessors(predecessorsBackward);
                var endToCurrent = pathTracer.unsafeTrace(end, current);
                var path = mergePaths(startToCurrent, endToCurrent);
                return Optional.of(path);
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

            expandNode(
                    currentBackward,
                    endCondition,
                    graph,
                    openBackward,
                    closedBackward,
                    distancesBackward,
                    predecessorsBackward
            );
        }

        return Optional.empty();
    }

    private List<T> mergePaths(List<T> startToCurrent, List<T> endToCurrent) {
        var currentToEnd = endToCurrent.reversed();
        startToCurrent.addAll(currentToEnd.subList(1, currentToEnd.size()));
        return startToCurrent;
    }

}

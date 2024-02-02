package pathfinding.algorithms;

import pathfinding.graphs.Graph;
import pathfinding.service.PathTracer;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @param <T>
 */
public class Dijkstra<T> implements PathfindingAlgorithm<T> {

    @Override
    public Optional<List<T>> findShortestPath(T start,
                                              T end,
                                              Graph<T> graph) {

        var distances = graph.getVertices()
                .stream()
                .collect(Collectors.toMap(vertex -> vertex, vertex -> Double.POSITIVE_INFINITY));

        distances.put(start, 0.0);
        var predecessors = new HashMap<T, T>();
        var queue = new PriorityQueue<T>(Comparator.comparingDouble(distances::get));
        queue.add(start);

        while (!queue.isEmpty()) {
            var current = queue.poll();

            graph.getNeighbors(current)
                    .forEach((neighbor, weight) -> {
                        var newDistance = weight + distances.get(current);

                        if (newDistance < distances.get(neighbor)) {
                            distances.put(neighbor, newDistance);
                            predecessors.put(neighbor, current);
                            queue.offer(neighbor);
                        }
                    });
        }

        var pathTracer = new PathTracer<>(predecessors);
        return Optional.of(pathTracer.unsafeTrace(start, end));
    }

}

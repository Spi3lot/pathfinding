package pathfinding.algorithms;

import pathfinding.graphs.Graph;
import pathfinding.service.PathTracer;

import java.util.*;

/**
 * Implementation of the Dijkstra algorithm for finding the shortest path.
 *
 * @param <T> the type of the vertices in the graph
 */
public class Dijkstra<T> implements PathfindingAlgorithm<T> {

    @Override
    public Optional<List<T>> findShortestPath(T start,
                                              T end,
                                              Graph<T> graph) {
        var predecessors = new HashMap<T, T>();
        var distances = new HashMap<T, Double>();
        var queue = new PriorityQueue<T>(Comparator.comparingDouble(distances::get));

        for (var vertex : graph.getVertices()) {
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
                        double newDistance = weight + distances.get(current);

                        if (newDistance < distances.get(neighbor)) {
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

}

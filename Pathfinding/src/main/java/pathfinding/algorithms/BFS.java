package pathfinding.algorithms;

import pathfinding.graphs.Graph;
import pathfinding.service.PathTracer;

import java.util.*;

public class BFS<T> implements PathfindingAlgorithm<T> {

    @Override
    public Optional<List<T>> findShortestPath(T start, T end, Graph<T> graph) {
        var queue = new ArrayDeque<T>();
        var visited = new ArrayList<T>();
        var predecessors = new HashMap<T, T>();
        boolean found = false;
        queue.add(start);

        while (!queue.isEmpty()) {
            var current = queue.poll();
            visited.add(current);

            if (current.equals(end)) {
                found = true;
                break;
            }

            graph.getNeighbors(current)
                    .keySet()
                    .stream()
                    .filter(neighbor -> !visited.contains(neighbor))
                    .filter(neighbor -> !queue.contains(neighbor))
                    .forEach(neighbor -> {
                        queue.offer(neighbor);
                        predecessors.put(neighbor, current);
                        System.out.println(predecessors);
                    });
        }

        if (found) {
            var pathTracer = new PathTracer<>(predecessors);
            return Optional.of(pathTracer.trace(start, end));
        } else {
            return Optional.empty();
        }
    }

}

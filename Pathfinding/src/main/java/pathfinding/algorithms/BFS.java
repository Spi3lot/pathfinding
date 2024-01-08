package pathfinding.algorithms;

import pathfinding.graphs.Graph;

import java.util.*;

public class BFS<T> implements PathfindingAlgorithm<T> {

    @Override
    public Optional<List<T>> findShortestPath(T start, T end, Graph<T> graph) {
        var queue = new LinkedList<T>();
        var visited = new ArrayList<T>();
        var previous = new HashMap<T, T>();
        boolean found = false;
        queue.add(start);

        while (!queue.isEmpty()) {
            var current = queue.poll();
            System.out.println(current);

            if (current.equals(end)) {
                System.out.println(previous);
                found = true;
                break;
            }

            visited.add(current);

            graph.getNeighbors(current)
                    .keySet()
                    .stream()
                    .filter(neighbor -> !visited.contains(neighbor))
                    .filter(neighbor -> !queue.contains(neighbor))
                    .forEach(neighbor -> {
                        queue.offer(neighbor);
                        previous.put(neighbor, current);
                    });
        }

        if (found) {
            return Optional.of(makePathFromMap(start, end, previous));
        } else {
            return Optional.empty();
        }
    }

    private List<T> makePathFromMap(T start, T end, Map<T, T> predecessors) {
        var path = new ArrayList<T>();
        var current = end;

        while (!current.equals(start)) {
            path.addFirst(current);
            current = predecessors.get(current);
        }

        path.addFirst(start);
        return path;
    }

}

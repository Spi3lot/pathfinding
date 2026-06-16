package pathfinding.algorithms;

import lombok.Getter;
import pathfinding.graphs.Graph;
import pathfinding.service.EndCondition;
import pathfinding.service.PathTracer;

import java.util.*;

/**
 * Iterative implementation of the Breadth-First Search algorithm.
 *
 * @param <T> the type of the nodes in the graph to be searched
 */
@Getter
public class BreadthFirstSearch<T> implements PathfindingAlgorithm<T> {

    private int visitedVertexCount;

    @Override
    public List<T> findShortestPath(T start,
                                    EndCondition<T> endCondition,
                                    Graph<T> graph) {
        var queue = new ArrayDeque<T>();
        var visited = new ArrayList<T>();
        var predecessors = new HashMap<T, T>();
        queue.add(start);
        visitedVertexCount = 0;

        while (!queue.isEmpty()) {
            T current = queue.poll();
            visited.add(current);
            visitedVertexCount++;

            if (endCondition.condition().test(current)) {
                var pathTracer = new PathTracer<>(predecessors);
                return pathTracer.unsafeTrace(start, current);
            }

            graph.getNeighbors(current)
                    .keySet()
                    .stream()
                    .filter(neighbor -> !visited.contains(neighbor))
                    .filter(neighbor -> !queue.contains(neighbor))
                    .forEach(neighbor -> {
                        queue.offer(neighbor);
                        predecessors.put(neighbor, current);
                    });
        }

        return Collections.emptyList();
    }

}

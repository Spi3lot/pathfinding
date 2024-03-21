package pathfinding.service;

import pathfinding.algorithms.CycleException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * Utility class for reconstructing paths from a given map of predecessors.
 *
 * @param <T> the type of the nodes in the map
 */
public record PathTracer<T>(Map<T, T> predecessors) {

    /**
     * This method is less safe than {@link #safeTrace(T, T)} because
     * it is possibleto get stuck in an infinite loop. However, it is
     * more efficient because it makes use of {@link ArrayList}.
     */
    public List<T> unsafeTrace(T start, T end) {
        var path = new ArrayList<T>();
        var current = end;
        path.add(current);

        while (!current.equals(start)) {
            current = predecessors.get(current);
            path.addFirst(current);
        }

        return path;
    }

    /**
     * This method is safer than {@link #unsafeTrace(T, T)}
     * because it checks for cycles in the path. However, it is
     * less efficient because it makes use of {@link LinkedHashSet}.
     *
     * @throws CycleException if a cycle is detected in the path
     */
    public List<T> safeTrace(T start, T end) {
        var path = new LinkedHashSet<T>();
        var current = end;
        path.add(current);

        while (!current.equals(start)) {
            current = predecessors.get(current);

            if (!path.add(current)) {
                throw new CycleException(
                        "The end node is unreachable because of a cycle."
                );
            }
        }

        return path.reversed()
                .stream()
                .toList();
    }

}

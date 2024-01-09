package pathfinding.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pathfinding.exceptions.CycleException;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * Utility class for reconstructing paths from a given map of predecessors.
 *
 * @param <T> the type of the nodes in the map
 */
@AllArgsConstructor
@Getter
@Setter
public class PathTracer<T> {

    private Map<T, T> predecessors;

    /**
     * Traces the path from start to end in a given map of predecessors.
     *
     * @param start the node the path starts at
     * @param end   the node the path ends at
     * @return a list of nodes that form the path from start to end
     * @throws CycleException if the end node is unreachable because of a cycle
     */
    public List<T> trace(T start, T end) {
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

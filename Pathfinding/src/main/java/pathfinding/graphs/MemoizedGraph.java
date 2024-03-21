package pathfinding.graphs;

import java.util.HashMap;
import java.util.Map;

/**
 * An abstract class that helps with
 * memoizing the neighbors of a graph.
 * The neighbors will only be calculated
 * when they are requested for the first time.
 *
 * @param <T> the type of the vertices in the graph
 */
public abstract class MemoizedGraph<T> implements Graph<T> {

    private final Map<T, Map<T, Double>> adjacencyCache = new HashMap<>();

    @Override
    public Map<T, Double> getNeighbors(T vertex) {
        return adjacencyCache.computeIfAbsent(
                vertex,
                this::calculateNeighbors
        );
    }

    protected abstract Map<T, Double> calculateNeighbors(T vertex);

}

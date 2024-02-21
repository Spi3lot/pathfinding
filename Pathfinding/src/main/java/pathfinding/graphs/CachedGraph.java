package pathfinding.graphs;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 20.02.2024, Di.
 */
public abstract class CachedGraph<T> implements Graph<T> {

    private final Map<T, Map<T, Double>> adjacencyCache = new HashMap<>();

    @Override
    public Map<T, Double> getNeighbors(T vertex) {
        return adjacencyCache.computeIfAbsent(vertex, this::calculateNeighbors);
    }

    abstract Map<T, Double> calculateNeighbors(T vertex);

}

package pathfinding.algorithms;

import lombok.RequiredArgsConstructor;
import pathfinding.functions.Heuristic;
import pathfinding.service.EndCondition;

import java.util.Map;

/**
 * Abstract class for best-first search algorithms.
 *
 * @param <T> the type of the vertices in the graph

 */
@RequiredArgsConstructor
public class AStar<T> extends AbstractBestFirstSearch<T> {

    private final Heuristic<T> heuristic;

    @Override
    public final double g(T vertex, Map<T, Double> distances) {
        return distances.getOrDefault(vertex, Double.POSITIVE_INFINITY);
    }

    @Override
    public double h(T vertex, EndCondition<T> endCondition) {
        return heuristic.applyAsDouble(vertex, endCondition);
    }

}

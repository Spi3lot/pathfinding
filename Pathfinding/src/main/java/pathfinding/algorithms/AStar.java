package pathfinding.algorithms;

import lombok.Getter;
import pathfinding.functions.Heuristic;
import pathfinding.service.EndCondition;

/**
 * Implementation of the A* algorithm for finding the shortest path.
 *
 * @param <T> the type of the vertices in the graph
 */
@Getter
public class AStar<T> extends BestFirstSearch<T> {

    private final Heuristic<T> heuristic;

    /**
     * Creates a new instance of the A* algorithm.
     *
     * @param heuristic the heuristic function to use
     */
    public AStar(Heuristic<T> heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    protected final double g(T vertex) {
        return distances.getOrDefault(vertex, Double.POSITIVE_INFINITY);
    }

    @Override
    protected final double h(T vertex, EndCondition<T> endCondition) {
        return heuristic.applyAsDouble(vertex, endCondition);
    }

}

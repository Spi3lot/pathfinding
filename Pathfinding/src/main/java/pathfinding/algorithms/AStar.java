package pathfinding.algorithms;

import pathfinding.service.EndCondition;

import java.util.function.BiFunction;

/**
 * Implementation of the A* algorithm for finding the shortest path.
 *
 * @param <T> the type of the vertices in the graph
 */
public class AStar<T> extends BestFirstSearch<T> {

    private final BiFunction<T, EndCondition<T>, Double> heuristic;

    /**
     * Creates a new instance of the A* algorithm.
     *
     * @param heuristic the heuristic function to use
     */
    public AStar(BiFunction<T, EndCondition<T>, Double> heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    protected double g(T vertex) {
        return distances.getOrDefault(vertex, Double.POSITIVE_INFINITY);
    }

    @Override
    protected double h(T vertex, EndCondition<T> endCondition) {
        return heuristic.apply(vertex, endCondition);
    }

}

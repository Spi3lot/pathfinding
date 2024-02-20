package pathfinding.algorithms;

import java.util.function.BiFunction;

/**
 * Implementation of the A* algorithm for finding the shortest path.
 *
 * @param <T> the type of the vertices in the graph
 */
public class AStar<T> extends BestFirstSearch<T> {

    private final BiFunction<T, T, Double> heuristic;

    /**
     * Creates a new instance of the A* algorithm.
     *
     * @param heuristic the heuristic function to use
     */
    public AStar(BiFunction<T, T, Double> heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    protected double g(T current) {
        return distances.getOrDefault(current, Double.POSITIVE_INFINITY);
    }

    @Override
    protected double h(T current, T end) {
        return heuristic.apply(current, end);
    }

}

package pathfinding.algorithms;

/**
 * Implementation of the A* algorithm for finding the shortest path.
 *
 * @param <T> the type of the vertices in the graph
 */
public abstract class AStar<T> extends BestFirstSearch<T> {

    @Override
    protected double g(T current) {
        return distances.getOrDefault(current, Double.POSITIVE_INFINITY);
    }

}

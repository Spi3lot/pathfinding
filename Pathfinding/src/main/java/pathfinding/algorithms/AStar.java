package pathfinding.algorithms;

/**
 * Implementation of the A* algorithm for finding the shortest path.
 *
 * @param <T> the type of the vertices in the graph
 */
public abstract class AStar<T> extends BestFirstSearch<T> {

    @Override
    protected double g(T vertex) {
        return distances.getOrDefault(vertex, Double.POSITIVE_INFINITY);
    }

}

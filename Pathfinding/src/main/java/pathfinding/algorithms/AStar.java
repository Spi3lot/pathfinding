package pathfinding.algorithms;

/**
 * Implementation of the A* algorithm for finding the shortest path.
 *
 * @param <T> the type of the vertices in the graph
 */
public class AStar<T> extends BestFirstSearch<T> {

    @Override
    protected double g(T vertex) {
        return distances.get(vertex);
    }

    @Override
    protected double h(T vertex) {
        return 0;  // TODO: Implement heuristic
    }

}

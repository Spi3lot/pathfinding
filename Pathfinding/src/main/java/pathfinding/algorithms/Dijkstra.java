package pathfinding.algorithms;

/**
 * Implementation of the Dijkstra algorithm for finding the shortest path.
 *
 * @param <T> the type of the vertices in the graph
 */
public class Dijkstra<T> extends AStar<T> {

    @Override
    protected double h(T current, T end) {
        return 0;
    }

}

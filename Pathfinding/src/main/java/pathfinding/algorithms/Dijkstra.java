package pathfinding.algorithms;

/**
 * Implementation of the Dijkstra algorithm for finding the shortest path.
 *
 * @param <T> the type of the vertices in the graph
 */
public class Dijkstra<T> extends AStar<T> {

    /**
     * Creates a new instance of the Dijkstra algorithm.
     */
    public Dijkstra() {
        super((_, _) -> 0.0);
    }

}

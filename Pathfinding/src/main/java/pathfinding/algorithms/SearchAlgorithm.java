package pathfinding.algorithms;

import pathfinding.graphs.Graph;
import pathfinding.service.EndCondition;

import java.util.Collections;
import java.util.List;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 21.03.2024, Do.
 */
public interface SearchAlgorithm<T> {

    /**
     * @return the number of visited vertices during the last search.
     */
    int getVisitedVertexCount();

    /**
     * Finds any path between two given vertices in a graph.
     *
     * @param start        the vertex the path starts at
     * @param endCondition the condition that has to be met for the path to end
     * @param graph        the graph in which a path is to be found
     * @return Any path between the two vertices, or {@link Collections#emptyList()} if no path exists.
     */
    List<T> findAnyPath(T start,
                        EndCondition<T> endCondition,
                        Graph<T> graph);

}

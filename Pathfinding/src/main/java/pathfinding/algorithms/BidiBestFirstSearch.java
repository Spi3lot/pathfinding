package pathfinding.algorithms;

import pathfinding.functions.Heuristic;
import pathfinding.graphs.Graph;
import pathfinding.service.EndCondition;
import pathfinding.service.PathTracer;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of bidirectional best-first search.
 * It uses two instances of any best-first search
 * algorithms to search from both ends.
 * The two algorithms can even be different.
 * The search stops when the two searches <b>meet in the middle</b>.
 * <p>
 * <i>Bidi</i> stands for bidirectional and is
 * abbreviated to avoid line breaks in the code.
 *
 * @param <T> the type of the vertices in the graph
 */
public class BidiBestFirstSearch<T> implements PathfindingAlgorithm<T> {

    private final BestFirstSearch<T> forwardSearch;
    private final BestFirstSearch<T> backwardSearch;

    public BidiBestFirstSearch(BestFirstSearch<T> forwardSearch,
                               BestFirstSearch<T> backwardSearch) {
        if (forwardSearch == backwardSearch) {
            throw new IllegalArgumentException(
                    "The forward and backward searches " +
                            "must not be the same instance."
            );
        }

        this.forwardSearch = Objects.requireNonNull(forwardSearch);
        this.backwardSearch = Objects.requireNonNull(backwardSearch);
    }

    /**
     * Named constructor to create a bidirectional best-first search
     * using A* as the search algorithm for both directions.
     *
     * @param h   the heuristic function to use.
     *            The name is abbreviated to avoid line breaks in the code.
     * @param <T> the type of the vertices in the graph
     * @return a new instance of BidiBestFirstSearch
     */
    public static <T> BidiBestFirstSearch<T> usingAStar(Heuristic<T> h) {
        return new BidiBestFirstSearch<>(
                new AStar<>(h),
                new AStar<>(h)
        );
    }

    @Override
    public int getVisitedVertexCount() {
        return forwardSearch.getVisitedVertexCount()
                + backwardSearch.getVisitedVertexCount();
    }

    @Override
    public List<T> findShortestPath(T start,
                                    EndCondition<T> forwardEndCondition,
                                    Graph<T> graph) {
        T end = forwardEndCondition.vertex()
                .orElseThrow(() -> new IllegalArgumentException(
                        "The end condition must specify a vertex."
                ));

        var backwardEndCondition = EndCondition.endAt(start);
        forwardSearch.initializeDataStructures(start);
        backwardSearch.initializeDataStructures(end);

        while (forwardSearch.nextUnvisited() && backwardSearch.nextUnvisited()) {
            forwardSearch.closeCurrent();
            backwardSearch.closeCurrent();

            if (forwardSearch.hasVisited(backwardSearch.getCurrent())) {
                return mergePaths(start, backwardSearch.getCurrent(), end);
            }

            if (backwardSearch.hasVisited(forwardSearch.getCurrent())) {
                return mergePaths(start, forwardSearch.getCurrent(), end);
            }

            forwardSearch.expand(forwardEndCondition, graph);
            backwardSearch.expand(backwardEndCondition, graph);
        }

        return Collections.emptyList();
    }

    /**
     * Merges the vertices from the start to the common vertex
     * and from the end to the common vertex into a single path.
     * The common vertex is included only once in the result.
     *
     * @param start  the start vertex
     * @param common the common vertex
     * @param end    the end vertex
     * @return the merged path
     */
    private List<T> mergePaths(T start,
                               T common,
                               T end) {
        var forwardPredecessors = forwardSearch.getPredecessors();
        var forwardTracer = new PathTracer<>(forwardPredecessors);
        var startToCommon = forwardTracer.unsafeTrace(start, common);

        var backwardPredecessors = backwardSearch.getPredecessors();
        var backwardTracer = new PathTracer<>(backwardPredecessors);
        var endToCommon = backwardTracer.unsafeTrace(end, common);
        return mergePaths(startToCommon, endToCommon);
    }

    /**
     * Merges the 2 paths by reversing the 2nd one and
     * removing the common vertex from any of the 2
     * (in this case, from the 2nd path) and concatenating them
     *
     * @param startToCommon the path from the start to the common vertex
     * @param endToCommon   the path from the end to the common vertex
     * @return the merged path
     */
    private List<T> mergePaths(List<T> startToCommon, List<T> endToCommon) {
        var commonToEnd = endToCommon.reversed();
        startToCommon.addAll(commonToEnd.subList(1, commonToEnd.size()));
        return startToCommon;
    }

}

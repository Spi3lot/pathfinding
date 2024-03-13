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
public record BidiBestFirstSearch<T>(BestFirstSearch<T> forwardSearch,
                                     BestFirstSearch<T> backwardSearch)
        implements PathfindingAlgorithm<T> {

    public BidiBestFirstSearch {
        if (Objects.requireNonNull(forwardSearch) == Objects.requireNonNull(backwardSearch)) {
            throw new IllegalArgumentException(
                    "The forward and backward searches " +
                            "must not be the same instance."
            );
        }
    }

    /**
     * Constructor to create a bidirectional best-first search
     * using A* as the search algorithm for both directions.
     *
     * @param heuristic the heuristic function to use.
     */
    public BidiBestFirstSearch(Heuristic<T> heuristic) {
        this(new AStar<>(heuristic), new AStar<>(heuristic));
    }

    @Override
    public int getVisitedVertexCount() {
        return forwardSearch.getVisitedVertexCount()
                + backwardSearch.getVisitedVertexCount();
    }
    static List<?> path = Collections.emptyList();
    static Object starty;
    static Object endy;
    static double bestCost = Double.POSITIVE_INFINITY;
    @Override
    public List<T> findShortestPath(T start,
                                    EndCondition<T> forwardEndCondition,
                                    Graph<T> graph) {
        T end = forwardEndCondition.vertex()
                .orElseThrow(() -> new IllegalArgumentException(
                        "The end condition must specify a vertex."
                ));

        starty = start;
        endy = end;
        var backwardEndCondition = EndCondition.endAt(start);
        boolean forward = true;
        forwardSearch.initializeDataStructures(start);
        backwardSearch.initializeDataStructures(end);
        backwardSearch.nextOpen();

        do {
            if (forward) {
                step(forwardSearch, backwardSearch, forwardEndCondition, graph);
            } else {
                step(backwardSearch, forwardSearch, backwardEndCondition, graph);
            }

            forward = !forward;
        } while (forwardSearch.g() + backwardSearch.g() < bestCost);

        return Collections.emptyList();
    }

    private void step(BestFirstSearch<T> search,
                      BestFirstSearch<T> otherSearch,
                      EndCondition<T> endCondition,
                      Graph<T> graph) {
        search.nextUnvisited();

        for (var visited : search.expand(endCondition, graph).keySet()) {
            double cost = search.g(visited) + otherSearch.g(visited);

            if (cost < bestCost && otherSearch.hasVisited(visited)){
                bestCost = cost;
                var tracer1 = new PathTracer<>(search.getPredecessors());
                var tracer2 = new PathTracer<>(otherSearch.getPredecessors());
                var path1 =  tracer1.unsafeTrace((T) starty, search.getCurrent());
                var path2 =  tracer2.unsafeTrace((T) endy, visited);
                path1.addAll(path2.reversed());
                path = path1;
            }
        }
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

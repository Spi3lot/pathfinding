package pathfinding.algorithms;

import pathfinding.functions.Heuristic;
import pathfinding.graphs.Graph;
import pathfinding.service.EndCondition;
import pathfinding.service.PathTracer;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of the bidirectional best-first search algorithm.
 * It uses two instances of any best-first search algorithm to search from both ends.
 * The two algorithms can even be different.
 * The algorithm stops when the two searches <b>meet in the middle</b>.
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
                            "must be different instances."
            );
        }

        this.forwardSearch = Objects.requireNonNull(forwardSearch);
        this.backwardSearch = Objects.requireNonNull(backwardSearch);
    }

    public static <T> BidiBestFirstSearch<T> usingAStar(Heuristic<T> heuristic) {
        return new BidiBestFirstSearch<>(
                new AStar<>(heuristic),
                new AStar<>(heuristic)
        );
    }

    @Override
    public List<T> findShortestPath(T start,
                                    EndCondition<T> endCondition,
                                    Graph<T> graph) {
        T end = endCondition.endVertex().orElseThrow();
        var backwardEndCondition = EndCondition.endAt(start);
        forwardSearch.initializeDataStructures(start);
        backwardSearch.initializeDataStructures(end);

        while (forwardSearch.hasOpen() && backwardSearch.hasOpen()) {
            forwardSearch.updateCurrent();
            backwardSearch.updateCurrent();

            if (forwardSearch.hasVisited(forwardSearch.getCurrent()) ||
                    backwardSearch.hasVisited(backwardSearch.getCurrent())) {
                continue;
            }

            forwardSearch.closeCurrent();
            backwardSearch.closeCurrent();

            if (backwardSearch.hasVisited(forwardSearch.getCurrent())) {
                return mergePaths(start, forwardSearch.getCurrent(), end);
            }

            if (forwardSearch.hasVisited(backwardSearch.getCurrent())) {
                return mergePaths(start, backwardSearch.getCurrent(), end);
            }

            forwardSearch.expand(endCondition, graph);
            backwardSearch.expand(backwardEndCondition, graph);
        }

        return Collections.emptyList();
    }

    private List<T> mergePaths(T start,
                               T contained,
                               T end) {
        var pathTracer = new PathTracer<>(forwardSearch.getPredecessors());
        var startToContained = pathTracer.unsafeTrace(start, contained);

        pathTracer.setPredecessors(backwardSearch.getPredecessors());
        var endToContained = pathTracer.unsafeTrace(end, contained);
        return mergePaths(startToContained, endToContained);
    }

    private List<T> mergePaths(List<T> startToContained, List<T> endToContained) {
        var containedToEnd = endToContained.reversed();
        startToContained.addAll(containedToEnd.subList(1, containedToEnd.size()));
        return startToContained;
    }

}

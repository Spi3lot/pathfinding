package pathfinding;

import pathfinding.algorithms.BidiBestFirstSearch;
import pathfinding.games.FifteenPuzzle;
import pathfinding.graphs.FifteenPuzzleGraph;
import pathfinding.service.Benchmark;
import pathfinding.service.EndCondition;
import pathfinding.service.Pathfinder;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 23.02.2024, Fr.
 **/
public class FifteenPuzzleBenchmark {

    private static final int BOARD_SIZE = 4;

    public static void main(String[] args) {
        var graph = new FifteenPuzzleGraph();
        var solvedPuzzle = FifteenPuzzle.solved(BOARD_SIZE);

        var pathfinder = new Pathfinder<>(graph, BidiBestFirstSearch.usingAStar(
                (vertex, endCondition) -> (double) vertex.getLeastMoveCountTo(
                        endCondition.endVertex().orElseThrow()
                )
        ));

        var benchmark = new Benchmark(() -> pathfinder.findShortestPath(
                new FifteenPuzzle(BOARD_SIZE),
                EndCondition.endAt(solvedPuzzle)
        ));

        long totalDuration = benchmark.times(3);
        System.out.println(STR."Total duration: \{totalDuration}ms");
        System.out.println(benchmark);
    }

}

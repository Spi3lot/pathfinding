package pathfinding.benchmark;

import pathfinding.algorithms.BidiBestFirstSearch;
import pathfinding.games.FifteenPuzzle;
import pathfinding.graphs.FifteenPuzzleGraph;
import pathfinding.service.Benchmark;
import pathfinding.service.EndCondition;
import pathfinding.service.Pathfinder;

import java.util.List;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 23.02.2024, Fr.
 **/
public class FifteenPuzzleBenchmark {

    private static final int BOARD_SIZE = 4;

    public static void main(String[] args) {
        var graph = new FifteenPuzzleGraph();
        var solvedPuzzle = FifteenPuzzle.solved(BOARD_SIZE);

        var pathfinder = new Pathfinder<>(graph, new BidiBestFirstSearch<>(
                (vertex, endCondition) -> vertex.getLeastMoveCountTo(
                        endCondition.vertex().orElseThrow()
                )
        ));

        var benchmark = Benchmark.<List<FifteenPuzzle>>builder()
                .task((_) -> pathfinder.findAnyPath(
                        new FifteenPuzzle(BOARD_SIZE),
                        EndCondition.endAt(solvedPuzzle)
                ))
                .build();

        long totalDuration = benchmark.times(3);
        System.out.println(STR."Total duration: \{totalDuration}ms");
        System.out.println(benchmark);
    }

}

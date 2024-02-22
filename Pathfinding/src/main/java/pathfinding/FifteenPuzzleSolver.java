package pathfinding;

import pathfinding.algorithms.AStar;
import pathfinding.games.FifteenPuzzle;
import pathfinding.graphs.FifteenPuzzleGraph;
import pathfinding.service.Benchmark;
import pathfinding.service.EndCondition;
import pathfinding.service.Pathfinder;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 19.02.2024, Mo.
 **/
public class FifteenPuzzleSolver {

    private static final int BOARD_SIZE = 4;

    public static void main(String[] args) {
        var graph = new FifteenPuzzleGraph();
        var puzzle = new FifteenPuzzle(BOARD_SIZE);
        var solvedPuzzle = FifteenPuzzle.solved(BOARD_SIZE);

        var pathfinder = new Pathfinder<>(graph, new AStar<>(
                        (vertex, endCondition) -> (double) vertex.getLeastMoveCountTo(
                                endCondition.endVertex().orElseThrow()
                        )
        ));

        var benchmark = new Benchmark();
        var path = pathfinder.findShortestPath(puzzle, EndCondition.endAt(solvedPuzzle));
        long millis = benchmark.elapsedMillis();
        System.out.println(path);
        System.out.println(graph.sumEdgeWeights(path.orElseThrow()));
        System.out.println(STR."\{millis} ms");
    }

}

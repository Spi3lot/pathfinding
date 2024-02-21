package pathfinding;

import pathfinding.algorithms.AStar;
import pathfinding.games.FifteenPuzzle;
import pathfinding.graphs.FifteenPuzzleGraph;
import pathfinding.service.Benchmark;
import pathfinding.service.Pathfinder;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 19.02.2024, Mo.
 **/
public class FifteenPuzzleSolver {

    private static final int BOARD_SIZE = 3;

    public static void main(String[] args) {
        var graph = new FifteenPuzzleGraph();
        var puzzle = new FifteenPuzzle(BOARD_SIZE);
        var solvedPuzzle = FifteenPuzzle.solved(BOARD_SIZE);

        var pathfinder = new Pathfinder<>(
                graph,
                new AStar<>((current, end) -> (double) current.getLeastMoveCountTo(end))
        );

        var benchmark = new Benchmark();
        var path = pathfinder.findShortestPath(puzzle, solvedPuzzle);
        System.out.println(benchmark);
        System.out.println(path);
        System.out.println(graph.sumEdgeWeights(path.orElseThrow()));
    }

}

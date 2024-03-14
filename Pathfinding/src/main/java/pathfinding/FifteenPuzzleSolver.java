package pathfinding;

import pathfinding.algorithms.BidiBestFirstSearch;
import pathfinding.games.FifteenPuzzle;
import pathfinding.graphs.FifteenPuzzleGraph;
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

        var pathfinder = new Pathfinder<>(graph, new BidiBestFirstSearch<>(
                (vertex, endCondition) -> (double) vertex.getLeastMoveCountTo(
                        endCondition.vertex().orElseThrow()
                )
        ));

        var path = pathfinder.findShortestPath(puzzle, EndCondition.endAt(solvedPuzzle));
        System.out.println(path);
        System.out.println(graph.sumEdgeWeights(path));
    }

}

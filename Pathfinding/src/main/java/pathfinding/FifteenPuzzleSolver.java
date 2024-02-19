package pathfinding;

import pathfinding.algorithms.AStar;
import pathfinding.games.FifteenPuzzle;
import pathfinding.graphs.FifteenPuzzleGraph;
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
        var solvedPuzzle = new FifteenPuzzle(BOARD_SIZE, 0);

        var pathfinder = new Pathfinder<>(graph, new AStar<>() {
            @Override
            protected double h(FifteenPuzzle vertex) {
                return vertex.getLeastPossibleMoveCount();
            }
        });

        var path = pathfinder.findShortestPath(puzzle, solvedPuzzle);
        System.out.println(path);
    }

}

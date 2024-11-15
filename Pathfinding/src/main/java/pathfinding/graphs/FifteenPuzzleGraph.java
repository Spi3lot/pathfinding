package pathfinding.graphs;

import pathfinding.games.Direction;
import pathfinding.games.FifteenPuzzle;
import pathfinding.games.FifteenPuzzleBoard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A graph that helps with solving the
 * 15 puzzle in the least amount of moves.
 **/
public class FifteenPuzzleGraph implements Graph<FifteenPuzzle> {

    @Override
    public Map<FifteenPuzzle, Double> getNeighbors(FifteenPuzzle puzzle) {
        var map = new HashMap<FifteenPuzzle, Double>();

        for (var direction : Direction.values()) {
            var boardCopy = new FifteenPuzzleBoard(puzzle.board());
            boolean moved = boardCopy.move(direction);

            if (moved) {
                var puzzleCopy = new FifteenPuzzle(boardCopy);
                map.put(puzzleCopy, 1.0);
            }
        }

        return map;
    }

    @Override
    public double getEdgeWeight(FifteenPuzzle source,
                                FifteenPuzzle destination) {
        return 1;
    }

    @Override
    public double sumEdgeWeights(List<FifteenPuzzle> path) {
        return (path.isEmpty()) ? 0 : path.size() - 1;
    }

}

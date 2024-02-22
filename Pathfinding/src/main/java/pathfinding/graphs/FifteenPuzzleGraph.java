package pathfinding.graphs;

import pathfinding.games.Direction;
import pathfinding.games.FifteenPuzzle;
import pathfinding.games.FifteenPuzzleBoard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A graph that helps with solving the fifteen puzzle in the shortest amount of moves.
 **/
public class FifteenPuzzleGraph extends CachedGraph<FifteenPuzzle> {

    @Override
    public Map<FifteenPuzzle, Double> calculateNeighbors(FifteenPuzzle puzzle) {
        var map = new HashMap<FifteenPuzzle, Double>();

        for (var direction : Direction.values()) {
            var boardCopy = new FifteenPuzzleBoard(puzzle.board());
            boolean moved = boardCopy.move(direction);

            if (moved) {
                var puzzleCopy = new FifteenPuzzle(boardCopy);
                map.put(puzzleCopy, getEdgeWeight(puzzle, puzzleCopy));
            }
        }

        return map;
    }

    @Override
    public double getEdgeWeight(FifteenPuzzle source, FifteenPuzzle destination) {
        return 1;
    }

    @Override
    public double sumEdgeWeights(List<FifteenPuzzle> path) {
        return path.size() - 1.0;
    }

}

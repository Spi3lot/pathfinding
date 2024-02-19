package pathfinding.graphs;

import pathfinding.games.Direction;
import pathfinding.games.FifteenPuzzle;
import pathfinding.games.FifteenPuzzleBoard;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 19.02.2024, Mo.
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

}

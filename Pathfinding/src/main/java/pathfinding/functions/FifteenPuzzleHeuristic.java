package pathfinding.functions;

import pathfinding.games.FifteenPuzzle;
import pathfinding.service.EndCondition;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 26.02.2024, Mo.
 **/
public class FifteenPuzzleHeuristic implements Heuristic<FifteenPuzzle> {

    @Override
    public double applyAsDouble(FifteenPuzzle puzzle, EndCondition<FifteenPuzzle> endCondition) {
        return puzzle.getLeastMoveCountTo(endCondition.vertex().orElseThrow());
    }

}

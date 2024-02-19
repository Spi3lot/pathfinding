package pathfinding;

import pathfinding.games.Direction;
import pathfinding.games.FifteenPuzzle;
import pathfinding.games.FifteenPuzzleBoard;

import java.util.HashMap;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 19.02.2024, Mo.
 **/
public class HashMapTest {

    public static void main(String[] args) {
        test2(new FifteenPuzzle(4, 0));
    }

    private static void test1() {
        var map = new HashMap<FifteenPuzzle, Double>();
        var puzzle = new FifteenPuzzle(4);
        var puzzleCopy = new FifteenPuzzle(new FifteenPuzzleBoard(puzzle.board()));
        map.put(puzzle, 1.0);
        map.put(puzzleCopy, 2.0);
        System.out.println(map);
    }

    private static void test2(FifteenPuzzle puzzle) {
        var map = new HashMap<FifteenPuzzle, Double>();

        for (var direction : Direction.values()) {
            var boardCopy = new FifteenPuzzleBoard(puzzle.board());
            boolean moved = boardCopy.move(direction);

            if (moved) {
                var puzzleCopy = new FifteenPuzzle(boardCopy);
                map.put(puzzleCopy, 1.0);
            }
        }

        System.out.println(map);
    }

}

package pathfinding.games;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 17.02.2024, Sa.
 */
public record FifteenPuzzle(FifteenPuzzleBoard board) {

    public FifteenPuzzle(int size) {
        this(new FifteenPuzzleBoard(size));
        board.shuffle();
    }

    public FifteenPuzzle(int size, int shuffleCount) {
        this(new FifteenPuzzleBoard(size));
        board.shuffle(shuffleCount);
    }

    public int getLeastPossibleMoveCount() {
        return getLeastPossibleMoveCounts()
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public Map<Integer, Integer> getLeastPossibleMoveCounts() {
        var moves = HashMap.<Integer, Integer>newHashMap(board.calcArea() - 1);

        for (int j = 0; j < board.getLength(); j++) {
            for (int i = 0; i < board.getLength(); i++) {
                var position = new Position(i, j);
                int value = board.get(position);

                if (value != board.getEmptyValue()) {
                    var expectedPosition = Position.fromValue(value, board.getLength());
                    int distance = position.manhattanDistance(expectedPosition);
                    moves.put(value, distance);
                }
            }
        }

        return moves;
    }

    @Override
    public String toString() {
        return board.toString();
    }

}

package pathfinding.games;

import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper class for the fifteen puzzle board to make
 * it easier to use and extend its functionality.
 *
 * @param board the board of the puzzle
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

    public static FifteenPuzzle solved(int size) {
        var board = new FifteenPuzzleBoard(size);
        return new FifteenPuzzle(board);
    }

    public int countOutOfPlaceTiles(FifteenPuzzle desired) {
        int count = 0;

        for (int j = 0; j < board.getLength(); j++) {
            for (int i = 0; i < board.getLength(); i++) {
                var position = new Position(i, j);
                int value = board.get(position);

                if (value != board.getEmptyValue() && value != desired.board().get(position)) {
                    count++;
                }
            }
        }

        return count;
    }

    public int getLeastMoveCountTo(FifteenPuzzle desired) {
        return getLeastMoveCountsTo(desired)
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public Map<Integer, Integer> getLeastMoveCountsTo(FifteenPuzzle desired) {
        var moveCounts = HashMap.<Integer, Integer>newHashMap(board.calculateArea() - 1);
        var positions = getPositions();
        var desiredPositions = desired.getPositions();

        for (int i = 1; i < board.calculateArea(); i++) {
            var position = positions.get(i);
            var desiredPosition = desiredPositions.get(i);
            int moveCount = position.manhattanDistance(desiredPosition);
            moveCounts.put(i, moveCount);
        }

        return moveCounts;
    }

    public Map<Integer, Position> getPositions() {
        var positions = HashMap.<Integer, Position>newHashMap(board.calculateArea());

        for (int j = 0; j < board.getLength(); j++) {
            for (int i = 0; i < board.getLength(); i++) {
                var position = new Position(i, j);
                int value = board.get(position);
                positions.put(value, position);
            }
        }

        return positions;
    }

    @Override
    public String toString() {
        return board.toString();
    }

}

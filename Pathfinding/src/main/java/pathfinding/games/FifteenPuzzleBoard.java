package pathfinding.games;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 17.02.2024, Sa.
 */
//@EqualsAndHashCode
public class FifteenPuzzleBoard {

    private final int[][] board;
    private final int emptyValue;

    public FifteenPuzzleBoard(FifteenPuzzleBoard board) {
        this(board.board);
    }

    public FifteenPuzzleBoard(int size) {
        this(new int[size][size]);
        fillBoard(size);
    }

    private FifteenPuzzleBoard(int[][] board) {
        this.board = new int[board.length][board.length];
        this.emptyValue = calcArea();

        for (int j = 0; j < board.length; j++) {
            System.arraycopy(board[j], 0, this.board[j], 0, board.length);
        }
    }

    private void fillBoard(int size) {
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                var position = new Position(i, j);
                set(position, position.calcExpectedValue(size));
            }
        }
    }

    public boolean isSolved() {
        for (int j = 0; j < board.length; j++) {
            for (int i = 0; i < board.length; i++) {
                var position = new Position(i, j);

                if (get(position) != position.calcExpectedValue(board.length)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void shuffle() {
        shuffle(calcArea() * 100);
    }

    public void shuffle(int count) {
        Direction previousDirection = null;

        while (count > 0) {
            var direction = Direction.random();

            if (direction.opposite() != previousDirection && move(direction)) {
                previousDirection = direction;
                count--;
            }
        }
    }

    public boolean move(Direction direction) {
        var emptyPosition = findEmptyPosition();
        var switchPosition = emptyPosition.move(direction.opposite());

        if (switchPosition.isOnBoard(board.length)) {
            set(emptyPosition, get(switchPosition));
            set(switchPosition, emptyValue);
            return true;
        }

        return false;
    }

    public boolean move(int value) {
        var emptyPosition = findEmptyPosition();
        var valuePosition = findValuePosition(value);

        if (valuePosition.isVonNeumannAdjacent(emptyPosition)) {
            set(emptyPosition, value);
            set(valuePosition, emptyValue);
            return true;
        }

        return false;
    }

    private Position findEmptyPosition() {
        return findValuePosition(emptyValue);
    }

    private Position findValuePosition(int value) {
        return Position.fromIndex(findValueIndex(value), board.length);
    }

    private int findValueIndex(int value) {
        for (int j = 0; j < board.length; j++) {
            for (int i = 0; i < board.length; i++) {
                if (get(j, i) == value) {
                    return new Position(i, j).calcFlatIndex(board.length);
                }
            }
        }

        throw new IllegalStateException(STR."The value \{value} is not on the board.");
    }

    public int calcArea() {
        return board.length * board.length;
    }

    public int getLength() {
        return board.length;
    }

    public int getEmptyValue() {
        return emptyValue;
    }

    public int get(Position position) {
        return get(position.y(), position.x());
    }

    public int get(int row, int column) {
        return board[row][column];
    }

    private void set(Position position, int value) {
        board[position.y()][position.x()] = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FifteenPuzzleBoard that = (FifteenPuzzleBoard) o;
        return Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override
    public String toString() {
        var outerJoiner = new StringJoiner("\n");

        for (int[] row : board) {
            var innerJoiner = new StringJoiner("\t");

            for (int value : row) {
                innerJoiner.add(STR."\{value}");
            }

            outerJoiner.merge(innerJoiner);
        }

        return outerJoiner.toString();
    }

}

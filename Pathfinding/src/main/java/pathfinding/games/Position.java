package pathfinding.games;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 17.02.2024, Sa.
 */
public record Position(int x, int y) {

    public static Position fromValue(int value, int boardLength) {
        return fromIndex(value - 1, boardLength);
    }

    public static Position fromIndex(int index, int boardLength) {
        return new Position(index % boardLength, index / boardLength);
    }

    public Position move(Direction direction) {
        return switch (direction) {
            case UP -> new Position(x, y - 1);
            case DOWN -> new Position(x, y + 1);
            case LEFT -> new Position(x - 1, y);
            case RIGHT -> new Position(x + 1, y);
        };
    }

    public boolean isOnBoard(int boardLength) {
        return x >= 0 && x < boardLength && y >= 0 && y < boardLength;
    }

    public boolean isVonNeumannAdjacent(Position other) {
        return manhattanDistance(other) == 1;
    }

    public int manhattanDistance(Position other) {
        return Math.abs(x - other.x) + Math.abs(y - other.y);
    }

    public int calculateExpectedValue(int boardLength) {
        return 1 + calculateFlatIndex(boardLength);
    }

    public int calculateFlatIndex(int boardLength) {
        return y * boardLength + x;
    }

}

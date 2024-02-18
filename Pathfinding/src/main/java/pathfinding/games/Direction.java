package pathfinding.games;

import processing.core.PConstants;

import java.util.Random;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 17.02.2024, Sa.
 */
public enum Direction {

    UP, DOWN, LEFT, RIGHT;

    private static final Random RANDOM = new Random();

    public static Direction random() {
        return values()[RANDOM.nextInt(values().length)];
    }

    public static Direction fromKey(char key) {
        return switch (Character.toUpperCase(key)) {
            case 'W' -> UP;
            case 'S' -> DOWN;
            case 'A' -> LEFT;
            case 'D' -> RIGHT;
            default -> null;
        };
    }

    public static Direction fromKeyCode(int keyCode) {
        return switch (keyCode) {
            case PConstants.UP -> UP;
            case PConstants.DOWN -> DOWN;
            case PConstants.LEFT -> LEFT;
            case PConstants.RIGHT -> RIGHT;
            default -> null;
        };
    }

    public Direction opposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }

}

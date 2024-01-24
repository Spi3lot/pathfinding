package pathfinding;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 24.01.2024, Mi.
 **/
public class RecursionTest {

    public static void main(String[] args) {
        recurse();
    }

    private static void recurse() {
        recurse(0);
    }

    private static void recurse(int depth) {
        System.out.println(depth);

        try {
            recurse(depth + 1);
        } finally {
            System.err.println("Entering");
            recurse(depth + 1);
            System.err.println("Exited");
        }
    }

}

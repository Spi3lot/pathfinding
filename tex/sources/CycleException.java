package pathfinding.algorithms;

/**
 * An exception thrown when the program detects that it
 * got stuck in a cycle during the traversal of a graph.
 */
public class CycleException extends RuntimeException {

    public CycleException() {
        super();
    }

    public CycleException(String message) {
        super(message);
    }

}

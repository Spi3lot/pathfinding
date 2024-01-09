package pathfinding.exceptions;

/**
 * @author : Emilio Zottel (5AHIF)
 * @since : 09.01.2024, Di.
 **/
public class CycleException extends RuntimeException {

    public CycleException() {
        super();
    }

    public CycleException(String message) {
        super(message);
    }

}

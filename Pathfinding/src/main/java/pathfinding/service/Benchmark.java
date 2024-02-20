package pathfinding.service;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 20.02.2024, Di.
 */
public class Benchmark {

    private long lastResetMillis;

    public Benchmark() {
        resetTimer();
    }

    public void resetTimer() {
        lastResetMillis = System.currentTimeMillis();
    }

    public long elapsedMillis() {
        return System.currentTimeMillis() - lastResetMillis;
    }

    @Override
    public String toString() {
        return STR."\{elapsedMillis()} ms";
    }

}

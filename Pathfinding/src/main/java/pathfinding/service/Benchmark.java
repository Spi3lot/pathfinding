package pathfinding.service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 20.02.2024, Di.
 */
@RequiredArgsConstructor
public class Benchmark {

    private final List<Long> durations = new ArrayList<>();
    private final Runnable runnable;
    private long lastResetMillis;

    public long times(int times) {
        long totalDuration = 0;

        for (int i = 0; i < times; i++) {
            resetTimer();
            runnable.run();
            long elapsedMillis = elapsedMillis();
            totalDuration += elapsedMillis;
            durations.add(elapsedMillis);
        }

        return totalDuration;
    }

    private void resetTimer() {
        lastResetMillis = System.currentTimeMillis();
    }

    private long elapsedMillis() {
        return System.currentTimeMillis() - lastResetMillis;
    }

    public double standardDeviationMillis() {
        double mean = meanMillis();

        double sum = durations.stream()
                .mapToDouble(d -> d - mean)
                .map(d -> d * d)
                .sum();

        double variance = sum / durations.size();
        return Math.sqrt(variance);
    }

    public double meanMillis() {
        return durations.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0);
    }

    @Override
    public String toString() {
        return STR."μ=\{meanMillis()} ms, σ=\{standardDeviationMillis()} ms";
    }

}

package pathfinding.service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;

import static java.util.FormatProcessor.FMT;

/**
 * Utility class to measure the duration of a given task.
 * It can also calculate the mean and standard deviation of the durations.
 * The task is given as a {@link Runnable}.
 */
@RequiredArgsConstructor
public class Benchmark {

    private final List<Long> durations = new ArrayList<>();
    private final IntConsumer task;
    private long lastResetMillis;

    public long times(int times) {
        long totalDuration = 0;

        for (int i = 0; i < times; i++) {
            resetTimer();
            task.accept(i);
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

        double squaredErrorSum = durations.stream()
                .mapToDouble(duration -> duration - mean)
                .map(error -> error * error)
                .sum();

        double variance = squaredErrorSum / durations.size();
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
        return FMT."μ=%.3f\{meanMillis()} ms, σ=%.3f\{standardDeviationMillis()} ms";
    }

}

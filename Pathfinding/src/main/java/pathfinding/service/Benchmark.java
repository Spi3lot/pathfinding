package pathfinding.service;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.ObjLongConsumer;

/**
 * Utility class to measure the duration of a given task.
 * It can also calculate the mean and standard deviation of the durations.
 * The task is given as a {@link Runnable}.
 */
@Builder
public class Benchmark<T> {

    private final List<Long> durations = new ArrayList<>();

    private long lastResetNanos;

    private IntFunction<T> task;

    @Builder.Default
    private ObjLongConsumer<T> postProcessor = (_, _) -> {};

    public long times(int times) {
        long totalNanos = 0;

        for (int i = 0; i < times; i++) {
            resetTimer();
            T result = task.apply(i);
            long nanos = elapsedNanos();
            totalNanos += nanos;
            durations.add(nanos);
            postProcessor.accept(result, nanos);
        }

        return totalNanos;
    }

    private void resetTimer() {
        lastResetNanos = System.nanoTime();
    }

    private long elapsedNanos() {
        return System.nanoTime() - lastResetNanos;
    }

    public NormalDistribution calculateDistribution() {
        return NormalDistribution.of(durations);
    }

    @Override
    public String toString() {
        return calculateDistribution().toString("ms");
    }

}

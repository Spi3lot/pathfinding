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

    private long lastResetMillis;

    private IntFunction<T> task;

    @Builder.Default
    private ObjLongConsumer<T> postProcessor = (_, _) -> {};

    public long times(int times) {
        long totalDuration = 0;

        for (int i = 0; i < times; i++) {
            resetTimer();
            T result = task.apply(i);
            long elapsedMillis = elapsedMillis();
            totalDuration += elapsedMillis;
            durations.add(elapsedMillis);
            postProcessor.accept(result, elapsedMillis);
        }

        return totalDuration;
    }

    private void resetTimer() {
        lastResetMillis = System.currentTimeMillis();
    }

    private long elapsedMillis() {
        return System.currentTimeMillis() - lastResetMillis;
    }

    public NormalDistribution calculateDistribution() {
        return NormalDistribution.of(durations);
    }

    @Override
    public String toString() {
        return calculateDistribution().toString("ms");
    }

}

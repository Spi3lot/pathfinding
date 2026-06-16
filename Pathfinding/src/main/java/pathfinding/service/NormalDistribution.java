package pathfinding.service;

import java.util.Collection;
import java.util.Locale;

/**
 * Represents a normal distribution.
 *
 * @param mean              the mean of the distribution
 * @param standardDeviation the standard deviation of the distribution
 */
public record NormalDistribution(double mean, double standardDeviation) {

    public static NormalDistribution of(Collection<Long> longs) {
        double mean = calculateMean(longs);
        double standardDeviation = calculateStandardDeviation(mean, longs);
        return new NormalDistribution(mean, standardDeviation);
    }

    public static double calculateMean(Collection<Long> longs) {
        return longs.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0);
    }

    public static double calculateStandardDeviation(Collection<Long> longs) {
        double mean = calculateMean(longs);
        return calculateStandardDeviation(mean, longs);
    }

    private static double calculateStandardDeviation(double mean, Collection<Long> longs) {
        double squaredErrorSum = longs.stream()
                .mapToDouble(value -> value - mean)
                .map(error -> error * error)
                .sum();

        double variance = squaredErrorSum / longs.size();
        return Math.sqrt(variance);
    }

    @Override
    public String toString() {
        return toString("units");
    }

    public String toString(String unit) {
        return toString(unit, 3);
    }

    public String toString(String unit, int decimals) {
        var result = STR."μ=%.\{decimals}f \{unit}, σ=%.\{decimals}f \{unit}";
        return String.format(Locale.ROOT, result, mean, standardDeviation);
    }

}

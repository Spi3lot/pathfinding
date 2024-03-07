package pathfinding.benchmark;

import pathfinding.graphs.CompleteGraph;
import pathfinding.graphs.Graph;
import pathfinding.service.Benchmark;
import pathfinding.service.EndCondition;
import processing.core.PVector;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 05.03.2024, Di.
 */
public class CompleteGraphBenchmark implements PVectorBenchmark {

    private static final int ITERATION_COUNT = 1000;
    private static final Graph<PVector> GRAPH = new CompleteGraph<>(VERTICES);

    public static void main(String[] args) {
        new CompleteGraphBenchmark().runBenchmark();
    }

    /**
     * Runs the benchmark for the complete graph.
     * The average degree of the graph is not calculated, as it is known to be |V| - 1.
     */
    @Override
    public void runBenchmark() {
        var random = new Random();

        var startVertices = random.ints(ITERATION_COUNT, 0, VERTICES.size())
                .mapToObj(VERTICES::get)
                .toList();

        var endConditions = random.ints(ITERATION_COUNT, 0, VERTICES.size())
                .mapToObj(VERTICES::get)
                .map(EndCondition::endAt)
                .toList();

        try (var csvWriter = csvWriter()) {
            csvWriter.writeNext(new String[]{"Algorithm", "Path Length", "Duration (µs)", "Average Degree"});

            for (var algorithm : SPF_ALGORITHMS) {
                var benchmark = Benchmark.<List<PVector>>builder()
                        .task(iteration -> algorithm.findAnyPath(
                                startVertices.get(iteration),
                                endConditions.get(iteration),
                                GRAPH
                        ))
                        .postProcessor((_, path, nanos) -> csvWriter.writeNext(new String[]{
                                algorithm.getClass().getSimpleName(),
                                STR."\{path.size()}",
                                STR."\{nanos / 1e3}",
                                STR."\{VERTICES.size() * (VERTICES.size() - 1) / 2}"
                        }))
                        .build();

                long nanos = benchmark.times(ITERATION_COUNT);
                System.out.println(STR."\{nanos / 1e6} ms for \{algorithm.getClass().getSimpleName()}");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String outputFileName() {
        return "complete-benchmark.csv";
    }

}

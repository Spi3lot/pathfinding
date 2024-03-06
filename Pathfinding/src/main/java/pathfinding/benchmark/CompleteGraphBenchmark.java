package pathfinding.benchmark;

import pathfinding.graphs.CompleteGraph;
import pathfinding.graphs.Graph;
import pathfinding.service.Benchmark;
import pathfinding.service.EndCondition;
import processing.core.PVector;

import java.io.IOException;
import java.util.List;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 05.03.2024, Di.
 */
public class CompleteGraphBenchmark implements PVectorBenchmark {

    private static final Graph<PVector> GRAPH = new CompleteGraph<>(VERTICES);

    public static void main(String[] args) {

    }

    /**
     * Runs the benchmark for the complete graph.
     * The average degree of the graph is not calculated, as it is known to be |V| - 1.
     */
    @Override
    public void runBenchmark() {
        var start = VERTICES.getFirst();
        var endCondition = EndCondition.endAt(VERTICES.getLast());

        try (var csvWriter = csvWriter()) {
            csvWriter.writeNext(new String[]{"Algorithm", "Path Length", "Duration (µs)"});

            for (var algorithm : SPF_ALGORITHMS) {
                var benchmark = Benchmark.<List<PVector>>builder()
                        .task((_) -> algorithm.findAnyPath(
                                start,
                                endCondition,
                                GRAPH
                        ))
                        .postProcessor((_, path, nanos) -> csvWriter.writeNext(new String[]{
                                algorithm.getClass().getSimpleName(),
                                STR."\{path.size()}",
                                STR."\{nanos / 1e3}"
                        }))
                        .build();

                long nanos = benchmark.times(1);
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

package pathfinding.benchmark;

import pathfinding.service.Benchmark;
import pathfinding.service.EndCondition;
import processing.core.PVector;

import java.io.IOException;
import java.util.List;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 23.02.2024, Fr.
 */
public class ModifiableGraphBenchmark implements PVectorBenchmark {

    private static final boolean DISCONNECT_START = false;
    private static final boolean DISCONNECT_END = false;

    public static void main(String[] args) throws IOException {
        new ModifiableGraphBenchmark().runBenchmark();
    }

    /**
     * Runs the benchmark for modifiable graphs.
     * The average degree of the graph is calculated.
     */
    @Override
    public void runBenchmark() {
        System.out.println(STR."Running benchmarks with an edge probability of \{EDGE_PROBABILITY}...");
        var start = VERTICES.getFirst();
        var endCondition = EndCondition.endAt(VERTICES.getLast());

        try (var csvWriter = csvWriter()) {
            csvWriter.writeNext(new String[]{"Algorithm", "Path Length", "Duration (µs)", "Average Degree"});

            if (DISCONNECT_START) {
                PVectorBenchmark.disconnectVertexInAllGraphs(start);
            }

            if (DISCONNECT_END) {
                PVectorBenchmark.disconnectVertexInAllGraphs(endCondition.vertex().orElseThrow());
            }

            for (var algorithm : SPF_ALGORITHMS) {
                var benchmark = Benchmark.<List<PVector>>builder()
                        .task((iteration) -> algorithm.findAnyPath(
                                start,
                                endCondition,
                                GRAPHS.get(iteration)
                        ))
                        .postProcessor((iteration, path, nanos) -> csvWriter.writeNext(new String[]{
                                algorithm.getClass().getSimpleName(),
                                STR."\{path.size()}",
                                STR."\{nanos / 1e3}",
                                STR."\{GRAPHS.get(iteration).calculateAverageDegree()}"
                        }))
                        .build();

                long nanos = benchmark.times(GRAPH_COUNT);
                System.out.println(STR."\{nanos / 1e6} ms for \{algorithm.getClass().getSimpleName()}");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String outputFileName() {
        return "modifiable-benchmark.csv";
    }

}

package pathfinding.benchmark;

import pathfinding.graphs.ModifiableGraph;
import pathfinding.service.Benchmark;
import pathfinding.service.EndCondition;
import processing.core.PVector;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 23.02.2024, Fr.
 */
public class ModifiableGraphBenchmark implements GraphBenchmark {

    private static final boolean DISCONNECT_START = false;
    private static final boolean DISCONNECT_END = false;

    private final List<ModifiableGraph<PVector>> graphs = IntStream.range(0, GRAPH_COUNT)
            .mapToObj(_ -> RANDOMIZER.randomizeUndirectedEdges())
            .toList();

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
            csvWriter.writeNext(new String[]{
                    "Algorithm",
                    "Path Length",
                    "Path Weight",
                    "Duration (µs)",
                    "Visited Vertices",
                    "Average Degree",
                    "Average Path Degree"
            });

            if (DISCONNECT_START) {
                disconnectVertexInAllGraphs(start);
            }

            if (DISCONNECT_END) {
                disconnectVertexInAllGraphs(endCondition.vertex().orElseThrow());
            }

            for (var algorithm : SPF_ALGORITHMS) {
                var benchmark = Benchmark.<List<PVector>>builder()
                        .task(iteration -> algorithm.findAnyPath(
                                start,
                                endCondition,
                                graphs.get(iteration)
                        ))
                        .postProcessor((iteration, path, nanos) -> {
                            var graph = graphs.get(iteration);

                            double averagePathDegree = path.stream()
                                    .mapToDouble(graph::getDegree)
                                    .average()
                                    .orElse(0);

                            csvWriter.writeNext(new String[]{
                                    algorithm.getClass().getSimpleName(),
                                    STR."\{path.size()}",
                                    STR."\{graph.sumEdgeWeights(path)}",
                                    STR."\{nanos / 1e3}",
                                    STR."\{algorithm.getVisitedVertexCount()}",
                                    STR."\{graph.calculateAverageDegree()}",
                                    STR."\{averagePathDegree}"
                            });
                        })
                        .build();

                long nanos = benchmark.times(GRAPH_COUNT);
                System.out.println(STR."\{nanos / 1e6} ms for \{algorithm.getClass().getSimpleName()}");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void disconnectVertexInAllGraphs(PVector vertex) {
        for (var graph : graphs) {
            graph.getNeighbors(vertex)
                    .keySet()
                    .stream()
                    .toList()  // copy to avoid concurrent modification
                    .forEach(neighbor -> graph.removeEdge(vertex, neighbor));
        }
    }

    @Override
    public String outputFileName() {
        return "modifiable-benchmark.csv";
    }

}

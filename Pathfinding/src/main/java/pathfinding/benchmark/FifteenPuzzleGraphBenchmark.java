package pathfinding.benchmark;

import pathfinding.algorithms.AStar;
import pathfinding.algorithms.BidiBestFirstSearch;
import pathfinding.algorithms.PathfindingAlgorithm;
import pathfinding.functions.Heuristic;
import pathfinding.games.FifteenPuzzle;
import pathfinding.graphs.FifteenPuzzleGraph;
import pathfinding.graphs.Graph;
import pathfinding.service.Benchmark;
import pathfinding.service.EndCondition;
import pathfinding.service.Pathfinder;
import processing.core.PVector;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 23.02.2024, Fr.
 */
public class FifteenPuzzleGraphBenchmark implements GraphBenchmark {

    private static final Graph<FifteenPuzzle> GRAPH = new FifteenPuzzleGraph();

    private static final Heuristic<FifteenPuzzle> HEURISTIC = (vertex, endCondition) -> vertex.getLeastMoveCountTo(endCondition.vertex().orElseThrow());

    private static final List<PathfindingAlgorithm<FifteenPuzzle>> ALGORITHMS = List.of(
            //new BidiBestFirstSearch<>(HEURISTIC)
            new AStar<>(HEURISTIC)
    );

    public static void main(String[] args) throws IOException {
        new FifteenPuzzleGraphBenchmark().runBenchmark();
    }

    /**
     * Runs the benchmark for modifiable graphs.
     * The average degree of the graph is calculated.
     */
    @Override
    public void runBenchmark() {
        var start = new FifteenPuzzle(4);
        var endCondition = EndCondition.endAt(FifteenPuzzle.solved(4));

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

            for (var algorithm : ALGORITHMS) {
                var benchmark = Benchmark.<List<FifteenPuzzle>>builder()
                        .task(_ -> algorithm.findShortestPath(
                                start,
                                endCondition,
                                GRAPH
                        ))
                        .postProcessor((_, path, nanos) -> {
                            double averagePathDegree = path.stream()
                                    .mapToDouble(GRAPH::getDegree)
                                    .average()
                                    .orElse(0);

                            csvWriter.writeNext(new String[]{
                                    algorithm.getClass().getSimpleName(),
                                    STR."\{path.size()}",
                                    STR."\{GRAPH.sumEdgeWeights(path)}",
                                    STR."\{nanos / 1e3}",
                                    STR."\{algorithm.getVisitedVertexCount()}",
                                    STR."\{(4 * 4 + 3 * 8 + 2 * 4) / 16.0}",
                                    STR."\{averagePathDegree}"
                            });

                            start.board().shuffle();
                        })
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
        return "15-benchmark.csv";
    }

}

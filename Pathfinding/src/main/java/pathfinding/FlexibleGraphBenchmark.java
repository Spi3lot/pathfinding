package pathfinding;

import com.opencsv.CSVWriter;
import pathfinding.algorithms.*;
import pathfinding.functions.Heuristic;
import pathfinding.service.Benchmark;
import pathfinding.service.EndCondition;
import pathfinding.service.FlexibleGraphRandomizer;
import processing.core.PVector;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 23.02.2024, Fr.
 */
public class FlexibleGraphBenchmark {

    private static final int GRAPH_COUNT = 1000;
    private static final int VERTEX_COUNT_PER_GRAPH = 1000;
    private static final int AVERAGE_EDGE_COUNT_PER_GRAPH = 100;
    private static final double EDGE_PROBABILITY = AVERAGE_EDGE_COUNT_PER_GRAPH * 2.0 / (VERTEX_COUNT_PER_GRAPH * (VERTEX_COUNT_PER_GRAPH - 1));
    private static final Heuristic<PVector> HEURISTIC = (vertex, endCondition) -> vertex.dist(endCondition.vertex().orElseThrow());
    private static final String UNIT = "ns";

    public static void main(String[] args) throws IOException {
        System.out.println("Generating random vertices...");

        var vertices = IntStream.range(0, VERTEX_COUNT_PER_GRAPH)
                .mapToObj(_ -> PVector.random2D())
                .toList();

        var start = vertices.getFirst();
        var endCondition = EndCondition.endAt(vertices.getLast());

        var randomizer = FlexibleGraphRandomizer.<PVector>builder()
                .vertices(vertices)
                .weightFunction((source, destination) -> source.dist(destination))
                .edgeProbability(EDGE_PROBABILITY)
                .build();

        System.out.println("Generating random graphs...");

        var graphs = IntStream.range(0, GRAPH_COUNT)
                .mapToObj(_ -> randomizer.randomizeUndirectedEdges())
                .toList();

        var searches = List.<PathfindingAlgorithm<PVector>>of(
                BidiBestFirstSearch.usingAStar(HEURISTIC),
                new AStar<>(HEURISTIC),
                new Dijkstra<>(),
//                new RecursiveDFS<>(),
//                new DepthFirstSearch<>(),
                new BreadthFirstSearch<>()
        );

        var csvWriter = new CSVWriter(new FileWriter("benchmark.csv"));
        csvWriter.writeNext(new String[]{"Algorithm", "Path Length", UNIT});
        System.out.println(STR."Running benchmarks with an edge probability of \{EDGE_PROBABILITY}...");

        for (var search : searches) {
            var benchmark = Benchmark.<List<PVector>>builder()
                    .task((iteration) -> search.findAnyPath(
                            start,
                            endCondition,
                            graphs.get(iteration))
                    )
                    .postProcessor((path, nanos) -> csvWriter.writeNext(new String[]{
                            search.getClass().getSimpleName(),
                            STR."\{path.size()}",
                            STR."\{nanos}",
                    }))
                    .build();

            long nanos = benchmark.times(GRAPH_COUNT);
            System.out.println(STR."\{nanos / 1e6} ms for \{search.getClass().getSimpleName()}");
        }

        csvWriter.close();
    }

}

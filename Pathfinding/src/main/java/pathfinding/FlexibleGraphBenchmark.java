package pathfinding;

import pathfinding.algorithms.*;
import pathfinding.functions.Heuristic;
import pathfinding.service.Benchmark;
import pathfinding.service.EndCondition;
import pathfinding.service.FlexibleGraphRandomizer;
import pathfinding.service.NormalDistribution;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 23.02.2024, Fr.
 */
public class FlexibleGraphBenchmark {

    private static final int VERTEX_COUNT = 250;
    private static final int GRAPH_COUNT = 250;
    private static final Heuristic<PVector> HEURISTIC = (vertex, endCondition) -> vertex.dist(endCondition.vertex().orElseThrow());
    private static final String UNIT = "ms";

    public static void main(String[] args) {
        var vertices = IntStream.range(0, VERTEX_COUNT)
                .mapToObj(_ -> PVector.random2D())
                .toList();

        var randomizer = FlexibleGraphRandomizer.<PVector>builder()
                .vertices(vertices)
                .weightFunction((source, destination) -> source.dist(destination))
                .edgeProbability(0.1)
                .build();

        var graphs = IntStream.range(0, GRAPH_COUNT)
                .mapToObj(_ -> randomizer.randomizeUndirectedEdges())
                .toList();

        var searches = List.<PathfindingAlgorithm<PVector>>of(
                BidiBestFirstSearch.usingAStar(HEURISTIC),
                new AStar<>(HEURISTIC),
                new Dijkstra<>(),
                new RecursiveDFS<>(),
                new DepthFirstSearch<>(),
                new BreadthFirstSearch<>()
        );

        searches.forEach(search -> {
                    var durations = new HashMap<Integer, List<Long>>();

                    var benchmark = Benchmark.<List<PVector>>builder()
                            .task((iteration) -> search.findAnyPath(
                                    vertices.getFirst(),
                                    EndCondition.endAt(vertices.getLast()),
                                    graphs.get(iteration)
                            ))
                            .postProcessor((path, millis) -> durations.computeIfAbsent(path.size(), _ -> new ArrayList<>()).add(millis))
                            .build();

                    benchmark.times(GRAPH_COUNT);
                    System.out.println(search.getClass().getSimpleName());
                    System.out.println(benchmark);
                    durations.forEach((length, values) -> System.out.println(STR."\{length}: \{NormalDistribution.of(values).toString(UNIT)}"));
                    System.out.println();
                }
        );
    }

}

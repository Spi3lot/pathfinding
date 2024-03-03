package pathfinding;

import pathfinding.algorithms.*;
import pathfinding.functions.Heuristic;
import pathfinding.service.Benchmark;
import pathfinding.service.EndCondition;
import pathfinding.service.FlexibleGraphRandomizer;
import processing.core.PVector;

import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 23.02.2024, Fr.
 */
public class FlexibleGraphBenchmark {

    private static final int VERTEX_COUNT = 50;
    private static final int GRAPH_COUNT = 100;
    private static final Heuristic<PVector> HEURISTIC = (vertex, endCondition) -> vertex.dist(endCondition.endVertex().orElseThrow());

    public static void main(String[] args) {
        var vertices = IntStream.range(0, VERTEX_COUNT)
                .mapToObj(_ -> PVector.random2D())
                .toList();

        var randomizer = FlexibleGraphRandomizer.<PVector>builder()
                .vertices(vertices)
                .weightFunction((source, destination) -> source.dist(destination))
                .build();

        var graphs = IntStream.range(0, GRAPH_COUNT)
                .mapToObj(_ -> randomizer.randomizeUndirectedEdges())
                .toList();

        var searches = List.<PathfindingAlgorithm<PVector>>of(
                new BreadthFirstSearch<>(),
                new DepthFirstSearch<>(),
                new RecursiveDFS<>(),
                new Dijkstra<>(),
                new AStar<>(HEURISTIC),
                BidiBestFirstSearch.aStar(HEURISTIC)
        );

        searches.forEach(search -> {
                    var benchmark = new Benchmark((iteration) -> search.findShortestPath(
                            vertices.getFirst(),
                            EndCondition.endAt(vertices.getLast()),
                            graphs.get(iteration)
                    ));

                    benchmark.times(GRAPH_COUNT);
                    System.out.println(benchmark);
                }
        );
    }

}

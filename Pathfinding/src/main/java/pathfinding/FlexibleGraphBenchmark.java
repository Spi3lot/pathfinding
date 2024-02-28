package pathfinding;

import pathfinding.algorithms.AStar;
import pathfinding.service.Benchmark;
import pathfinding.service.EndCondition;
import pathfinding.service.FlexibleGraphRandomizer;
import processing.core.PVector;

import java.util.stream.IntStream;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 23.02.2024, Fr.
 */
public class FlexibleGraphBenchmark {

    public static void main(String[] args) {
        var vertices = IntStream.range(0, 1000)
                .mapToObj(_ -> PVector.random2D())
                .toList();

        var randomizer = FlexibleGraphRandomizer.<PVector>builder()
                .vertices(vertices)
                .maxRandomWeight(10)
                .build();

        var search = new AStar<PVector>(
                (vertex, endCondition) -> vertex.dist(endCondition.endVertex().orElseThrow())
        );

        var benchmark = new Benchmark(() -> search.findShortestPath(
                vertices.getFirst(),
                EndCondition.endAt(vertices.getLast()),
                randomizer.randomizeUndirectedEdges()
        ));

        benchmark.times(10);
        System.out.println(benchmark);
    }

}

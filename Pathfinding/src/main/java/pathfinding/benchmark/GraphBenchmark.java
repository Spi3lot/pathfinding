package pathfinding.benchmark;

import com.opencsv.CSVWriter;
import pathfinding.algorithms.*;
import pathfinding.functions.Heuristic;
import pathfinding.service.ModifiableGraphRandomizer;
import processing.core.PVector;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 05.03.2024, Di.
 */
public interface GraphBenchmark {

    int GRAPH_COUNT = 1;

    int VERTEX_COUNT_PER_GRAPH = 1000;

    int AVERAGE_EDGE_COUNT_PER_GRAPH = 1000;

    double EDGE_PROBABILITY = AVERAGE_EDGE_COUNT_PER_GRAPH * 2.0 / (VERTEX_COUNT_PER_GRAPH * (VERTEX_COUNT_PER_GRAPH - 1));

    Heuristic<PVector> HEURISTIC = (vertex, endCondition) -> vertex.dist(endCondition.vertex().orElseThrow());

    List<PVector> VERTICES = IntStream.range(0, VERTEX_COUNT_PER_GRAPH)
            .mapToObj(_ -> PVector.random2D())
            .toList();

    ModifiableGraphRandomizer<PVector> RANDOMIZER = ModifiableGraphRandomizer.<PVector>builder()
            .vertices(VERTICES)
            .weightFunction((source, destination) -> source.dist(destination))
            .edgeProbability(EDGE_PROBABILITY)
            .build();

    List<PathfindingAlgorithm<PVector>> ALGORITHMS = List.of(
            new BidiBestFirstSearch<>(HEURISTIC),
            new AStar<>(HEURISTIC),
            new Dijkstra<>(),
            new BreadthFirstSearch<>(),
            new DepthFirstSearch<>(),
            new RecursiveDFS<>()
    );

    List<PathfindingAlgorithm<PVector>> ITERATIVE_ALGORITHMS = ALGORITHMS.subList(0, 5);

    /**
     * The algorithms reasonable to be used for finding the shortest paths.
     * Although the DFS algorithms implement the findShortestPath method,
     * they are not included in this list, because they are not designed
     * for this purpose and take a long time to complete.
     * <p>
     * SPF stands for "Shortest Path First".
     */
    List<PathfindingAlgorithm<PVector>> SPF_ALGORITHMS = ALGORITHMS.subList(0, 4);

    List<PathfindingAlgorithm<PVector>> BEFS_ALGORITHMS = ALGORITHMS.subList(0, 3);

    List<PathfindingAlgorithm<PVector>> UNIDIRECTIONAL_BEFS_ALGORITHMS = ALGORITHMS.subList(1, 3);

    void runBenchmark();

    String outputFileName();

    default CSVWriter csvWriter() throws IOException {
        return new CSVWriter(new FileWriter(outputFileName()));
    }


}

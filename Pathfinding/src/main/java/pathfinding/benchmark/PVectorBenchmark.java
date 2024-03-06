package pathfinding.benchmark;

import com.opencsv.CSVWriter;
import pathfinding.algorithms.*;
import pathfinding.functions.Heuristic;
import pathfinding.graphs.FlexibleGraph;
import pathfinding.service.FlexibleGraphRandomizer;
import processing.core.PVector;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 05.03.2024, Di.
 */
public interface PVectorBenchmark {

    int GRAPH_COUNT = 1000;

    int VERTEX_COUNT_PER_GRAPH = 1000;

    int AVERAGE_EDGE_COUNT_PER_GRAPH = 1000;

    double EDGE_PROBABILITY = AVERAGE_EDGE_COUNT_PER_GRAPH * 2.0 / (VERTEX_COUNT_PER_GRAPH * (VERTEX_COUNT_PER_GRAPH - 1));

    Heuristic<PVector> HEURISTIC = (vertex, endCondition) -> vertex.dist(endCondition.vertex().orElseThrow());

    List<PVector> VERTICES = IntStream.range(0, VERTEX_COUNT_PER_GRAPH)
            .mapToObj(_ -> PVector.random2D())
            .toList();

    FlexibleGraphRandomizer<PVector> RANDOMIZER = FlexibleGraphRandomizer.<PVector>builder()
            .vertices(VERTICES)
            .weightFunction((source, destination) -> source.dist(destination))
            .edgeProbability(EDGE_PROBABILITY)
            .build();

    List<FlexibleGraph<PVector>> GRAPHS = IntStream.range(0, GRAPH_COUNT)
            .mapToObj(_ -> RANDOMIZER.randomizeUndirectedEdges())
            .toList();

    List<PathfindingAlgorithm<PVector>> ALGORITHMS = List.of(
            BidiBestFirstSearch.usingAStar(HEURISTIC),
            new AStar<>(HEURISTIC),
            new Dijkstra<>(),
            new BreadthFirstSearch<>(),
            new DepthFirstSearch<>(),
            new RecursiveDFS<>()
    );

    /**
     * The algorithms reasonable to be used for finding the shortest paths.
     * Although the DFS algorithms implement the findShortestPath method,
     * they are not included in this list, because they are not designed
     * for this purpose and take a long time to complete.
     * <p>
     * SPF stands for "Shortest Path First".
     */
    List<PathfindingAlgorithm<PVector>> SPF_ALGORITHMS = ALGORITHMS.subList(0, 4);

    static void disconnectVertexInAllGraphs(PVector vertex) {
        for (var graph : GRAPHS) {
            graph.getNeighbors(vertex)
                    .keySet()
                    .stream()
                    .toList()  // copy to avoid concurrent modification
                    .forEach(neighbor -> graph.removeEdge(vertex, neighbor));
        }
    }

    void runBenchmark();

    String outputFileName();

    default CSVWriter csvWriter() throws IOException {
        return new CSVWriter(new FileWriter(outputFileName()));
    }


}

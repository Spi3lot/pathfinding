package pathfinding.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pathfinding.graphs.Graph;

import java.util.List;
import java.util.Random;
import java.util.function.DoubleUnaryOperator;

/**
 * Randomizes edges between vertices of a graph.
 *
 * @param <T> the type of the value each vertex holds
 */
@Builder
@Getter
@Setter
public class GraphRandomizer<T> {

    private static final Random RANDOM = new Random();

    /**
     * The probability of an edge being created between two vertices
     */
    @Builder.Default
    private double edgeProbability = 0.5;

    /**
     * The minimum value of the random value for the weight of an edge (inclusive)
     */
    @Builder.Default
    private double minRandomWeight = 1;

    /**
     * The maximum value of the random value for the weight of an edge (exclusive)
     */
    @Builder.Default
    private double maxRandomWeight = 1;

    /**
     * The function used to map the random weight to the actual weight
     */
    @Builder.Default
    private DoubleUnaryOperator weightMapping = Math::round;

    /**
     * The vertices of the graph
     */
    @Builder.Default
    private List<T> vertices = List.of();


    public Graph<T> randomizeDirectedEdges() {
        var graph = createFilledGraph(true);

        for (var source : vertices) {
            for (var destination : vertices) {
                if (source != destination && random() < edgeProbability) {
                    graph.addEdge(source, destination, randomWeight());
                }
            }
        }

        return graph;
    }

    public Graph<T> randomizeUndirectedEdges() {
        var graph = createFilledGraph(false);

        for (int i = 0; i < vertices.size(); i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                if (random() < edgeProbability) {
                    var source = vertices.get(i);
                    var destination = vertices.get(j);
                    graph.addEdge(source, destination, randomWeight());
                }
            }
        }

        return graph;
    }

    private Graph<T> createFilledGraph(boolean directed) {
        var graph = new Graph<T>(directed);
        graph.addVertices(vertices);
        return graph;
    }

    private double random() {
        return RANDOM.nextDouble();
    }

    private double randomWeight() {
        double random = RANDOM.nextDouble(
                minRandomWeight,
                maxRandomWeight
        );

        return weightMapping.applyAsDouble(random);
    }

}

package pathfinding.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pathfinding.graphs.FlexibleGraph;
import pathfinding.graphs.ModifiableGraph;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.ToDoubleBiFunction;

/**
 * Helpful utility class for randomizing edges between vertices of a graph
 *
 * @param <T> the type of the value each vertex holds
 */
@Builder
@Getter
@Setter
public class ModifiableGraphRandomizer<T> {

    private static final Random RANDOM = new Random();

    /**
     * The vertices the randomized graph will contain
     */
    @Builder.Default
    private List<T> vertices = Collections.emptyList();

    /**
     * The probability of an edge being created between two vertices
     */
    @Builder.Default
    private double edgeProbability = 0.5;

    /**
     * The function used to calculate the weight of a given edge
     */
    @Builder.Default
    private ToDoubleBiFunction<T, T> weightFunction = (_, _) -> 1;

    /**
     * Generates random edges between the vertices of a directed graph
     *
     * @return a new directed graph with randomized edges
     */
    public ModifiableGraph<T> randomizeDirectedEdges() {
        var graph = new FlexibleGraph<T>(true);

        for (T source : vertices) {
            for (T destination : vertices) {
                if (source != destination && RANDOM.nextDouble() < edgeProbability) {
                    graph.addEdge(
                            source,
                            destination,
                            calculateWeight(source, destination)
                    );
                }
            }
        }

        return graph;
    }

    /**
     * Generates random edges between the vertices of an undirected graph
     *
     * @return a new undirected graph with randomized edges
     */
    public ModifiableGraph<T> randomizeUndirectedEdges() {
        var graph = new FlexibleGraph<T>(false);

        for (int i = 0; i < vertices.size(); i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                if (RANDOM.nextDouble() < edgeProbability) {
                    T source = vertices.get(i);
                    T destination = vertices.get(j);

                    graph.addEdge(
                            source,
                            destination,
                            calculateWeight(source, destination)
                    );
                }
            }
        }

        return graph;
    }

    private double calculateWeight(T source, T destination) {
        return weightFunction.applyAsDouble(source, destination);
    }

}

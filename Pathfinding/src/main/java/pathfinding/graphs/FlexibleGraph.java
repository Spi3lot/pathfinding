package pathfinding.graphs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.function.ToDoubleBiFunction;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class FlexibleGraph<T> implements ModifiableGraph<T> {

    private final boolean directed;
    
    private final Map<T, Map<T, Double>> adjacencies = new HashMap<>();

    @ToString.Exclude
    private ToDoubleBiFunction<T, T> defaultWeightFunction = (_, _) -> 1;

    /**
     * Undirected graph constructor.
     */
    public FlexibleGraph() {
        this(false);
    }

    @Override
    public void addEdge(T source, T destination) {
        addEdge(
                source,
                destination,
                defaultWeightFunction.applyAsDouble(source, destination)
        );
    }

    @Override
    public void addEdge(T source, T destination, double weight) {
        addVertex(source);
        addVertex(destination);
        adjacencies.get(source).put(destination, weight);

        if (!directed) {
            adjacencies.get(destination).put(source, weight);
        }
    }

    @Override
    public void addVertex(T vertex) {
        adjacencies.computeIfAbsent(vertex, _ -> new HashMap<>());
    }

    @Override
    public void removeEdge(T source, T destination) {
        adjacencies.get(source).remove(destination);

        if (!directed) {
            adjacencies.get(destination).remove(source);
        }
    }

    @Override
    public void removeVertex(T vertex) {
        adjacencies.remove(vertex);
    }

    @Override
    public int getEdgeCount() {
        int count = adjacencies.values()
                .stream()
                .mapToInt(Map::size)
                .sum();

        return (directed) ? count : count / 2;
    }

    @Override
    public int getVertexCount() {
        return adjacencies.size();
    }

    @Override
    public boolean hasVertex(T vertex) {
        return adjacencies.containsKey(vertex);
    }

    @Override
    public double calculateAverageDegree() {
        return adjacencies.values()
                .stream()
                .mapToInt(Map::size)
                .average()
                .orElse(0);
    }

    @Override
    public Set<T> getVertices() {
        return adjacencies.keySet();
    }

    @Override
    public Map<T, Double> getNeighbors(T vertex) {
        return adjacencies.getOrDefault(vertex, Collections.emptyMap());
    }

}

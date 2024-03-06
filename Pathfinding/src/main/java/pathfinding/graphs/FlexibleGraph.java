package pathfinding.graphs;

import lombok.Getter;
import lombok.ToString;

import java.util.*;

@Getter
@ToString
public class FlexibleGraph<T> implements ModifiableGraph<T> {

    private final boolean directed;
    private final Map<T, Map<T, Double>> adjacencies = new HashMap<>();

    /**
     * Undirected graph constructor.
     */
    public FlexibleGraph() {
        this(false);
    }

    /**
     * Graph constructor.
     *
     * @param directed whether the graph is directed or not
     */
    public FlexibleGraph(boolean directed) {
        this.directed = directed;
    }

    @Override
    public void addEdge(T source, T destination) {
        addEdge(source, destination, 1);
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
    public void addVertices(Collection<T> vertices) {
        for (T vertex : vertices) {
            addVertex(vertex);
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
    public int getDegree(T vertex) {
        return getNeighbors(vertex).size();
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

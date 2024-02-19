package pathfinding.graphs;

import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ToString
public class FlexibleGraph<T> implements Graph<T> {

    @Getter
    private final boolean directed;

    @Getter
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

    /**
     * Adds an unweighted edge between two vertices.
     *
     * @param source      the source vertex of the edge
     * @param destination the destination vertex of the edge
     */
    public void addEdge(T source, T destination) {
        addEdge(source, destination, 1);
    }

    /**
     * Adds a weighted edge between two vertices with a weight.
     *
     * @param source      the source vertex of the edge
     * @param destination the destination vertex of the edge
     * @param weight      the weight of the edge
     */
    public void addEdge(T source, T destination, double weight) {
        addVertex(source);
        addVertex(destination);
        adjacencies.get(source).put(destination, weight);

        if (!directed) {
            adjacencies.get(destination).put(source, weight);
        }
    }

    /**
     * Adds a Collection of vertices to the graph.
     */
    public void addVertices(Collection<T> vertices) {
        for (T vertex : vertices) {
            addVertex(vertex);
        }
    }

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex the value of the vertex to be added
     */
    public void addVertex(T vertex) {
        adjacencies.computeIfAbsent(vertex, _ -> new HashMap<>());
    }

    /**
     * Removes an edge between two vertices.
     *
     * @param source      the source vertex of the edge to be removed
     * @param destination the destination vertex of the edge to be removed
     */
    public void removeEdge(T source, T destination) {
        adjacencies.get(source).remove(destination);

        if (!directed) {
            adjacencies.get(destination).remove(source);
        }
    }

    /**
     * Removes a vertex from the graph.
     *
     * @param vertex the value of the vertex to be removed
     */
    public void removeVertex(T vertex) {
        adjacencies.remove(vertex);
    }

    /**
     * @return the number of edges in the graph
     */
    public int getEdgeCount() {
        int count = adjacencies
                .values()
                .stream()
                .mapToInt(Map::size)
                .sum();

        return (directed) ? count : count / 2;
    }

    /**
     * @return the number of vertices in the graph
     */
    public int getVertexCount() {
        return adjacencies.size();
    }

    /**
     * @param source      the source vertex of the edge to be checked for
     * @param destination the destination vertex of the edge to be checked for
     * @return the weight of the edge between the two vertices
     */
    public boolean hasEdge(T source, T destination) {
        return adjacencies.get(source).containsKey(destination);
    }

    /**
     * @param vertex the vertex to be checked for
     * @return the weight of the edge between the two vertices
     */
    public boolean hasVertex(T vertex) {
        return adjacencies.containsKey(vertex);
    }

    /**
     * @return a Set of all the vertices in the graph
     */
    public Set<T> getVertices() {
        return adjacencies.keySet();
    }

    @Override
    public Map<T, Double> getNeighbors(T vertex) {
        return adjacencies.get(vertex);
    }

}

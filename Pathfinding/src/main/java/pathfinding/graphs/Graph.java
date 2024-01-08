package pathfinding.graphs;

import lombok.Getter;
import lombok.ToString;

import java.util.*;

@ToString
public class Graph<T> {

    @Getter
    private final boolean directed;

    @Getter
    private final Map<T, Map<T, Double>> vertices = new HashMap<>();

    /**
     * Undirected graph constructor.
     */
    public Graph() {
        this(false);
    }

    /**
     * Graph constructor.
     *
     * @param directed whether the graph is directed or not
     */
    public Graph(boolean directed) {
        this.directed = directed;
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
        vertices.computeIfAbsent(vertex, key -> new HashMap<>());
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
        vertices.get(source).put(destination, weight);

        if (!directed) {
            vertices.get(destination).put(source, weight);
        }
    }

    /**
     * Removes a vertex from the graph.
     *
     * @param vertex the value of the vertex to be removed
     */
    public void removeVertex(T vertex) {
        vertices.remove(vertex);
    }

    /**
     * Removes an edge between two vertices.
     *
     * @param source      the source vertex of the edge to be removed
     * @param destination the destination vertex of the edge to be removed
     */
    public void removeEdge(T source, T destination) {
        vertices.get(source).remove(destination);

        if (!directed) {
            vertices.get(destination).remove(source);
        }
    }

    /**
     * @return the number of vertices in the graph
     */
    public int getVertexCount() {
        return vertices.size();
    }

    /**
     * @return the number of edges in the graph
     */
    public int getEdgeCount() {
        int count = vertices
                .values()
                .stream()
                .mapToInt(Map::size)
                .sum();

        return (directed) ? count : count / 2;
    }

    /**
     * @param vertex the vertex to be checked for
     * @return the weight of the edge between the two vertices
     */
    public boolean hasVertex(T vertex) {
        return vertices.containsKey(vertex);
    }

    /**
     * @param source      the source vertex of the edge to be checked for
     * @param destination the destination vertex of the edge to be checked for
     * @return the weight of the edge between the two vertices
     */
    public boolean hasEdge(T source, T destination) {
        return vertices.get(source).containsKey(destination);
    }

    /**
     * @param path the path to calculate the total weight of
     * @return the accumulated weight of the path
     */
    public double calculateAccumulatedPathWeight(List<T> path) {
        double weight = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            weight += getEdgeWeight(path.get(i), path.get(i + 1));
        }

        return weight;
    }

    /**
     * @param source      the source vertex of the edge
     * @param destination the destination vertex of the edge
     * @return the weight of the edge between the two vertices
     */
    public double getEdgeWeight(T source, T destination) {
        return vertices.get(source).getOrDefault(destination, Double.POSITIVE_INFINITY);
    }

    /**
     * @param vertex the vertex to get the neighbors of
     * @return a Map of the neighbors of the vertex and the weights of the edges between them
     */
    public Map<T, Double> getNeighbors(T vertex) {
        return vertices.get(vertex);
    }

}

package pathfinding.graphs;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * A graph that can be modified and a lot
 * of information can be retrieved from it.
 */
public interface ModifiableGraph<T> extends Graph<T> {

    /**
     * Adds an unweighted edge between two vertices.
     *
     * @param source      the source vertex of the edge
     * @param destination the destination vertex of the edge
     */
    void addEdge(T source, T destination);

    /**
     * Adds a weighted edge between two vertices with a weight.
     *
     * @param source      the source vertex of the edge
     * @param destination the destination vertex of the edge
     * @param weight      the weight of the edge
     */
    void addEdge(T source, T destination, double weight);

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex the value of the vertex to be added
     */
    void addVertex(T vertex);

    /**
     * Removes an edge between two vertices.
     *
     * @param source      the source vertex of the edge to be removed
     * @param destination the destination vertex of the edge to be removed
     */
    void removeEdge(T source, T destination);

    /**
     * Removes a vertex from the graph.
     *
     * @param vertex the value of the vertex to be removed
     */
    void removeVertex(T vertex);

    /**
     * @return the number of edges in the graph
     */
    int getEdgeCount();

    /**
     * @return the number of vertices in the graph
     */
    int getVertexCount();

    /**
     * @param vertex the vertex to be checked for
     * @return the weight of the edge between the two vertices
     */
    boolean hasVertex(T vertex);

    /**
     * @return the average degree of the vertices in the graph
     */
    double calculateAverageDegree();

    /**
     * @return whether the graph is directed or not
     */
    boolean isDirected();

    /**
     * @return a map of all the vertices and their adjacencies
     */
    Map<T, Map<T, Double>> getAdjacencies();

    /**
     * @return a Set of all the vertices in the graph
     */
    Set<T> getVertices();

    /**
     * Adds a Collection of vertices to the graph.
     */
    default void addVertices(Collection<T> vertices) {
        vertices.forEach(this::addVertex);
    }

}

package pathfinding.graphs;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A complete graph is a graph in which every pair of vertices is connected by an edge.
 *
 * @param <T> the type of the vertices in the graph
 */
@RequiredArgsConstructor
public class CompleteGraph<T> implements Graph<T> {

    private final List<T> vertices;

    @Override
    public Map<T, Double> getNeighbors(T vertex) {
        return vertices.stream()
                .filter(neighbor -> !neighbor.equals(vertex))
                .collect(Collectors.toMap(neighbor -> neighbor, _ -> 1.0));
    }

}

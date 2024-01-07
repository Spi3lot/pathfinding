package pathfinding;

import pathfinding.algorithms.RecursiveDFS;
import pathfinding.graphs.Graph;
import pathfinding.service.Pathfinder;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 06.01.2024, Sa.
 */
public class RecursiveDFSExample {

    public static void main(String[] args) {
        var graph = new Graph<Character>();
        graph.addEdge('A', 'B', 1);
        graph.addEdge('B', 'D', 10);
        graph.addEdge('A', 'C', 2);
        graph.addEdge('C', 'D', 2);
        var pathfinder = new Pathfinder<>(graph, new RecursiveDFS<>());
        var path = pathfinder.findShortestPath('A', 'D');
        System.out.println(path);
    }

}

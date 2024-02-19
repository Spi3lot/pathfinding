package pathfinding;

import pathfinding.algorithms.DepthFirstSearch;
import pathfinding.graphs.SimpleGraph;
import pathfinding.service.Pathfinder;

/**
 * @author : Emilio Zottel (5AHIF)
 * @since : 09.01.2024, Di.
 **/
public class BinaryTree {

    public static void main(String[] args) {
        var graph = new SimpleGraph<Integer>();
        graph.addEdge(4, 2);
        graph.addEdge(4, 6);
        graph.addEdge(6, 5);
        graph.addEdge(6, 7);
        graph.addEdge(2, 1);
        graph.addEdge(2, 3);

        var pathfinder = new Pathfinder<>(graph, new DepthFirstSearch<>());
        System.out.println(pathfinder.findShortestPath(4, 1));
    }

}

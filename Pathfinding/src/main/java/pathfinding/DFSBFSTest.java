package pathfinding;

import pathfinding.algorithms.BFS;
import pathfinding.algorithms.DFS;
import pathfinding.graphs.Graph;
import pathfinding.service.Pathfinder;

/**
 * @author : Emilio Zottel (5AHIF)
 * @since : 09.01.2024, Di.
 **/
public class DFSBFSTest {

    public static void main(String[] args) {
        var graph = new Graph<Integer>();
        graph.addEdge(4, 2);
        graph.addEdge(4, 6);
        graph.addEdge(6, 5);
        graph.addEdge(6, 7);
        graph.addEdge(2, 1);
        graph.addEdge(2, 3);

        var pathfinder = new Pathfinder<>(graph, new BFS<>());
        System.out.println(pathfinder.findAnyPath(4, 7));
    }

}

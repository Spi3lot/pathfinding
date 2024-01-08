package pathfinding;

import pathfinding.algorithms.DFS;
import pathfinding.graphs.Graph;
import pathfinding.service.Pathfinder;

public class PathfindingExample {

    public static void main(String[] args) {
        var graph = new Graph<Character>();
        graph.addEdge('A', 'B', 1);
        graph.addEdge('B', 'D', 10);
        graph.addEdge('A', 'C', 2);
        graph.addEdge('C', 'D', 2);
        graph.addEdge('D', 'A', 2);

        var pathfinder = new Pathfinder<>(graph, new DFS<>());
        System.out.println(pathfinder.findShortestPath('A', 'D'));
    }

}

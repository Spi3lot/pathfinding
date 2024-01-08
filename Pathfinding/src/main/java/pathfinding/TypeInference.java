package pathfinding;

import pathfinding.algorithms.BFS;
import pathfinding.graphs.Graph;
import pathfinding.service.Pathfinder;

public class TypeInference {

    public static void main(String[] args) {
        var graph = new Graph<Character>();
        graph.addEdge('A', 'B');
        graph.addEdge('B', 'C');
        graph.addEdge('C', 'D');
        graph.addEdge('B', 'D');

        var pathfinder = new Pathfinder<>(graph, new BFS<>());
        System.out.println(pathfinder.findShortestPath('A', 'D'));
    }

}

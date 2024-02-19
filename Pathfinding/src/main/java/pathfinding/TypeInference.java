package pathfinding;

import pathfinding.algorithms.BreadthFirstSearch;
import pathfinding.graphs.SimpleGraph;
import pathfinding.service.Pathfinder;

public class TypeInference {

    public static void main(String[] args) {
        var graph = new SimpleGraph<Character>();
        graph.addEdge('A', 'B');
        graph.addEdge('B', 'C');
        graph.addEdge('C', 'D');
        graph.addEdge('B', 'D');

        var pathfinder = new Pathfinder<>(graph, new BreadthFirstSearch<>());
        System.out.println(pathfinder.findShortestPath('A', 'D'));
    }

}

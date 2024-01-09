package pathfinding;

import pathfinding.algorithms.BFS;
import pathfinding.algorithms.DFS;
import pathfinding.graphs.Graph;
import pathfinding.service.Pathfinder;

public class PathfindingExample {

    public static void main(String[] args) {
        iterativeDfsBreaker();
    }

    private static void pathfinding() {
        var graph = new Graph<Character>();
        graph.addEdge('A', 'B', 1);
        graph.addEdge('B', 'D', 10);
        graph.addEdge('A', 'C', 2);
        graph.addEdge('C', 'D', 2);
        graph.addEdge('D', 'A', 2);

        var pathfinder = new Pathfinder<>(graph, new DFS<>());
        System.out.println(pathfinder.findShortestPath('A', 'C'));
    }

    private static void iterativeDfsBreaker() {
        var graph = new Graph<Character>();
        graph.addEdge('A', 'B', 1);
        graph.addEdge('B', 'C', 3);
        graph.addEdge('C', 'D', 1);
        graph.addEdge('A', 'D', 1000);

        var pathfinder = new Pathfinder<>(graph, new DFS<>());
        System.out.println(pathfinder.findShortestPath('A', 'D'));
    }

    private static void any() {
        var graph = new Graph<Character>();
        graph.addEdge('A', 'B', 1);
        graph.addEdge('B', 'C', 10);

        var pathfinder = new Pathfinder<>(graph, new DFS<>());
        System.out.println(pathfinder.findAnyPath('A', 'C'));
    }

}

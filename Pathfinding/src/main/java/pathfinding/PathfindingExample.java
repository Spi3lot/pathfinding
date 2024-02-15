package pathfinding;

import pathfinding.algorithms.DepthFirstSearch;
import pathfinding.algorithms.Dijkstra;
import pathfinding.graphs.Graph;
import pathfinding.service.Pathfinder;

public class PathfindingExample {

    public static void main(String[] args) {
        dijkstraBreakerNoReinsert();
    }

    /**
     * This method demonstrates how the Dijkstra algorithm can break if a vertex is not reinserted into the queue.
     */
    private static void dijkstraBreakerNoReinsert() {
        var graph = new Graph<Character>();
        graph.addEdge('A', 'B', 1);
        graph.addEdge('B', 'C', 2);
        graph.addEdge('A', 'C', 5);
        graph.addEdge('A', 'D', 4);

        var pathfinder = new Pathfinder<>(graph, new Dijkstra<>());
        System.out.println(pathfinder.findShortestPath('A', 'C'));
    }

    private static void dijkstraBreakerNegative() {
        var graph = new Graph<Character>();
        graph.addEdge('A', 'B', 5);
        graph.addEdge('A', 'C', 2);
        graph.addEdge('B', 'C', -10);

        var pathfinder = new Pathfinder<>(graph, new Dijkstra<>());
        System.out.println(pathfinder.findShortestPath('A', 'C'));
    }

    private static void dijkstra() {
        var graph = new Graph<Character>();
        graph.addEdge('A', 'B', 2);
        graph.addEdge('A', 'C', 1);
        graph.addEdge('B', 'C', 3);
        graph.addEdge('B', 'D', 10);
        graph.addEdge('C', 'D', 4);
        graph.addEdge('D', 'E', 5);

        var pathfinder = new Pathfinder<>(graph, new Dijkstra<>());
        System.out.println(pathfinder.findShortestPath('A', 'E'));
    }

    private static void pathfinding() {
        var graph = new Graph<Character>();
        graph.addEdge('A', 'B', 1);
        graph.addEdge('B', 'D', 10);
        graph.addEdge('A', 'C', 2);
        graph.addEdge('C', 'D', 2);
        graph.addEdge('D', 'A', 2);

        var pathfinder = new Pathfinder<>(graph, new DepthFirstSearch<>());
        System.out.println(pathfinder.findShortestPath('A', 'C'));
    }

    private static void iterativeDfsBreaker() {
        var graph = new Graph<Character>();
        graph.addEdge('A', 'B', 1);
        graph.addEdge('B', 'C', 3);
        graph.addEdge('C', 'A', 1);
        graph.addEdge('C', 'D', 1);
        graph.addEdge('A', 'D', 1000);

        var pathfinder = new Pathfinder<>(graph, new DepthFirstSearch<>());
        System.out.println(pathfinder.findShortestPath('A', 'D'));
    }


    private static void formerIterativeDfsBreaker() {
        var graph = new Graph<Character>();
        graph.addEdge('A', 'B', 1);
        graph.addEdge('B', 'C', 3);
        graph.addEdge('C', 'D', 1);
        graph.addEdge('A', 'D', 1000);

        var pathfinder = new Pathfinder<>(graph, new DepthFirstSearch<>());
        System.out.println(pathfinder.findShortestPath('A', 'D'));
    }

    private static void any() {
        var graph = new Graph<Character>();
        graph.addEdge('A', 'B', 1);
        graph.addEdge('B', 'C', 10);

        var pathfinder = new Pathfinder<>(graph, new DepthFirstSearch<>());
        System.out.println(pathfinder.findAnyPath('A', 'C'));
    }

}

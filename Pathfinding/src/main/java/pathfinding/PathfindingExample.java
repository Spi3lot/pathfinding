package pathfinding;

import pathfinding.algorithms.*;
import pathfinding.graphs.FlexibleGraph;
import pathfinding.graphs.Graph;
import pathfinding.service.EndCondition;
import pathfinding.service.ModifiableGraphRandomizer;
import pathfinding.service.Pathfinder;
import processing.core.PVector;

import java.util.List;
import java.util.Objects;
import java.util.function.ToDoubleBiFunction;
import java.util.stream.IntStream;

public class PathfindingExample {

    public static void main(String[] args) {
        bidiBefsIntBreaker4();
    }

    private static void bidiBefsIntBreaker3() {
        var graph = new FlexibleGraph<Integer>();
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);

        var aStar = new AStar<Integer>((v, c) -> (v.equals(c.vertex().orElseThrow())) ? 0 : 1);
        var bidiAStar = new BidiBestFirstSearch<>(aStar::h);
        System.out.println(aStar.findShortestPath(0, EndCondition.endAt(1), graph));
        System.out.println(bidiAStar.findAnyPath(0, EndCondition.endAt(1), graph));
    }

    private static void bidiBefsIntBreaker4() {
        var graph = new FlexibleGraph<Integer>();
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 1);

        var aStar = new AStar<Integer>((v, c) -> (v.equals(c.vertex().orElseThrow())) ? 0 : 1);
        var bidiAStar = new BidiBestFirstSearch<>(aStar::h);
        System.out.println(aStar.findShortestPath(0, EndCondition.endAt(3), graph));
        System.out.println(bidiAStar.findAnyPath(0, EndCondition.endAt(3), graph));
    }

    private static void bidiBefsIntBreaker() {
        var vertices = IntStream.range(0, 4)  // Takes at least 3 vertices
                .boxed()
                .toList();

        var randomizer = ModifiableGraphRandomizer.<Integer>builder()
                .vertices(vertices)
                .edgeProbability(0.1)
                .build();

        List<Integer> unidiPath = null;
        List<Integer> bidiPath = null;
        Graph<Integer> graph = null;
        int i;

        for (i = 0; unidiPath == null || bidiPath == null || graph.sumEdgeWeights(unidiPath) >= graph.sumEdgeWeights(bidiPath); i++) {
            graph = randomizer.randomizeUndirectedEdges();
            var aStar = new AStar<Integer>((v, c) -> v.equals(c.vertex().orElseThrow()) ? 0 : 1);
            var bidiAStar = new BidiBestFirstSearch<>(aStar::h);
            unidiPath = aStar.findShortestPath(vertices.getFirst(), EndCondition.endAt(vertices.getLast()), graph);
            bidiPath = bidiAStar.findAnyPath(vertices.getFirst(), EndCondition.endAt(vertices.getLast()), graph);
        }

        System.out.println(STR."It took \{i} iterations to break BidiBestFirstSearch");
        System.out.println(STR."Unidirectional (\{unidiPath.size()}, \{graph.sumEdgeWeights(unidiPath)}): \{unidiPath}");
        System.out.println(STR."Bidirectional: (\{bidiPath.size()}, \{graph.sumEdgeWeights(bidiPath)}): \{bidiPath}");
        System.out.println(graph);
    }

    private static void bidiBefsPVectorBreaker() {
        ToDoubleBiFunction<PVector, PVector> weightFunction = (source, destination) -> source.dist(destination);

        var vertices = IntStream.range(0, 100)
                .mapToObj(_ -> PVector.random2D())
                .toList();

        var randomizer = ModifiableGraphRandomizer.<PVector>builder()
                .vertices(vertices)
                .weightFunction(weightFunction)
                .edgeProbability(0.1)
                .build();

        List<PVector> unidiPath = null;
        List<PVector> bidiPath = null;
        Graph<PVector> graph = null;
        int i;

        for (i = 0; Objects.equals(unidiPath, bidiPath); i++) {
            graph = randomizer.randomizeUndirectedEdges();
            var aStar = new AStar<PVector>((current, endCondition) -> weightFunction.applyAsDouble(current, endCondition.vertex().orElseThrow()));
            var bidiAStar = new BidiBestFirstSearch<>(aStar::h);
            unidiPath = aStar.findShortestPath(vertices.getFirst(), EndCondition.endAt(vertices.getLast()), graph);
            bidiPath = bidiAStar.findAnyPath(vertices.getFirst(), EndCondition.endAt(vertices.getLast()), graph);
        }

        System.out.println(STR."It took \{i} iterations to break the BidiBestFirstSearch algorithm.");
        System.out.println(STR."Unidirectional (\{unidiPath.size()}, \{graph.sumEdgeWeights(unidiPath)}): \{unidiPath}");
        System.out.println(STR."Bidirectional: (\{bidiPath.size()}, \{graph.sumEdgeWeights(bidiPath)}): \{bidiPath}");
    }

    private static void aStarBreaker() {
        var graph = new FlexibleGraph<Character>();
        graph.addEdge('A', 'B', 1);
        graph.addEdge('A', 'C', 2);
        graph.addEdge('B', 'D', 3);
        graph.addEdge('C', 'D', 1);

        var pathfinder = new Pathfinder<>(graph, new AStar<>((_, _) -> -1.0));
        System.out.println(pathfinder.findShortestPath('A', EndCondition.endAt('D')));
    }

    private static void aStar() {
        var graph = new FlexibleGraph<PVector>();
        var a = new PVector(0, 0);
        var b = new PVector(1, 0);
        var c = new PVector(2, 0);
        graph.addEdge(a, b, 5);
        graph.addEdge(a, c, 2);
        graph.addEdge(c, b, 2);

        var pathfinder = new Pathfinder<>(
                graph,
                new AStar<>((current, endCondition) -> current.dist(endCondition.vertex().orElseThrow()))
        );

        System.out.println(pathfinder.findShortestPath(a, EndCondition.endAt(b)));
    }

    /**
     * This method demonstrates how the Dijkstra algorithm can break if a vertex is not reinserted into the queue.
     */
    private static void dijkstraBreakerNoReinsert() {
        var graph = new FlexibleGraph<Character>();
        graph.addEdge('A', 'B', 1);
        graph.addEdge('B', 'C', 2);
        graph.addEdge('A', 'C', 5);
        graph.addEdge('A', 'D', 4);

        var pathfinder = new Pathfinder<>(graph, new Dijkstra<>());
        System.out.println(pathfinder.findShortestPath('A', EndCondition.endAt('C')));
    }

    private static void dijkstraBreakerNegative() {
        var graph = new FlexibleGraph<Character>();
        graph.addEdge('A', 'B', 5);
        graph.addEdge('A', 'C', 2);
        graph.addEdge('B', 'C', -10);

        var pathfinder = new Pathfinder<>(graph, new Dijkstra<>());
        System.out.println(pathfinder.findShortestPath('A', EndCondition.endAt('C')));
    }

    private static void dijkstra() {
        var graph = new FlexibleGraph<Character>();
        graph.addEdge('A', 'B', 2);
        graph.addEdge('A', 'C', 1);
        graph.addEdge('B', 'C', 3);
        graph.addEdge('B', 'D', 10);
        graph.addEdge('C', 'D', 4);
        graph.addEdge('D', 'E', 5);

        var pathfinder = new Pathfinder<>(graph, new Dijkstra<>());
        System.out.println(pathfinder.findShortestPath('A', EndCondition.endAt('E')));
    }

    private static void pathfinding() {
        var graph = new FlexibleGraph<Character>();
        graph.addEdge('A', 'B', 1);
        graph.addEdge('B', 'D', 10);
        graph.addEdge('A', 'C', 2);
        graph.addEdge('C', 'D', 2);
        graph.addEdge('D', 'A', 2);

        var pathfinder = new Pathfinder<>(graph, new DepthFirstSearch<>());
        System.out.println(pathfinder.findShortestPath('A', EndCondition.endAt('C')));
    }

    private static void iterativeDfsBreaker() {
        var graph = new FlexibleGraph<Character>();
        graph.addEdge('A', 'B', 1);
        graph.addEdge('B', 'C', 3);
        graph.addEdge('C', 'A', 1);
        graph.addEdge('C', 'D', 1);
        graph.addEdge('A', 'D', 1000);

        var pathfinder = new Pathfinder<>(graph, new DepthFirstSearch<>());
        System.out.println(pathfinder.findShortestPath('A', EndCondition.endAt('D')));
    }


    private static void formerIterativeDfsBreaker() {
        var graph = new FlexibleGraph<Character>();
        graph.addEdge('A', 'B', 1);
        graph.addEdge('B', 'C', 3);
        graph.addEdge('C', 'D', 1);
        graph.addEdge('A', 'D', 1000);

        var pathfinder = new Pathfinder<>(graph, new DepthFirstSearch<>());
        System.out.println(pathfinder.findShortestPath('A', EndCondition.endAt('D')));
    }

    private static void any() {
        var graph = new FlexibleGraph<Character>();
        graph.addEdge('A', 'B', 1);
        graph.addEdge('B', 'C', 10);

        var pathfinder = new Pathfinder<>(graph, new DepthFirstSearch<>());
        System.out.println(pathfinder.findAnyPath('A', EndCondition.endAt('C')));
    }

}

package pathfinding;

import pathfinding.algorithms.AStar;
import pathfinding.algorithms.AStar2;
import pathfinding.games.FifteenPuzzle;
import pathfinding.graphs.FifteenPuzzleGraph;
import pathfinding.service.Benchmark;
import pathfinding.service.EndCondition;
import pathfinding.service.Pathfinder;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 19.02.2024, Mo.
 **/
public class FifteenPuzzleSolver {

    private static final int BOARD_SIZE = 4;

    public static void main(String[] args) {
        var graph = new FifteenPuzzleGraph();
        var puzzle = new FifteenPuzzle(BOARD_SIZE);
        var solvedPuzzle = FifteenPuzzle.solved(BOARD_SIZE);

        var pathfinder = new Pathfinder<>(graph, new AStar<>(
                        (current, endCondition) -> (double) current.getLeastMoveCountTo(
                                endCondition.endVertex().orElseThrow()
                        )));

        var pathfinder2 = new Pathfinder<>(graph, new AStar2<>(
                        (current, endCondition) -> (double) current.getLeastMoveCountTo(
                                endCondition.endVertex().orElseThrow()
                        )));

        var benchmark = new Benchmark();
        var path = pathfinder.findShortestPath(puzzle, EndCondition.endAt(solvedPuzzle));
        System.out.println(benchmark);
        System.out.println(graph.sumEdgeWeights(path.orElseThrow()));
        benchmark.resetTimer();
        var path2 = pathfinder2.findShortestPath(puzzle, EndCondition.endAt(solvedPuzzle));
        System.out.println(benchmark);
        System.out.println(graph.sumEdgeWeights(path2.orElseThrow()));


//        for (int i = 0; i < 1; i++) {
//            benchmark.resetTimer();
//            var path = pathfinder.findShortestPath(puzzle, EndCondition.endAt(solvedPuzzle));
//            System.out.println(benchmark);
//            //System.out.println(graph.sumEdgeWeights(path.orElseThrow()));
//            //System.out.println(path.orElseThrow());
//        }

    }

}

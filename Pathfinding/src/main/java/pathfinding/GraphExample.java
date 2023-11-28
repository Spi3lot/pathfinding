package pathfinding;

import pathfinding.service.GraphRandomizer;

import java.util.List;

public class GraphExample {

    public static void main(String[] args) {
        var graphRandomizer = new GraphRandomizer<>(
                List.of('A', 'B', 'C', 'D')
        );

        var graph = graphRandomizer.randomizeEdges(false, 0.5);
        System.out.println(graph);
    }

}

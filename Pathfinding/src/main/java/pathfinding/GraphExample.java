package pathfinding;

import pathfinding.service.GraphRandomizer;

import java.util.List;

public class GraphExample {

    public static void main(String[] args) {
        var graphRandomizer = GraphRandomizer.<Character>builder()
                .maxRandomWeight(10)
                .vertices(List.of('A', 'B', 'C', 'D', 'E'))
                .build();

        var graph = graphRandomizer.randomizeDirectedEdges();
        System.out.println(graph);
    }

}

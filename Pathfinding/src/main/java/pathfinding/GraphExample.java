package pathfinding;

import pathfinding.service.SimpleGraphRandomizer;

import java.util.List;

public class GraphExample {

    public static void main(String[] args) {
        var graphRandomizer = SimpleGraphRandomizer.<Character>builder()
                .maxRandomWeight(10)
                .vertices(List.of('A', 'B', 'C', 'D', 'E'))
                .build();

        var graph = graphRandomizer.randomizeDirectedEdges();
        System.out.println(graph);
    }

}

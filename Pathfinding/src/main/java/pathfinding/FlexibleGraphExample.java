package pathfinding;

import pathfinding.service.FlexibleGraphRandomizer;

import java.util.List;

public class FlexibleGraphExample {

    public static void main(String[] args) {
        var graphRandomizer = FlexibleGraphRandomizer.<Character>builder()
                .maxRandomWeight(10)
                .vertices(List.of('A', 'B', 'C', 'D', 'E'))
                .build();

        var graph = graphRandomizer.randomizeDirectedEdges();
        System.out.println(graph);
    }

}

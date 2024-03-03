package pathfinding;

import pathfinding.service.FlexibleGraphRandomizer;

import java.util.List;

public class FlexibleGraphExample {

    public static void main(String[] args) {
        var graphRandomizer = FlexibleGraphRandomizer.<Character>builder()
                .vertices(List.of('A', 'B', 'C', 'D', 'E'))
                .weightFunction((source, destination) -> Math.abs(source - destination))
                .build();

        var graph = graphRandomizer.randomizeDirectedEdges();
        System.out.println(graph);
    }

}

package pathfinding;

import pathfinding.service.FlexibleGraphRandomizer;

import java.util.List;
import java.util.Random;

public class FlexibleGraphExample {

    public static void main(String[] args) {
        var random = new Random();

        var graphRandomizer = FlexibleGraphRandomizer.<Character>builder()
                .vertices(List.of('A', 'B', 'C', 'D', 'E'))
                .weightFunction((_, _) -> 1 + random.nextInt(10))
                .build();

        var graph = graphRandomizer.randomizeDirectedEdges();
        System.out.println(graph);
    }

}

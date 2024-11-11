package pathfinding;

import pathfinding.service.ModifiableGraphRandomizer;

import java.util.List;
import java.util.Random;

public class ModifiableGraphRandomizerExample {

    public static void main(String[] args) {
        var random = new Random();

        var graphRandomizer = ModifiableGraphRandomizer.<Character>builder()
                .vertices(List.of('A', 'B', 'C', 'D', 'E'))
                .weightFunction((_, _) -> 1 + random.nextInt(10))
                .build();

        var graph = graphRandomizer.randomizeDirectedEdges();
        System.out.println(graph);
    }

}

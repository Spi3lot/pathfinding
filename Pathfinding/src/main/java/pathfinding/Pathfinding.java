package pathfinding;

import pathfinding.algorithms.AStar;
import pathfinding.algorithms.BestFirstSearch;
import pathfinding.algorithms.BidiBestFirstSearch;
import pathfinding.functions.Heuristic;
import pathfinding.graphs.ModifiableGraph;
import pathfinding.service.EndCondition;
import pathfinding.service.ModifiableGraphRandomizer;
import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;

import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 13.09.2023, Mi.
 */
public class Pathfinding extends PApplet {

    private static final int VERTEX_COUNT = 20;
    private static final Heuristic<PVector> HEURISTIC = (v, c) -> v.dist(c.vertex().orElseThrow());
    private final BestFirstSearch<PVector> aStar = new AStar<>(HEURISTIC);
    private final BidiBestFirstSearch<PVector> bidiBefs = new BidiBestFirstSearch<>(HEURISTIC);
    private final ModifiableGraphRandomizer<PVector> randomizer = ModifiableGraphRandomizer.<PVector>builder()
            .edgeProbability(0.1)
            .build();
    private ModifiableGraph<PVector> graph;
    private List<PVector> vertices;

    public static void main(String[] args) {
        PApplet.main(Pathfinding.class);
    }

    @Override
    public void settings() {
        size(800, 800);
    }

    @Override
    public void setup() {
        randomize();
        stroke(255);
        noLoop();
    }

    @Override
    public void draw() {
        background(0);
        var path1 = aStar.findShortestPath(vertices.getFirst(), EndCondition.endAt(vertices.getLast()), graph);
        var path2 = bidiBefs.findShortestPath(vertices.getFirst(), EndCondition.endAt(vertices.getLast()), graph);

        for (var vertexEntry : graph.getAdjacencies().entrySet()) {
            var vertex = vertexEntry.getKey();
            var neighbors = vertexEntry.getValue();
            fill(0, 255, 255);
            stroke(255);

            for (var neighborEntry : neighbors.entrySet()) {
                var neighbor = neighborEntry.getKey();
                var center = PVector.lerp(vertex, neighbor, 0.5f);
                line(vertex.x, vertex.y, neighbor.x, neighbor.y);
                text(STR."\{neighborEntry.getValue()}", center.x, center.y);
            }

//            int color = (bidiBefs.backwardSearch().hasVisited(vertex)) ? color(255, 0, 0) : color(255);
//            color = lerpColor(color, color(0, 255, 0), bidiBefs.forwardSearch().hasVisited(vertex) ? 0.5f : 0);
//            color = lerpColor(color, color(0, 0, 255), aStar.hasVisited(vertex) ? 0.5f : 0);
            int color = (path1.contains(vertex)) ? color(255, 0, 0) : color(255);
            color = lerpColor(color, color(0, 255, 0), (path2.contains(vertex)) ? 0.5f : 0);
            fill(color);
            stroke(0);
            circle(vertex.x, vertex.y, 50);
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (Character.toLowerCase(event.getKey()) == 'r') {
            randomize();
            redraw();
        }
    }

    private void randomize() {
        vertices = IntStream.range(0, VERTEX_COUNT)
                .mapToObj(_ -> new PVector(random(width), random(height)))
                .toList();

        randomizer.setVertices(vertices);
        graph = randomizer.randomizeUndirectedEdges();
    }

}

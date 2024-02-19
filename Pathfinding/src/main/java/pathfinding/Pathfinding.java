package pathfinding;

import pathfinding.graphs.FlexibleGraph;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 13.09.2023, Mi.
 */
public class Pathfinding extends PApplet {

    private static final int VERTEX_COUNT = 10;
    private FlexibleGraph<PVector> graph;

    public static void main(String[] args) {
        PApplet.main(Pathfinding.class);
    }

    @Override
    public void settings() {
        size(800, 800);
    }

    @Override
    public void setup() {
        graph = new FlexibleGraph<>();

        for (int i = 0; i < VERTEX_COUNT; i++) {
            graph.addVertex(new PVector(random(width), random(height)));
        }

        for (var vertex : graph.getVertices()) {
            for (var neighbor : graph.getVertices()) {
                if (vertex != neighbor && random(1) < 0.1) {
                    graph.addEdge(vertex, neighbor);
                }
            }
        }

        stroke(255);
        noLoop();
    }

    @Override
    public void draw() {
        background(0);

        for (var entry : graph.getAdjacencies().entrySet()) {
            var vertex = entry.getKey();
            var edges = entry.getValue();

            for (var neighbour : edges.keySet()) {
                circle(vertex.x, vertex.y, 50);
                line(vertex.x, vertex.y, neighbour.x, neighbour.y);
            }
        }
    }

}

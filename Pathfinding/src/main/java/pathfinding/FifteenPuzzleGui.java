package pathfinding;

import pathfinding.algorithms.BidiBestFirstSearch;
import pathfinding.functions.FifteenPuzzleHeuristic;
import pathfinding.games.Direction;
import pathfinding.games.FifteenPuzzle;
import pathfinding.games.FifteenPuzzleBoard;
import pathfinding.games.Position;
import pathfinding.graphs.FifteenPuzzleGraph;
import pathfinding.service.EndCondition;
import pathfinding.service.Pathfinder;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.util.FormatProcessor.FMT;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 17.02.2024, Sa.
 */
public class FifteenPuzzleGui extends PApplet {

    private static final int BOARD_SIZE = 4;
    private static final BidiBestFirstSearch<FifteenPuzzle> SEARCH = BidiBestFirstSearch.usingAStar(new FifteenPuzzleHeuristic());
    private static final Pathfinder<FifteenPuzzle> FINDER = new Pathfinder<>(new FifteenPuzzleGraph(), SEARCH);
    private final FifteenPuzzle puzzle = new FifteenPuzzle(BOARD_SIZE, 0);
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private Future<Integer> minMoveCountFuture;
    private float tileSize;
    private float numberTextSize;
    private float solvedTextSize;
    private int moveCount;
    private long startNanos;

    public static void main(String[] args) {
        PApplet.main(FifteenPuzzleGui.class);
    }

    @Override
    public void settings() {
        size(400, 400);
    }

    @Override
    public void setup() {
        int highestDigitCount = countDigits(puzzle.board().calculateArea() - 1);
        tileSize = (float) width / BOARD_SIZE;
        numberTextSize = tileSize * 2 / (highestDigitCount + 1);
        solvedTextSize = width / 10f;
        windowTitle("15 Puzzle");
        textAlign(CENTER, CENTER);
        textFont(createFont("Comic Sans MS", 32));
        noLoop();
        resetBoard();
    }

    @Override
    public void draw() {
        if (puzzle.board().isSolved()) {
            background(0xFF00FF00);
            fill(0);
            textSize(solvedTextSize);
            text(formatSolveText(), width * 0.5f, height * 0.5f);
            return;
        }

        for (int j = 0; j < BOARD_SIZE; j++) {
            for (int i = 0; i < BOARD_SIZE; i++) {
                int value = puzzle.board().get(j, i);
                fill(255);

                if (value == puzzle.board().getEmptyValue()) {
                    square(i * tileSize, j * tileSize, tileSize);
                } else {
                    var position = new Position(i, j);
                    int actualValue = puzzle.board().get(position);
                    int expectedValue = position.calculateExpectedValue(BOARD_SIZE);
                    square(i * tileSize, j * tileSize, tileSize);
                    fill((actualValue == expectedValue) ? 0xFF005500 : 0xFF0000FF);
                    textSize(numberTextSize);
                    text(value, tileSize * (i + 0.5f), tileSize * (j + 0.5f));
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        handleMouse();
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        handleMouse();
    }

    private void handleMouse() {
        int i = (int) (mouseX / tileSize);
        int j = (int) (mouseY / tileSize);
        var position = new Position(i, j);

        if (position.isOnBoard(BOARD_SIZE) && !puzzle.board().isSolved() && puzzle.board().move(puzzle.board().get(position))) {
            increaseMoveCount();
            redraw();
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (!puzzle.board().isSolved()) {
            Direction direction;

            if (event.getKey() == CODED) {
                direction = Direction.fromKeyCode(event.getKeyCode());
            } else {
                direction = Direction.fromKey(event.getKey());
            }

            if (direction != null) {
                if (puzzle.board().move(direction)) {
                    increaseMoveCount();
                }

                redraw();
                return;
            }
        }

        if ('R' == Character.toUpperCase(event.getKey())) {
            resetBoard();
            redraw();
        }
    }

    private void resetBoard() {
        puzzle.board().shuffle();
        moveCount = 0;

        if (minMoveCountFuture != null) {
            minMoveCountFuture.cancel(true);
        }

        minMoveCountFuture = executor.submit(() -> {
            var start = new FifteenPuzzle(new FifteenPuzzleBoard(puzzle.board()));
            var path = FINDER.findShortestPath(start, EndCondition.endAt(FifteenPuzzle.solved(BOARD_SIZE)));
            return (int) FINDER.getGraph().sumEdgeWeights(path);
        });
    }

    private void increaseMoveCount() {
        moveCount++;

        if (moveCount == 1) {
            startNanos = System.nanoTime();
        }
    }

    private String formatSolveText() {
        int minMoveCount = getMinMoveCount();
        double seconds = (System.nanoTime() - startNanos) / 1e9;
        double moveRatio = (double) moveCount / minMoveCount;
        double relativePercent = 100 * (moveRatio - 1);
        return FMT."SOLVED%n%.2f\{seconds} seconds%n%d\{moveCount}/%d\{minMoveCount} moves%n(+%.2f\{relativePercent}%%)";
    }

    private int getMinMoveCount() {
        try {
            return minMoveCountFuture.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private int countDigits(int number) {
        return (int) Math.log10(number) + 1;
    }

}

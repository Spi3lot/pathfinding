package pathfinding.functions;

import pathfinding.service.EndCondition;

import java.util.function.ToDoubleBiFunction;

/**
 * Represents a heuristic function that estimates the cost of the shortest path
 * from a given vertex to the vertex that satisfies the given end condition.
 *
 * @param <T> the type of the vertices in the graph
 */
@FunctionalInterface
public interface Heuristic<T> extends ToDoubleBiFunction<T, EndCondition<T>> {

}

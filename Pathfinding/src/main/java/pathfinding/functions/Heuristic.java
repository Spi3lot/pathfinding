package pathfinding.functions;

import pathfinding.service.EndCondition;

import java.util.function.BiFunction;

/**
 * Represents a heuristic function that estimates the cost of the shortest path
 * from a given vertex to the vertex that satisfies the given end condition.
 */
@FunctionalInterface
public interface Heuristic<T> extends BiFunction<T, EndCondition<T>, Double> {

}

package pathfinding.service;

import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Utility class to specify the end condition of a search algorithm.
 * It can be either a specific vertex or a condition that the vertex must satisfy.
 *
 * @param <T> the type of the vertices in the graph
 */
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class EndCondition<T> {

    private final T endVertex;
    private final Predicate<T> condition;

    public static <T> EndCondition<T> endAt(T endVertex) {
        return new EndCondition<>(endVertex, endVertex::equals);
    }

    public static <T> EndCondition<T> endIf(Predicate<T> condition) {
        return new EndCondition<>(null, condition);
    }

    public Optional<T> endVertex() {
        return Optional.ofNullable(endVertex);
    }

    public Predicate<T> condition() {
        return condition;
    }

}

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

    private final T vertex;
    private final Predicate<T> condition;

    public static <T> EndCondition<T> endAt(T vertex) {
        return new EndCondition<>(vertex, vertex::equals);
    }

    public static <T> EndCondition<T> endIf(Predicate<T> condition) {
        return new EndCondition<>(null, condition);
    }

    public Optional<T> vertex() {
        return Optional.ofNullable(vertex);
    }

    public Predicate<T> condition() {
        return condition;
    }

}

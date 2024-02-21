package pathfinding.service;

import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 21.02.2024, Mi.
 **/
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class EndCondition<T> {

    private final T endVertex;
    private final Predicate<T> condition;

    public static <T> EndCondition<T> endAt(T endVertex) {
        return new EndCondition<>(endVertex, vertex -> vertex.equals(endVertex));
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

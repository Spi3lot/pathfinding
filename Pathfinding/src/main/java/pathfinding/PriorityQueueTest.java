package pathfinding;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 15.02.2024, Do.
 */
public class PriorityQueueTest {

    public static void main(String[] args) {
        test1();
    }

    /**
     * Demonstrating the behavior of a PriorityQueue when the value
     * of the comparator changes after elements have been added.
     * <p>
     * The queue will not be sorted again after the comparator changes.
     */
    private static void test1() {
        var map = new HashMap<>(Map.of(1, 3, 4, 5));

        var queue = new PriorityQueue<Integer>(Comparator.comparingInt(map::get));
        queue.add(1);
        queue.add(4);
        map.put(4, 2);
        System.out.println(queue);

        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }

    /**
     * Demonstrating the behavior of a PriorityQueue when there are multiple possible insertion indexes for an element.
     * <p>
     * However, this test is not very useful because the elements are sorted when they are added to the queue.
     * <p>
     * IMPORTANT NOTE: printing the queue will not show the elements in the order they would be polled in.
     */
    private static void test2() {
        var map = Map.of(1, 1, 2, 2, 3, 3, 4, 4);

        var queue = new PriorityQueue<Integer>(Comparator.comparingInt(map::get));
        queue.add(1);
        queue.add(3);
        queue.add(4);
        queue.add(1);
        queue.add(3);
        queue.add(4);
        //queue.add(2);
        System.out.println(queue);

        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }

}

package example.collections.seq;

import io.vavr.Tuple2;
import io.vavr.collection.CharSeq;
import io.vavr.collection.List;
import io.vavr.collection.Queue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * 以下のサイトのコードがオリジナルです。
 * https://www.baeldung.com/vavr-collections
 */
public class QueueTest {

    @Test
    public void enqueue_dequeue_combination() {
        Queue<Integer> queue = Queue.of(1, 2, 3);
        Queue<Integer> secondQueue = queue.enqueueAll(List.of(4, 5));

        assertEquals(3, queue.size());
        assertEquals(5, secondQueue.size());

        Tuple2<Integer, Queue<Integer>> result = secondQueue.dequeue();
        assertEquals(Integer.valueOf(1), result._1);

        Queue<Integer> tailQueue = result._2;
        assertFalse(tailQueue.contains(secondQueue.get(0)));

        Queue<Queue<Integer>> queue1 = queue.combinations(2);
        assertEquals(queue1.get(0).toCharSeq(), CharSeq.of("12"));
        assertEquals(queue1.get(1).toCharSeq(), CharSeq.of("13"));
        assertEquals(queue1.get(2).toCharSeq(), CharSeq.of("23"));
    }
}

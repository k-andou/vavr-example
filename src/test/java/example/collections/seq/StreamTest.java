package example.collections.seq;

import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Stream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 以下のサイトのコードがオリジナルです。
 * https://www.baeldung.com/vavr-collections
 */
public class StreamTest {

    @Test
    public void iterate_sum_tabulate_zip() {
        Stream<Integer> intStream = Stream.iterate(0, i -> i + 1)
                .take(10);

        assertEquals(10, intStream.size());

        long evenSum = intStream.filter(i -> i % 2 == 0)
                .sum()
                .longValue();

        assertEquals(20, evenSum);

        Stream<Integer> s1 = Stream.tabulate(5, (i) -> i + 1);
        assertEquals(s1.get(2).intValue(), 3);

        Stream<Integer> s = Stream.of(2, 1, 3, 4);
        Stream<Tuple2<Integer, Integer>> s2 = s.zip(List.of(7, 8, 9));
        Tuple2<Integer, Integer> t1 = s2.get(0);

        assertEquals(t1._1().intValue(), 2);
        assertEquals(t1._2().intValue(), 7);
    }
}

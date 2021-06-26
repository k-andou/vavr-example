package example.collections.interoperability;

import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 以下のサイトのコードがオリジナルです。
 * https://www.baeldung.com/vavr-collections
 */
public class ToVavrTest {

    @Test
    public void toVavr() {
        java.util.List<Integer> javaList = java.util.Arrays.asList(1, 2, 3, 4);
        List<Integer> vavrList = List.ofAll(javaList);

        java.util.stream.Stream<Integer> javaStream = javaList.stream();
        Set<Integer> vavrSet = HashSet.ofAll(javaStream);

        List<Integer> vavrList2 = IntStream.range(1, 10)
                .boxed()
                .filter(i -> i % 2 == 0)
                .collect(List.collector());

        assertEquals(4, vavrList2.size());
        assertEquals(2, vavrList2.head().intValue());
    }
}

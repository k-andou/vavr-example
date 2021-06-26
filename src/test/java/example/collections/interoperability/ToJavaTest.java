package example.collections.interoperability;

import io.vavr.Tuple;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 以下のサイトのコードがオリジナルです。
 * https://www.baeldung.com/vavr-collections
 */
public class ToJavaTest {

    @Test
    public void toJava() {
        Integer[] array = List.of(1, 2, 3)
                .toJavaArray(Integer[]::new);
        assertEquals(3, array.length);

        java.util.Map<String, Integer> map = List.of("1", "2", "3")
                .toJavaMap(i -> Tuple.of(i, Integer.valueOf(i)));
        assertEquals(2, map.get("2").intValue());

        java.util.Set<Integer> javaSet = List.of(1, 2, 3)
                .collect(Collectors.toSet());

        assertEquals(3, javaSet.size());
        assertEquals(1, javaSet.toArray()[0]);
    }

    @Test
    public void givenVavrList_whenViewConverted_thenException() {
        java.util.List<Integer> javaList = List.of(1, 2, 3)
                .asJava();

        assertEquals(3, javaList.get(2).intValue());
        assertThrows(UnsupportedOperationException.class, () ->
                javaList.add(4));
    }

    @Test
    public void toViews() {
        java.util.List<Integer> javaList = List.of(1, 2, 3)
                .asJavaMutable();
        javaList.add(4);

        assertEquals(4, javaList.get(3).intValue());
    }

}

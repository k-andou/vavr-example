package example.collections.map;

import io.vavr.collection.SortedMap;
import io.vavr.collection.TreeMap;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 以下のサイトのコードがオリジナルです。
 * https://www.baeldung.com/vavr-collections
 */
public class TreeMapTest {

    @Test
    public void of_reverseOrder() {
        SortedMap<Integer, String> map
                = TreeMap.of(3, "Three", 2, "Two", 4, "Four", 1, "One");

        assertEquals(1, map.keySet().toJavaArray()[0]);
        assertEquals("Four", map.get(4).get());

        TreeMap<Integer, String> treeMap2 =
                TreeMap.of(Comparator.reverseOrder(), 3, "three", 6, "six", 1, "one");
        assertEquals(treeMap2.keySet().mkString(), "631");
    }
}

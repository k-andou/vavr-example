package example.collections.map;

import io.vavr.Tuple;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 以下のサイトのコードがオリジナルです。
 * https://www.baeldung.com/vavr-collections
 */
public class HashMapTest {

    @Test
    public void range_of_filterKeys_filterValues_map() {
        Map<Integer, List<Integer>> map = List.rangeClosed(0, 10)
                .groupBy(i -> i % 2);

        assertEquals(2, map.size());
        assertEquals(6, map.get(0).get().size());
        assertEquals(5, map.get(1).get().size());

        Map<String, String> map1
                = HashMap.of("key1", "val1", "key2", "val2", "key3", "val3");

        Map<String, String> fMap
                = map1.filterKeys(k -> k.contains("1") || k.contains("2"));
        assertFalse(fMap.containsKey("key3"));

        Map<String, String> fMap2
                = map1.filterValues(v -> v.contains("3"));
        assertEquals(fMap2.size(), 1);
        assertTrue(fMap2.containsValue("val3"));

        Map<String, Integer> map2 = map1.map(
                (k, v) -> Tuple.of(k, Integer.valueOf(v.charAt(v.length() - 1) + "")));
        assertEquals(map2.get("key1").get().intValue(), 1);
    }
}

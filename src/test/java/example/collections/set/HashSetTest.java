package example.collections.set;

import io.vavr.collection.HashSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 以下のサイトのコードがオリジナルです。
 * https://www.baeldung.com/vavr-collections
 */
public class HashSetTest {

    @Test
    public void range_of() {
        HashSet<Integer> set0 = HashSet.rangeClosed(1, 5);
        HashSet<Integer> set1 = HashSet.rangeClosed(3, 6);

        assertEquals(set0.union(set1), HashSet.rangeClosed(1, 6));
        assertEquals(set0.diff(set1), HashSet.rangeClosed(1, 2));
        assertEquals(set0.intersect(set1), HashSet.rangeClosed(3, 5));

        HashSet<String> set = HashSet.of("Red", "Green", "Blue");
        HashSet<String> newSet = set.add("Yellow");

        assertEquals(3, set.size());
        assertEquals(4, newSet.size());
        assertTrue(newSet.contains("Yellow"));
    }
}

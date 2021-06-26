package example.collections.set;

import io.vavr.collection.SortedSet;
import io.vavr.collection.TreeSet;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 以下のサイトのコードがオリジナルです。
 * https://www.baeldung.com/vavr-collections
 */
public class TreeSetTest {

    @Test
    public void of_reverseOrder_mkString() {
        SortedSet<String> set = TreeSet.of("Red", "Green", "Blue");
        assertEquals("Blue", set.head());

        SortedSet<Integer> intSet = TreeSet.of(1, 2, 3);
        assertEquals(2, intSet.average().get().intValue());

        SortedSet<String> reversedSet
                = TreeSet.of(Comparator.reverseOrder(), "Green", "Red", "Blue");
        assertEquals("Red", reversedSet.head());

        String str = reversedSet.mkString(" and ");
        assertEquals("Red and Green and Blue", str);
    }
}

package example.collections.seq;

import io.vavr.collection.Array;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 以下のサイトのコードがオリジナルです。
 * https://www.baeldung.com/vavr-collections
 */
public class ArrayTest {
    @Test
    public void range_remove_replace() {
        Array<Integer> rArray = Array.range(1, 5);
        assertFalse(rArray.contains(5));

        Array<Integer> rArray2 = Array.rangeClosed(1, 5);
        assertTrue(rArray2.contains(5));

        Array<Integer> rArray3 = Array.rangeClosedBy(1, 6, 2);
        assertTrue(rArray3.contains(1));
        assertTrue(rArray3.contains(3));
        assertTrue(rArray3.contains(5));
        assertEquals(rArray3.size(), 3);

        Array<Integer> intArray = Array.of(1, 2, 3);
        Array<Integer> newArray = intArray.removeAt(1);

        assertEquals(3, intArray.size());
        assertEquals(2, newArray.size());
        assertEquals(3, newArray.get(1).intValue());

        Array<Integer> array2 = intArray.replace(1, 5);
        assertEquals(array2.get(0).intValue(), 5);
    }
}

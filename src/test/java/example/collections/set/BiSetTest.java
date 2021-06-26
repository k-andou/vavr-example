package example.collections.set;

import io.vavr.collection.BitSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 以下のサイトのコードがオリジナルです。
 * https://www.baeldung.com/vavr-collections
 */
public class BiSetTest {

    @Test
    public void of_takeUntil() {
        BitSet<Integer> bitSet = BitSet.of(1, 2, 3, 4, 5, 6, 7, 8);
        BitSet<Integer> bitSet1 = bitSet.takeUntil(i -> i > 4);
        assertEquals(bitSet1.size(), 4);
        assertTrue(bitSet1.contains(4));
        assertFalse(bitSet1.contains(5));
    }
}

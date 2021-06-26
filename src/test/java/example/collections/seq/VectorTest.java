package example.collections.seq;

import io.vavr.collection.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 以下のサイトのコードがオリジナルです。
 * https://www.baeldung.com/vavr-collections
 */
public class VectorTest {

    @Test
    public void range_replace() {
        Vector<Integer> intVector = Vector.range(1, 5);
        Vector<Integer> newVector = intVector.replace(2, 6);

        assertEquals(4, intVector.size());
        assertEquals(4, newVector.size());

        assertEquals(2, intVector.get(1).intValue());
        assertEquals(6, newVector.get(1).intValue());
    }
}

package example.collections.seq;

import io.vavr.collection.CharSeq;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 以下のサイトのコードがオリジナルです。
 * https://www.baeldung.com/vavr-collections
 */
public class CharSeqTest {

    @Test
    public void of_replace() {
        CharSeq chars = CharSeq.of("vavr");
        CharSeq newChars = chars.replace('v', 'V');

        assertEquals(4, chars.size());
        assertEquals(4, newChars.size());

        assertEquals('v', chars.charAt(0));
        assertEquals('V', newChars.charAt(0));
        assertEquals("Vavr", newChars.mkString());
    }
}

package example.intro;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 以下のサイトのコードがオリジナルです。
 * https://www.baeldung.com/vavr
 */
public class CollectionsTest {

    @Test
    public void whenCreatesVavrList_thenCorrect() {
        List<Integer> intList = List.of(1, 2, 3);

        assertEquals(3, intList.length());
        assertEquals(Integer.valueOf(1), intList.get(0));
        assertEquals(Integer.valueOf(2), intList.get(1));
        assertEquals(Integer.valueOf(3), intList.get(2));
    }

    @Test
    public void whenSumsVavrList_thenCorrect() {
        int sum = List.of(1, 2, 3).sum().intValue();

        assertEquals(6, sum);
    }
}

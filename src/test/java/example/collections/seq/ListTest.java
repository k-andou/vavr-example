package example.collections.seq;

import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 以下のサイトのコードがオリジナルです。
 * https://www.baeldung.com/vavr-collections
 */
public class ListTest {

    @Test
    public void drop_take_and_so_on() {
        List<String> list = List.of(
                "Java", "PHP", "Jquery", "JavaScript", "JShell", "JAVA");

        List<String> list1 = list.drop(2);
        assertFalse(list1.contains("Java") && list1.contains("PHP"));

        List<String> list2 = list.dropRight(2);
        assertFalse(list2.contains("JAVA") && list2.contains("JShell"));

        List<String> list3 = list.dropUntil(s -> s.contains("Shell"));
        assertEquals(list3.size(), 2);

        List<String> list4 = list.dropWhile(s -> s.length() > 0);
        assertTrue(list4.isEmpty());

        List<String> list5 = list.take(1);
        assertEquals(list5.single(), "Java");

        List<String> list6 = list.takeRight(1);
        assertEquals(list6.single(), "JAVA");

        List<String> list7 = list.takeUntil(s -> s.length() > 6);
        assertEquals(list7.size(), 3);

        List<String> list8 = list
                .distinctBy((s1, s2) -> s1.startsWith(s2.charAt(0) + "") ? 0 : 1);
        assertEquals(list8.size(), 2);

        String words = List.of("Boys", "Girls")
                .intersperse("and")
                .reduce((s1, s2) -> s1.concat(" " + s2))
                .trim();
        assertEquals(words, "Boys and Girls");

        Iterator<List<String>> iterator = list.grouped(2);
        assertEquals(iterator.head().size(), 2);

        Map<Boolean, List<String>> map = list.groupBy(e -> e.startsWith("J"));
        assertEquals(map.size(), 2);
        assertEquals(map.get(false).get().size(), 1);
        assertEquals(map.get(true).get().size(), 5);

        List<Integer> intList = List.empty();

        List<Integer> intList1 = intList.pushAll(List.rangeClosed(5, 10));

        assertEquals(intList1.peek(), Integer.valueOf(10));

        List<Integer> intList2 = intList1.pop();
        assertEquals(intList2.size(), (intList1.size() - 1));
    }
}

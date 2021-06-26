package example.intro;

import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 以下のサイトのコードがオリジナルです。
 * https://www.baeldung.com/vavr
 */
public class OptionTest {
    @Test
    public void givenNullOrValue_whenCreatesOption_thenCorrect() {
        Option<Object> noneOption = Option.of(null);
        Option<Object> someOption = Option.of("val");

        assertEquals("None", noneOption.toString());
        assertEquals("Some(val)", someOption.toString());
    }

    @Test
    public void givenNull_whenCreatesOption_thenCorrect() {
        String name = null;
        Option<String> nameOption = Option.of(name);

        assertEquals("example", nameOption.getOrElse("example"));
    }

    @Test
    public void givenValue_whenCreatesOption_thenCorrect() {
        String name = "example2";
        Option<String> nameOption = Option.of(name);

        assertEquals("example2", nameOption.getOrElse("not example2"));
    }

}

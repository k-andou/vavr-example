package example.intro;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 以下のサイトのコードがオリジナルです。
 * https://www.baeldung.com/vavr
 */
public class TryTest {
    @Test
    public void givenBadCode_whenTryHandles_thenCorrect() {
        Try<Integer> result = Try.of(() -> 1 / 0);

        assertTrue(result.isFailure());
    }

    @Test
    public void givenBadCode_whenTryHandles_thenCorrect2() {
        Try<Integer> computation = Try.of(() -> 1 / 0);
        int errorSentinel = computation.getOrElse(-1);

        assertEquals(-1, errorSentinel);
    }

    @Test
    public void givenBadCode_whenTryHandles_thenCorrect3() {
        Try<Integer> result = Try.of(() -> 1 / 0);
        assertThrows(ArithmeticException.class, () ->
                result.getOrElseThrow(() -> new ArithmeticException()));
    }
}

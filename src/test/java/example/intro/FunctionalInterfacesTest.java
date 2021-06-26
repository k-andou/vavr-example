package example.intro;

import io.vavr.Function0;
import io.vavr.Function1;
import io.vavr.Function2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 以下のサイトのコードがオリジナルです。
 * https://www.baeldung.com/vavr
 */
public class FunctionalInterfacesTest {

    @Test
    public void givenVavrFunction_whenWorks_thenCorrect() {
        Function1<Integer, Integer> square = (num) -> num * num;
        int result = square.apply(2);

        assertEquals(4, result);
    }

    @Test
    public void givenVavrBiFunction_whenWorks_thenCorrect() {
        Function2<Integer, Integer, Integer> sum =
                (num1, num2) -> num1 + num2;
        int result = sum.apply(5, 7);

        assertEquals(12, result);
    }

    @Test
    public void whenCreatesFunction_thenCorrect0() {
        Function0<String> getClazzName = () -> this.getClass().getName();
        String clazzName = getClazzName.apply();

        assertEquals("example.intro.FunctionalInterfacesTest", clazzName);
    }

    @Test
    public void whenCreatesFunctionFromMethodRef_thenCorrect() {
        Function2<Integer, Integer, Integer> sum = Function2.of(this::sum);
        int summed = sum.apply(5, 6);

        assertEquals(11, summed);
    }

    private int sum(int a, int b) {
        return a + b;
    }
}

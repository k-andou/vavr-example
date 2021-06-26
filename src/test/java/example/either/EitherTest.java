package example.either;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 以下のサイトのコードを基に一部改変しました。
 * https://dev.to/nbentoneves/vavr-s-either-tutorial-25bd
 */
public class EitherTest {

    // Pure Java
    private static Map<String, Object> divideNumberWithoutEither(int num1, int num2) {
        Map<String, Object> result = new HashMap<>();

        try {
            result.put("SUCCESS", num1 / num2);
        } catch (ArithmeticException ex) {
            result.put("FAIL", "Can't divide a number per zero!");
        } catch (Exception ex) {
            result.put("FAIL", "Problem when trying to divide the numbers! Reason: " + ex.getMessage());
        }

        return result;
    }

    // Using Vavr
    private static Either<String, Integer> divideNumberWithEither(int num1, int num2) {
        try {
            return Either.right(num1 / num2);
        } catch (ArithmeticException ex) {
            return Either.left("Can't divide a number per zero!");
        } catch (Exception ex) {
            return Either.left("Problem when try to divide the numbers! Reason: " + ex.getMessage());
        }
    }

    @Test
    void testShouldDivideNumberPerZeroWithEither() {

        Either<String, Integer> eitherResult = divideNumberWithEither(10, 0);

        //You can use isRight or isLeft to check if the value is available
        assertFalse(eitherResult.isRight());
        assertTrue(eitherResult.isLeft());

        //The left side contains the error message (using default behaviour)
        assertEquals(eitherResult.getLeft(), "Can't divide a number per zero!");

        //If you call the get() method when there is no value you will receive a NoSuchElementException exception
        assertThrows(NoSuchElementException.class, eitherResult::get);

    }

    @Test
    void testShouldDivideNumberPerZeroWithoutEither() {

        //Comparing to pure java using map
        Map<String, Object> javaResult = divideNumberWithoutEither(10, 0);
        assertNull(javaResult.get("SUCCESS"));
        assertNotNull(javaResult.get("FAIL"));
        assertEquals(javaResult.get("FAIL"), "Can't divide a number per zero!");

    }

    @Test
    void testShouldDivideNumberExceptPerZeroUsingFunctionalOperations() {

        Either<String, Integer> eitherResult = divideNumberWithEither(10, 2);

        //You can call functional operations such as map, flatmap, filter, etc
        List<Integer> eitherResultList = eitherResult
                .map(i -> i * 2)
                .collect(Collectors.toList());
        assertEquals(eitherResultList.size(), 1);
        assertEquals(eitherResultList, List.of((10 / 2) * 2));
    }

    @Test
    void testShouldDivideNumberExceptPerZeroUsingFunctionalOperationsWithoutEither() {

        //Comparing to pure java using map
        Map<String, Object> javaResult = divideNumberWithoutEither(10, 2);
        assertFalse(javaResult.entrySet()
                .stream()
                .noneMatch(entry -> "SUCCESS".equals(entry.getKey())));
    }
}

package example.exception;

import io.vavr.CheckedFunction1;
import io.vavr.Tuple2;
import io.vavr.collection.Array;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.collection.Traversable;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 以下のサイトの回答のコードに期待する結果のアサーションを追加しました。
 * https://stackoverflow.com/questions/61496512/handling-exceptions-idiomatically-with-vavr
 */
public class CheckedFunctionTest {

    @Test
    public void handlingExceptionsIdiomatically() {
        Array<String> input = Array.of(
                "123", "456", "789", "not a number", "1111", "another non-number"
        );

        // try and parse all the strings
        Array<Try<Integer>> trys = input.map(CheckedFunction1.liftTry(Integer::parseInt));

        // you can take just the successful values
        Array<Integer> values = trys.flatMap(Try::iterator);

        assertEquals(values.size(), 4);
        assertEquals(values, Array.of(123, 456, 789, 1111));

        // you can look just for the failures
        Array<Throwable> failures = trys.filter(Try::isFailure).map(Try::getCause);

        assertEquals(failures.size(), 2);
        assertTrue(failures.get(0) instanceof NumberFormatException);
        assertTrue(failures.get(1) instanceof NumberFormatException);

        // you can partition by the outcome and extract values/errors
        Tuple2<Traversable<Integer>, Traversable<Throwable>> partition =
                trys.partition(Try::isSuccess)
                        .map(
                                seq -> seq.map(Try::get),
                                seq -> seq.map(Try::getCause)
                        );

        assertEquals(partition._1().size(), 4);
        assertEquals(partition._2().size(), 2);
        assertEquals(partition._1(), Array.of(123, 456, 789, 1111));
        assertTrue(partition._2().head() instanceof NumberFormatException);
        assertTrue(partition._2().last() instanceof NumberFormatException);

        // you can do a short-circuiting parse of the original sequence
        // this will stop at the first error and return it as a failure
        // or take all success values and wrap them in a Seq wrapped in a Try
        Try<Seq<Integer>> shortCircuit = Try.sequence(
                input.iterator() //iterator is lazy, so it's not fully evaluated if not needed
                        .map(CheckedFunction1.liftTry(Integer::parseInt))
        );
        // Failure(java.lang.NumberFormatException: For input string: "not a number")

        // shortCircuitから値を取得しようとすると、"not a number"の所で、NumberFormatExceptionが発生して中断します
        // 以下は、recoverを使って、NumberFormatExceptionが発生した場合、空の値リストを返すようにすることで、
        // shortCircuitがNumberFormatExceptionが発生して中断することを表現しています。
        Try<Seq<Integer>> shortCircuitRecover = Try.sequence(
                input.iterator()
                        .map(CheckedFunction1.liftTry(Integer::parseInt))
        ).recover(r -> Match(r).of(
                Case(Match.Pattern0.of(NumberFormatException.class), List.empty())
        ));
        Seq<Integer> shortCircuitValues = shortCircuitRecover.get();

        assertTrue(shortCircuitValues.isEmpty());
    }
}

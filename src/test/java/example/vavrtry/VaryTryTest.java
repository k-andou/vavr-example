package example.vavrtry;

import io.vavr.collection.Stream;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 以下のサイトのコードを基に一部改変しました。
 * https://www.baeldung.com/vavr-either
 */
public class VaryTryTest {

    @Test
    public void givenHttpClient_whenMakeACall_shouldReturnSuccess() {
        // given
        Integer defaultChainedResult = 1;
        String id = "a";
        HttpClient httpClient = () -> new Response(id);

        // when
        Try<Response> response = Try.of(httpClient::call);
        Integer chainedResult = response
                .map(this::actionThatTakesResponse)
                .getOrElse(defaultChainedResult);
        Stream<String> stream = response.toStream().map(it -> it.id);

        // then
        assertTrue(!stream.isEmpty());
        assertTrue(response.isSuccess());
        response.onSuccess(r -> assertEquals(id, r.id));
        response.andThen(r -> assertEquals(id, r.id));

        assertNotEquals(defaultChainedResult, chainedResult);
        assertEquals(id.hashCode(), chainedResult);
    }

    @Test
    public void givenHttpClientFailure_whenMakeACall_shouldReturnFailure() {
        // given
        Integer defaultChainedResult = 1;
        HttpClient httpClient = () -> {
            throw new ClientException("problem");
        };

        // when
        Try<Response> response = Try.of(httpClient::call);
        Integer chainedResult = response
                .map(this::actionThatTakesResponse)
                .getOrElse(defaultChainedResult);
        Option<Response> optionalResponse = response.toOption();

        // then
        assertTrue(optionalResponse.isEmpty());
        assertTrue(response.isFailure());
        response.onFailure(ex -> assertTrue(ex instanceof ClientException));
        assertEquals(defaultChainedResult, chainedResult);
    }

    @Test
    public void givenHttpClientThatFailure_whenMakeACall_shouldReturnFailureAndNotRecover() {
        // given
        Response defaultResponse = new Response("b");
        HttpClient httpClient = () -> {
            throw new RuntimeException("critical problem");
        };

        // when
        Try<Response> recovered = Try.of(httpClient::call)
                .recover(r -> Match(r).of(
                        Case(Match.Pattern0.of(ClientException.class), defaultResponse)
                ));

        // then
        assertTrue(recovered.isFailure());
        recovered.onFailure(ex -> assertTrue(ex instanceof RuntimeException));
    }

    @Test
    public void givenHttpClientThatFailure_whenMakeACall_shouldReturnFailureAndRecover() {
        // given
        Integer defaultChainedResult = 1;
        String id = "b";
        Response defaultResponse = new Response(id);
        HttpClient httpClient = () -> {
            throw new ClientException("non critical problem");
        };

        // when
        Try<Response> recovered = Try.of(httpClient::call)
                .recover(r -> Match(r).of(
                        Case(Match.Pattern0.of(ClientException.class), defaultResponse),
                        Case(Match.Pattern0.of(IllegalArgumentException.class), defaultResponse)
                ));
        Integer chainedResult = recovered
                .map(this::actionThatTakesResponse)
                .getOrElse(defaultChainedResult);

        // then
        assertTrue(recovered.isSuccess());
        recovered.onSuccess(r -> assertEquals(id, r.id));

        assertNotEquals(defaultChainedResult, chainedResult);
        assertEquals(id.hashCode(), chainedResult);
    }

    private int actionThatTakesResponse(Response response) {
        return response.id.hashCode();
    }

    interface HttpClient {
        Response call() throws ClientException;
    }

    static class Response {
        public final String id;

        public Response(String id) {
            this.id = id;
        }
    }

    static class ClientException extends Exception {
        ClientException(String message) {
            super(message);
        }
    }
}

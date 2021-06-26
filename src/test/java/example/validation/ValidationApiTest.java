package example.validation;

import io.vavr.collection.CharSeq;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 以下のサイトのコードを基に一部改変しました。
 * https://www.baeldung.com/vavr-validation-api
 */
public class ValidationApiTest {
    UserValidator userValidator = new UserValidator();

    @Test
    public void
    givenInvalidUserParams_whenValidated_thenInvalidInstance() {
        assertTrue(userValidator.validateUser(" ", "no-email") instanceof Validation.Invalid);
    }

    @Test
    public void
    givenValidUserParams_whenValidated_thenValidInstance() {
        assertTrue(userValidator.validateUser("John", "john@domain.com") instanceof Validation.Valid);
    }

    @Test
    public void
    givenInvalidUserParams_whenValidated_thenIsInvalidIsTrue() {
        assertTrue(userValidator
                .validateUser("John", "no-email")
                .isInvalid());
    }

    @Test
    public void
    givenValidUserParams_whenValidated_thenIsValidMethodIsTrue() {
        assertTrue(userValidator
                .validateUser("John", "john@domain.com")
                .isValid());
    }

    @Test
    public void
    givenInValidUserParams_withGetErrorMethod_thenGetErrorMessages() {
        assertEquals(
                "Name contains invalid characters, Email must be a well-formed email address",
                userValidator.validateUser("John1", "no-email")
                        .getError()
                        .intersperse(", ")
                        .fold("", String::concat));
    }

    @Test
    public void
    givenValidUserParams_withGetMethod_thenGetUserInstance() {
        assertTrue(userValidator.validateUser("John", "john@domain.com").get() instanceof User);
    }

    @Test
    public void
    givenValidUserParams_withtoEitherMethod_thenRightInstance() {
        assertTrue(userValidator.validateUser("John", "john@domain.com")
                .toEither() instanceof Either.Right);
    }

    @Test
    public void
    givenValidUserParams_withFoldMethod_thenEqualstoParamsLength() {
        assertEquals(2, (int) userValidator.validateUser(" ", " ")
                .fold(Seq::length, User::hashCode));
    }

    @Data
    @AllArgsConstructor
    private static class User {
        private String name;
        private String email;
    }

    private static class UserValidator {
        private static final String NAME_PATTERN = "[A-Za-z]+";
        private static final String NAME_ERROR = "Name contains invalid characters";
        private static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        private static final String EMAIL_ERROR = "Email must be a well-formed email address";

        public Validation<Seq<String>, User> validateUser(
                String name, String email) {
            return Validation
                    .combine(
                            validateField(name, NAME_PATTERN, NAME_ERROR),
                            validateField(email, EMAIL_PATTERN, EMAIL_ERROR))
                    .ap(User::new);
        }

        private Validation<String, String> validateField
                (String field, String pattern, String error) {

            return CharSeq.of(field)
                    .replaceAll(pattern, "")
                    .transform(seq -> seq.isEmpty()
                            ? Validation.valid(field)
                            : Validation.invalid(error));
        }
    }
}

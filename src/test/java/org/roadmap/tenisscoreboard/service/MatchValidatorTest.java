package org.roadmap.tenisscoreboard.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.roadmap.tennisscoreboard.exception.PageValidationException;
import org.roadmap.tennisscoreboard.exception.ValidationException;
import org.roadmap.tennisscoreboard.util.MatchValidator;

public class MatchValidatorTest {
    @Test
    public void givenValidPlayerName_whenValidatePlayerName_thenNoException() {
        Assertions.assertDoesNotThrow(() -> MatchValidator.validateName("Дж. К. Роулинг"));
        Assertions.assertDoesNotThrow(() -> MatchValidator.validateName("Д''Артаньян"));
        Assertions.assertDoesNotThrow(() -> MatchValidator.validateName("Jean-Luc"));
        Assertions.assertDoesNotThrow(() -> MatchValidator.validateName("Anne-Marie O''Neil"));
        Assertions.assertDoesNotThrow(() -> MatchValidator.validateName("Roger Federer"));
        Assertions.assertDoesNotThrow(() -> MatchValidator.validateName("Евгений Кафельников"));
    }

    @Test
    public void givenInvalidPlayerName_whenValidatePlayerName_thenThrowValidationException() {
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validateName(null));
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validateName(""));
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validateName(" "));
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validateName("123"));
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validateName("   a"));
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validateName("TOO-LONG-FOR-A-NAME___"));
    }

    @Test
    public void givenValidPage_whenValidatePage_thenNoException() {
        Assertions.assertDoesNotThrow(() -> MatchValidator.validatePage("5"));
        Assertions.assertDoesNotThrow(() -> MatchValidator.validatePage("100"));
        Assertions.assertDoesNotThrow(() -> MatchValidator.validatePage("10000"));
    }

    @Test
    public void givenInvalidPage_whenValidatePage_thenThrowPageValidationException() {
        Assertions.assertThrows(PageValidationException.class, () -> MatchValidator.validatePage(null));
        Assertions.assertThrows(PageValidationException.class, () -> MatchValidator.validatePage(" "));
        Assertions.assertThrows(PageValidationException.class, () -> MatchValidator.validatePage("NOT_A_NUMBER"));
        Assertions.assertThrows(PageValidationException.class, () -> MatchValidator.validatePage("50,5"));
        Assertions.assertThrows(PageValidationException.class, () -> MatchValidator.validatePage("50.5"));
        Assertions.assertThrows(PageValidationException.class, () -> MatchValidator.validatePage("-5"));
        Assertions.assertThrows(PageValidationException.class, () -> MatchValidator.validatePage("*"));
    }

    @Test
    public void givenValidUUID_whenValidateUUID_thenNoException() {
        Assertions.assertDoesNotThrow(() -> MatchValidator.validateUUID("503ce35f-8d78-44d9-9c9f-66f4df6732ca"));
        Assertions.assertDoesNotThrow(() -> MatchValidator.validateUUID("fb535f52-67ca-468e-9788-2f3ec9a4cbd3"));
        Assertions.assertDoesNotThrow(() -> MatchValidator.validateUUID("ccf09bbf-274c-442f-afb9-e8faa6cd6bcf"));
    }

    @Test
    public void givenInvalidUUID_whenValidateUUID_thenThrowValidationException() {
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validateUUID(null));
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validateUUID(" "));
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validateUUID("10"));
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validateUUID("50,5"));
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validateUUID("50.5"));
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validateUUID("afb9-e8faa6cd6bcf"));
    }

    @Test
    public void givenValidPlayerId_whenValidatePlayerId_thenNoException() {
        Assertions.assertDoesNotThrow(() -> MatchValidator.validatePlayerId("1"));
        Assertions.assertDoesNotThrow(() -> MatchValidator.validatePlayerId("100"));
        Assertions.assertDoesNotThrow(() -> MatchValidator.validatePlayerId("999"));
    }

    @Test
    public void givenInvalidPlayerId_whenValidatePlayerId_thenThrowValidationException() {
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validatePlayerId(null));
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validatePlayerId(" "));
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validatePlayerId("not_a_number"));
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validatePlayerId("*"));
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validatePlayerId("50,5"));
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validatePlayerId("50.5"));
        Assertions.assertThrows(ValidationException.class, () -> MatchValidator.validatePlayerId("-1"));
    }
}
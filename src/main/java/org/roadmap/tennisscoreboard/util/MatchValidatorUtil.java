package org.roadmap.tennisscoreboard.util;

import org.roadmap.tennisscoreboard.exception.ExceptionMessages;
import org.roadmap.tennisscoreboard.exception.InvalidMatchIdException;
import org.roadmap.tennisscoreboard.exception.PageValidationException;
import org.roadmap.tennisscoreboard.exception.ValidationException;

import java.util.UUID;

public final class MatchValidatorUtil {
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 100;
    private static final String NAME_PATTERN = "^[A-Za-z]+";

    private MatchValidatorUtil() {
    }

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException(ExceptionMessages.MISSING_NAME);
        }
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new ValidationException(
                    String.format(
                            ExceptionMessages.INVALID_NAME_LENGTH,
                            MIN_NAME_LENGTH,
                            MAX_NAME_LENGTH
                    ));
        }
        if (!name.matches(NAME_PATTERN)) {
            // TODO: расширить паттерн для имени
            throw new ValidationException(ExceptionMessages.INVALID_NAME_PATTERN);
        }
    }

    public static void validateUUID(String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new ValidationException(ExceptionMessages.MISSING_ID);
        }
        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException ex) {
            throw new InvalidMatchIdException(
                    String.format(
                            ExceptionMessages.INVALID_MATCH_ID_FORMAT,
                            uuid
                    ));
        }
    }

    public static void validatePlayerId(String playerIdString) {
        if (playerIdString == null || playerIdString.trim().isEmpty()) {
            throw new ValidationException(ExceptionMessages.MISSING_ID);
        }
        try {
            Integer.valueOf(playerIdString);
        } catch (NumberFormatException ex) {
            throw new ValidationException(
                    String.format(
                            ExceptionMessages.INVALID_PLAYER_ID_FORMAT,
                            playerIdString
                    ));
        }
    }

    public static void validatePage(String pageNumberString) {
        if (pageNumberString == null || pageNumberString.trim().isEmpty()) {
            throw new PageValidationException(ExceptionMessages.MISSING_PAGE);
        }
        try {
            int pageNumber = Integer.parseInt(pageNumberString);
            if (pageNumber < 1) {
                throw new PageValidationException(ExceptionMessages.INVALID_PAGE_MIN);
            }
        } catch (NumberFormatException ex) {
            throw new PageValidationException(ExceptionMessages.INVALID_PAGE_FORMAT);
        }
    }
}

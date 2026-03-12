package org.roadmap.tennisscoreboard.util;

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
            throw new ValidationException("Name is required.");
        }
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new ValidationException(
                    String.format(
                            "Name length must be between %s and %s characters.",
                            MIN_NAME_LENGTH,
                            MAX_NAME_LENGTH
                    ));
        }
        if (!name.matches(NAME_PATTERN)) {
            // TODO: расширить паттерн для имени
            throw new ValidationException("Name must contain only these: letters.");
        }
    }

    public static void validateUUID(String uuid) {
        if (uuid == null || uuid.trim().isEmpty()) {
            throw new ValidationException("Id is required.");
        }
        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException ex) {
            throw new ValidationException(
                    String.format(
                            "Invalid match Id format: %s.",
                            uuid
                    ));
        }
    }

    public static void validatePlayerId(String playerIdString) {
        if (playerIdString == null || playerIdString.trim().isEmpty()) {
            throw new ValidationException("Id is required.");
        }
        try {
            Integer.valueOf(playerIdString);
        } catch (NumberFormatException ex) {
            throw new ValidationException(
                    String.format(
                            "Invalid player id: %s.",
                            playerIdString
                    ));
        }
    }

    public static void validatePage(String pageNumberString) {
        if (pageNumberString == null || pageNumberString.trim().isEmpty()) {
            throw new ValidationException("");
        }
        try {
            int pageNumber = Integer.parseInt(pageNumberString);
            if (pageNumber < 1) {
                throw new ValidationException("Page should be positive.");
            }
        } catch (NumberFormatException ex) {
            throw new ValidationException("Invalid page format.");
        }
    }
}

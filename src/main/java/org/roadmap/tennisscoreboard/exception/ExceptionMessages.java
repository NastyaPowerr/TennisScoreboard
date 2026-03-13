package org.roadmap.tennisscoreboard.exception;

public final class ExceptionMessages {
    private ExceptionMessages() {
    }

    public static final String INVALID_PAGE_SHOW_PAGE_ONE = "Invalid page formation. Showing page 1.";
    public static final String MATCH_NOT_FOUND = "Match with id %s not found.";
    public static final String PLAYERS_THE_SAME_NAME = "Players must have different names.";

    public static final String MISSING_NAME = "Name is required.";
    public static final String INVALID_NAME_LENGTH = "Name length must be between %s and %s characters.";
    public static final String INVALID_NAME_PATTERN = "Name must contain only these: letters.";
    public static final String MISSING_ID = "Id is required.";
    public static final String INVALID_MATCH_ID_FORMAT = "Invalid match id format: %s.";
    public static final String INVALID_PLAYER_ID_FORMAT = "Invalid player id: %s.";
    public static final String MISSING_PAGE = "Page field is empty.";
    public static final String INVALID_PAGE_FORMAT = "Invalid page format. Page must be a number.";
    public static final String INVALID_PAGE_MIN = "Page should be positive.";

    public static final String PLAYER_ALREADY_EXISTS = "Player with name %s already exists.";

    public static final String MISSING_WINNER = "Cannot have a won match without a winner.";
    public static final String PLAYER_NOT_IN_MATCH = "Player with that id is not in that match.";

    public static final String WRONG_USE_OF_NEXT = "Should not call .next() on Point.AD";
    public static final String COULD_NOT_GET_OPPONENT = "Couldn't get an opponent player.";

    public static final String INTERNAL_ERROR = "Internal error. Please try later.";
}

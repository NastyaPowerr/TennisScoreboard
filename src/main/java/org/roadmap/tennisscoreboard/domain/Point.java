package org.roadmap.tennisscoreboard.domain;

import org.roadmap.tennisscoreboard.exception.ExceptionMessages;

public enum Point {
    ZERO(0),
    FIFTEEN(15),
    THIRTY(30),
    FORTY(40),
    AD(100);

    private final int value;

    Point(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (this == AD) {
            return "AD";
        }
        return String.valueOf(value);
    }

    public Point next() {
        return switch (this) {
            case ZERO -> FIFTEEN;
            case FIFTEEN -> THIRTY;
            case THIRTY -> FORTY;
            case FORTY -> AD;
            default -> throw new IllegalStateException(ExceptionMessages.WRONG_USE_OF_NEXT);
        };
    }
}

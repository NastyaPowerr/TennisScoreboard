package org.roadmap.tennisscoreboard.domain;

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

    public boolean equalTo(Point point) {
        return this.value == point.value;
    }

    @Override
    public String toString() {
        if (this == AD) {
            return "AD";
        }
        return String.valueOf(value);
    }
}

package org.roadmap.tennisscoreboard.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PlayerScore {
    @Setter
    private Point playerPoint;
    private int playerGame;
    private int playerSet;
    private int tiebreakPoints;

    public PlayerScore() {
        this.playerPoint = Point.ZERO;
        this.playerGame = 0;
        this.playerSet = 0;
        this.tiebreakPoints = 0;
    }

    public void incrementPoint() {
        this.playerPoint = playerPoint.next();
    }

    public void incrementGame() {
        this.playerGame = playerGame + 1;
    }

    public void incrementSet() {
        this.playerSet = playerSet + 1;
    }

    public void incrementTiebreakPoints() {
        this.tiebreakPoints = tiebreakPoints + 1;
    }

    public void clearPoints() {
        this.playerPoint = Point.ZERO;
    }

    public void clearGame() {
        this.playerGame = 0;
    }

    public void clearTiebreakPoints() {
        this.tiebreakPoints = 0;
    }
}

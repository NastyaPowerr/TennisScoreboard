package org.roadmap.tennisscoreboard.domain;

public class PlayerScore {
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

    public Point getPlayerPoint() {
        return playerPoint;
    }

    public int getPlayerGame() {
        return playerGame;
    }

    public int getPlayerSet() {
        return playerSet;
    }

    public int getTiebreakPoints() {
        return tiebreakPoints;
    }

    public void incrementPoint() {
        this.playerPoint = playerPoint.next();
    }

    public void setPlayerPoint(Point point) {
        this.playerPoint = point;
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

    @Override
    public String toString() {
        return "PlayerScore{" +
                "playerPoint=" + playerPoint +
                ", playerGame=" + playerGame +
                ", playerSet=" + playerSet +
                ", tiebreakPoints=" + tiebreakPoints +
                '}';
    }
}

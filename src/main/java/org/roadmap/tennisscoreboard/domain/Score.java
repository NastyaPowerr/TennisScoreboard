package org.roadmap.tennisscoreboard.domain;

public class Score {
    private Point firstPlayerPoint;
    private Point secondPlayerPoint;
    private int firstPlayerGame;
    private int secondPlayerGame;
    private int firstPlayerSet;
    private int secondPlayerSet;

    private boolean isTieBreak;
    private int firstPlayerTiebreakPoint;
    private int secondPlayerTiebreakPoint;

    public Score() {
        this.firstPlayerPoint = Point.ZERO;
        this.secondPlayerPoint = Point.ZERO;
        this.firstPlayerGame = 0;
        this.secondPlayerGame = 0;
        this.firstPlayerSet = 0;
        this.secondPlayerSet = 0;
        this.isTieBreak = false;
        this.firstPlayerTiebreakPoint = 0;
        this.secondPlayerTiebreakPoint = 0;
    }

    public Point getFirstPlayerPoint() {
        return firstPlayerPoint;
    }

    public void setFirstPlayerPoint(Point firstPlayerPoint) {
        this.firstPlayerPoint = firstPlayerPoint;
    }

    public Point getSecondPlayerPoint() {
        return secondPlayerPoint;
    }

    public void setSecondPlayerPoint(Point secondPlayerPoint) {
        this.secondPlayerPoint = secondPlayerPoint;
    }

    public int getFirstPlayerGame() {
        return firstPlayerGame;
    }

    public void setFirstPlayerGame(int firstPlayerGame) {
        this.firstPlayerGame = firstPlayerGame;
    }

    public int getSecondPlayerGame() {
        return secondPlayerGame;
    }

    public void setSecondPlayerGame(int secondPlayerGame) {
        this.secondPlayerGame = secondPlayerGame;
    }

    public int getFirstPlayerSet() {
        return firstPlayerSet;
    }

    public void setFirstPlayerSet(int firstPlayerSet) {
        this.firstPlayerSet = firstPlayerSet;
    }

    public int getSecondPlayerSet() {
        return secondPlayerSet;
    }

    public void setSecondPlayerSet(int secondPlayerSet) {
        this.secondPlayerSet = secondPlayerSet;
    }

    public int getFirstPlayerTiebreakPoint() {
        return firstPlayerTiebreakPoint;
    }

    public void setFirstPlayerTiebreakPoint(int firstPlayerTiebreakPoint) {
        this.firstPlayerTiebreakPoint = firstPlayerTiebreakPoint;
    }

    public int getSecondPlayerTiebreakPoint() {
        return secondPlayerTiebreakPoint;
    }

    public void setSecondPlayerTiebreakPoint(int secondPlayerTiebreakPoint) {
        this.secondPlayerTiebreakPoint = secondPlayerTiebreakPoint;
    }

    public boolean isTieBreak() {
        return isTieBreak;
    }

    public void setTieBreak(boolean tieBreak) {
        isTieBreak = tieBreak;
    }

    //for test only
    @Override
    public String toString() {
        return "Score{" +
                "firstPlayerScore=" + firstPlayerPoint +
                ", secondPlayerScore=" + secondPlayerPoint +
                ", firstPlayerGame=" + firstPlayerGame +
                ", secondPlayerGame=" + secondPlayerGame +
                ", firstPlayerSet=" + firstPlayerSet +
                ", secondPlayerSet=" + secondPlayerSet +
                ", tiebreak=" + isTieBreak +
                ", firstPlayerTieBreakPoint=" + firstPlayerTiebreakPoint +
                ", secondPlayerTieBreakPoint=" + secondPlayerTiebreakPoint +
                '}';
    }
}

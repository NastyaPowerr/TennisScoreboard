package org.roadmap.dto;

public class Score {
    private int firstPlayerScore;
    private int secondPlayerScore;
    private int firstPlayerSet;
    private int secondPlayerSet;

    public Score(int firstPlayerScore, int secondPlayerScore, int firstPlayerSet, int secondPlayerSet) {
        this.firstPlayerScore = firstPlayerScore;
        this.secondPlayerScore = secondPlayerScore;
        this.firstPlayerSet = firstPlayerSet;
        this.secondPlayerSet = secondPlayerSet;
    }

    public Score(int firstPlayerScore, int secondPlayerScore) {
        this.firstPlayerScore = firstPlayerScore;
        this.secondPlayerScore = secondPlayerScore;
    }

    public int getFirstPlayerScore() {
        return firstPlayerScore;
    }

    public void setFirstPlayerScore(int firstPlayerScore) {
        this.firstPlayerScore = firstPlayerScore;
    }

    public int getSecondPlayerScore() {
        return secondPlayerScore;
    }

    public void setSecondPlayerScore(int secondPlayerScore) {
        this.secondPlayerScore = secondPlayerScore;
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

    //for test only
    @Override
    public String toString() {
        return "Score{" +
                "firstPlayerScore=" + firstPlayerScore +
                ", secondPlayerScore=" + secondPlayerScore +
                ", firstPlayerSet=" + firstPlayerSet +
                ", secondPlayerSet=" + secondPlayerSet +
                '}';
    }
}

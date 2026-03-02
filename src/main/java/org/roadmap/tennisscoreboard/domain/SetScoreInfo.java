package org.roadmap.tennisscoreboard.domain;

public class SetScoreInfo {
    private int firstPlayerGames;
    private int secondPlayerGames;

    public SetScoreInfo(int firstPlayerGames, int secondPlayerGames) {
        this.firstPlayerGames = firstPlayerGames;
        this.secondPlayerGames = secondPlayerGames;
    }

    public int getFirstPlayerGames() {
        return firstPlayerGames;
    }

    public void setFirstPlayerGames(int firstPlayerGames) {
        this.firstPlayerGames = firstPlayerGames;
    }

    public int getSecondPlayerGames() {
        return secondPlayerGames;
    }

    public void setSecondPlayerGames(int secondPlayerGames) {
        this.secondPlayerGames = secondPlayerGames;
    }
}

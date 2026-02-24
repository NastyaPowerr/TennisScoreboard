package org.roadmap.tennisscoreboard.domain;

import org.roadmap.tennisscoreboard.entity.Player;

public class OngoingMatch {
    private Integer id;
    private Player firstPlayer;
    private Player secondPlayer;
    private Score score;

    public OngoingMatch(Integer id, Player firstPlayer, Player secondPlayer, Score score) {
        this.id = id;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}

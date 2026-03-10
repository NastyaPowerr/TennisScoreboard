package org.roadmap.tennisscoreboard.domain;

import org.roadmap.tennisscoreboard.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class OngoingMatch {
    private final Score scoreModel;
    private final Map<Integer, SetScoreInfo> setsHistory;
    private Player firstPlayer;
    private Player secondPlayer;
    private boolean tiebreak;
    private boolean finished;

    public OngoingMatch(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.scoreModel = new Score();
        this.setsHistory = new HashMap<>();
        this.tiebreak = false;
        this.finished = false;
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

    public Map<Integer, SetScoreInfo> getSetsHistory() {
        return setsHistory;
    }

    public Score getScoreModel() {
        return scoreModel;
    }

    public boolean isTiebreak() {
        return tiebreak;
    }

    public void setTiebreak(boolean tiebreak) {
        this.tiebreak = tiebreak;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "OngoingMatch{" +
                ", firstPlayer=" + firstPlayer +
                ", secondPlayer=" + secondPlayer +
                ", score=" + scoreModel +
                ", tiebreak=" + tiebreak +
                '}';
    }
}

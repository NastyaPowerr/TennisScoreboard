package org.roadmap.tennisscoreboard.domain;

import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.exception.ExceptionMessages;

import java.util.Optional;

public class Score {
    private static final int SETS_TO_WIN = 2;
    private final PlayerScore firstPlayerScore;
    private final PlayerScore secondPlayerScore;

    public Score() {
        this.firstPlayerScore = new PlayerScore();
        this.secondPlayerScore = new PlayerScore();
    }

    public PlayerScore getFirstPlayerScore() {
        return firstPlayerScore;
    }

    public PlayerScore getSecondPlayerScore() {
        return secondPlayerScore;
    }

    public PlayerScore getOpponentPlayerScore(PlayerScore player) {
        if (player.equals(firstPlayerScore)) {
            return secondPlayerScore;
        }
        if (player.equals(secondPlayerScore)) {
            return firstPlayerScore;
        }
        throw new IllegalStateException(ExceptionMessages.COULD_NOT_GET_OPPONENT);
    }

    public Optional<Player> getWinner(Player firstPlayer, Player secondPlayer) {
        if (firstPlayerScore.getPlayerSet() >= SETS_TO_WIN) {
            return Optional.of(firstPlayer);
        }
        if (secondPlayerScore.getPlayerSet() >= SETS_TO_WIN) {
            return Optional.of(secondPlayer);
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return firstPlayerScore + ", " +  secondPlayerScore;
    }
}

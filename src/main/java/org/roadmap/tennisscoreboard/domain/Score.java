package org.roadmap.tennisscoreboard.domain;

import lombok.Getter;
import org.roadmap.tennisscoreboard.dto.PlayerDto;
import org.roadmap.tennisscoreboard.exception.ExceptionMessages;

import java.util.Optional;

@Getter
public class Score {
    private static final int SETS_TO_WIN = 2;
    private final PlayerScore firstPlayerScore;
    private final PlayerScore secondPlayerScore;

    public Score() {
        this.firstPlayerScore = new PlayerScore();
        this.secondPlayerScore = new PlayerScore();
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

    public Optional<PlayerDto> getWinner(PlayerDto firstPlayer, PlayerDto secondPlayer) {
        if (firstPlayerScore.getPlayerSet() >= SETS_TO_WIN) {
            return Optional.of(firstPlayer);
        }
        if (secondPlayerScore.getPlayerSet() >= SETS_TO_WIN) {
            return Optional.of(secondPlayer);
        }
        return Optional.empty();
    }
}

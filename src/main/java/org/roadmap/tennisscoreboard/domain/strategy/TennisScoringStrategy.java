package org.roadmap.tennisscoreboard.domain.strategy;

import org.roadmap.tennisscoreboard.domain.PlayerScore;

public interface TennisScoringStrategy {
    void pointWon(PlayerScore scoringPlayer, PlayerScore opponent);

    boolean isGameWon(PlayerScore scoringPlayer, PlayerScore opponent);

    boolean isSetWon(PlayerScore scoringPlayer, PlayerScore opponent);

    boolean isMatchWon(PlayerScore scoringPlayer, PlayerScore opponent);
}

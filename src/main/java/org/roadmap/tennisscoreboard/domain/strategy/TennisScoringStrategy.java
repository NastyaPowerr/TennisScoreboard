package org.roadmap.tennisscoreboard.domain.strategy;

import org.roadmap.tennisscoreboard.domain.PlayerScore;

public abstract class TennisScoringStrategy {
    protected static final int SETS_TO_WIN = 2;
    protected static final int DIFF_TO_WIN = 2;

    public abstract void pointWon(PlayerScore scoringPlayer, PlayerScore opponent);

    public abstract boolean isGameWon(PlayerScore scoringPlayer, PlayerScore opponent);

    public abstract boolean isSetWon(PlayerScore scoringPlayer, PlayerScore opponent);

    public boolean isMatchWon(PlayerScore scoringPlayer) {
        return scoringPlayer.getPlayerSet() >= SETS_TO_WIN;
    }
}

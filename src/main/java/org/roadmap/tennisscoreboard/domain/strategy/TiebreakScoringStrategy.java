package org.roadmap.tennisscoreboard.domain.strategy;

import org.roadmap.tennisscoreboard.domain.PlayerScore;

public class TiebreakScoringStrategy extends TennisScoringStrategy {
    private static final int MIN_TIEBREAK_POINTS_TO_WIN_GAME = 7;
    private static final int TIEBREAK_GAMES_TO_WIN_SET = 7;

    @Override
    public void pointWon(PlayerScore scoringPlayer, PlayerScore opponent) {
        scoringPlayer.incrementTiebreakPoints();

        if (isGameWon(scoringPlayer, opponent)) {
            scoringPlayer.incrementGame();
            if (isSetWon(scoringPlayer, opponent)) {
                scoringPlayer.incrementSet();

                scoringPlayer.clearTiebreakPoints();
                opponent.clearTiebreakPoints();
                scoringPlayer.clearGame();
                opponent.clearGame();
            }
        }
    }

    @Override
    public boolean isGameWon(PlayerScore scoringPlayer, PlayerScore opponent) {
        return scoringPlayer.getTiebreakPoints() >= MIN_TIEBREAK_POINTS_TO_WIN_GAME &&
                scoringPlayer.getTiebreakPoints() - opponent.getTiebreakPoints() >= DIFF_TO_WIN;
    }

    @Override
    public boolean isSetWon(PlayerScore scoringPlayer, PlayerScore opponent) {
        return scoringPlayer.getPlayerGame() >= TIEBREAK_GAMES_TO_WIN_SET;
    }
}

package org.roadmap.tennisscoreboard.domain.strategy;

import org.roadmap.tennisscoreboard.domain.PlayerScore;
import org.roadmap.tennisscoreboard.domain.Point;

public class DefaultScoringStrategy extends TennisScoringStrategy {
    private static final int GAMES_TO_WIN_SET = 6;

    @Override
    public void pointWon(PlayerScore scoringPlayer, PlayerScore opponent) {
        if (opponent.getPlayerPoint() == Point.AD) {
            opponent.setPlayerPoint(Point.FORTY);
            return;
        }
        if (scoringPlayer.getPlayerPoint() == Point.AD) {
            scoringPlayer.incrementGame();

            scoringPlayer.clearPoints();
            opponent.clearPoints();
            return;
        }

        scoringPlayer.incrementPoint();
        if (isGameWon(scoringPlayer, opponent)) {
            scoringPlayer.incrementGame();

            scoringPlayer.clearPoints();
            opponent.clearPoints();
        }

        if (isSetWon(scoringPlayer, opponent)) {
            scoringPlayer.incrementSet();

            scoringPlayer.clearGame();
            opponent.clearGame();

            scoringPlayer.clearPoints();
            opponent.clearPoints();
        }
    }

    @Override
    public boolean isGameWon(PlayerScore scoringPlayer, PlayerScore opponent) {
        return scoringPlayer.getPlayerPoint() == Point.AD && opponent.getPlayerPoint() != Point.FORTY;
    }

    @Override
    public boolean isSetWon(PlayerScore scoringPlayer, PlayerScore opponent) {
        return scoringPlayer.getPlayerGame() >= GAMES_TO_WIN_SET &&
                scoringPlayer.getPlayerGame() - opponent.getPlayerGame() >= DIFF_TO_WIN;
    }
}

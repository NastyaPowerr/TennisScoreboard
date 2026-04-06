package org.roadmap.tenisscoreboard.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.roadmap.tennisscoreboard.domain.PlayerScore;
import org.roadmap.tennisscoreboard.domain.strategy.TennisScoringStrategy;
import org.roadmap.tennisscoreboard.domain.strategy.TiebreakScoringStrategy;

public class TieBreakScoringStrategyTest {
    private TennisScoringStrategy scoringStrategy;
    private PlayerScore firstPlayer;
    private PlayerScore secondPlayer;

    @BeforeEach
    void setUp() {
        scoringStrategy = new TiebreakScoringStrategy();
        firstPlayer = new PlayerScore();
        secondPlayer = new PlayerScore();
        for (int i = 0; i < 6; i++) {
            firstPlayer.incrementGame();
            secondPlayer.incrementGame();
        }
    }

    @Test
    void givenZeroPoints_whenPointWon_thenPointBecomesOne() {
        scoringStrategy.pointWon(firstPlayer, secondPlayer);
        Assertions.assertEquals(1, firstPlayer.getTiebreakPoints());
        Assertions.assertEquals(0, secondPlayer.getTiebreakPoints());
    }

    @Test
    void givenSixPoints_whenPointWon_thenGameNotWon() {
        for (int i = 0; i < 6; i++) {
            scoringStrategy.pointWon(firstPlayer, secondPlayer);
        }
        Assertions.assertEquals(6, firstPlayer.getPlayerGame());
    }

    @Test
    void givenSevenPointsNoDifferenceInTwoPoints_whenPointWon_thenGameAndSetWon() {
        for (int i = 0; i < 7; i++) {
            scoringStrategy.pointWon(firstPlayer, secondPlayer);
        }
        Assertions.assertTrue(firstPlayer.getTiebreakPoints() - secondPlayer.getTiebreakPoints() < 2);
        Assertions.assertEquals(0, firstPlayer.getPlayerGame());
        Assertions.assertEquals(1, firstPlayer.getPlayerSet());
    }


    @Test
    void givenSixSixPoints_whenPlayerWinsTwoInRow_thenGameAndSetWon() {
        for (int i = 0; i < 6; i++) {
            scoringStrategy.pointWon(firstPlayer, secondPlayer);
            scoringStrategy.pointWon(secondPlayer, firstPlayer);
        }
        scoringStrategy.pointWon(firstPlayer, secondPlayer);
        scoringStrategy.pointWon(firstPlayer, secondPlayer);

        Assertions.assertEquals(0, firstPlayer.getPlayerGame());
        Assertions.assertEquals(1, firstPlayer.getPlayerSet());
    }
}

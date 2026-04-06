package org.roadmap.tenisscoreboard.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.roadmap.tennisscoreboard.domain.PlayerScore;
import org.roadmap.tennisscoreboard.domain.Point;
import org.roadmap.tennisscoreboard.domain.strategy.DefaultScoringStrategy;
import org.roadmap.tennisscoreboard.domain.strategy.TennisScoringStrategy;

public class DefaultScoringStrategyTest {
    private TennisScoringStrategy scoringStrategy;
    private PlayerScore firstPlayer;
    private PlayerScore secondPlayer;

    @BeforeEach
    void setUp() {
        scoringStrategy = new DefaultScoringStrategy();
        firstPlayer = new PlayerScore();
        secondPlayer = new PlayerScore();
    }

    @Test
    void givenZeroPoints_whenPointWon_thenPointsBecomeFifteen() {
        scoringStrategy.pointWon(firstPlayer, secondPlayer);
        Assertions.assertEquals(Point.FIFTEEN, firstPlayer.getPlayerPoint());
        Assertions.assertEquals(Point.ZERO, secondPlayer.getPlayerPoint());
    }

    @Test
    void givenFifteenPoints_whenPointWon_thenPointsBecomeThirty() {
        firstPlayer.incrementPoint();
        scoringStrategy.pointWon(firstPlayer, secondPlayer);
        Assertions.assertEquals(Point.THIRTY, firstPlayer.getPlayerPoint());
    }

    @Test
    void givenThirtyPoints_whenPointWon_thenPointsBecomeForty() {
        firstPlayer.incrementPoint();
        firstPlayer.incrementPoint();
        scoringStrategy.pointWon(firstPlayer, secondPlayer);
        Assertions.assertEquals(Point.FORTY, firstPlayer.getPlayerPoint());
    }

    @Test
    void givenFortyPointsAndOpponentHasNoAdvantage_whenPointWon_thenGameWon() {
        for (int i = 0; i < 3; i++) {
            firstPlayer.incrementPoint();
        }
        Assertions.assertNotEquals(Point.AD, secondPlayer.getPlayerPoint());

        scoringStrategy.pointWon(firstPlayer, secondPlayer);

        Assertions.assertEquals(Point.ZERO, firstPlayer.getPlayerPoint());
        Assertions.assertEquals(1, firstPlayer.getPlayerGame());
    }

    @Test
    void givenFortyFortyPoints_whenPlayerWinPoint_thenPlayerGetAdvantage() {
        for (int i = 0; i < 3; i++) {
            firstPlayer.incrementPoint();
            secondPlayer.incrementPoint();
        }
        scoringStrategy.pointWon(firstPlayer, secondPlayer);
        Assertions.assertEquals(Point.AD, firstPlayer.getPlayerPoint());
        Assertions.assertEquals(Point.FORTY, secondPlayer.getPlayerPoint());
    }

    @Test
    void givenAdvantagePoints_whenOpponentWinPoint_thenBackToForty() {
        for (int i = 0; i < 3; i++) {
            firstPlayer.incrementPoint();
            secondPlayer.incrementPoint();
        }
        scoringStrategy.pointWon(firstPlayer, secondPlayer);
        Assertions.assertEquals(Point.AD, firstPlayer.getPlayerPoint());

        scoringStrategy.pointWon(secondPlayer, firstPlayer);
        Assertions.assertEquals(Point.FORTY, firstPlayer.getPlayerPoint());
        Assertions.assertEquals(Point.FORTY, secondPlayer.getPlayerPoint());
    }

    @Test
    void givenAdvantagePoints_whenPlayerWinPoint_thenGameWon() {
        for (int i = 0; i < 3; i++) {
            firstPlayer.incrementPoint();
            secondPlayer.incrementPoint();
        }
        scoringStrategy.pointWon(firstPlayer, secondPlayer);
        scoringStrategy.pointWon(firstPlayer, secondPlayer);
        Assertions.assertEquals(1, firstPlayer.getPlayerGame());
        Assertions.assertEquals(Point.ZERO, firstPlayer.getPlayerPoint());
    }
}
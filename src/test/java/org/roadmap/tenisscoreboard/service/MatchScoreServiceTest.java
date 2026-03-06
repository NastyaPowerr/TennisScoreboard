package org.roadmap.tenisscoreboard.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.roadmap.tennisscoreboard.domain.Point;
import org.roadmap.tennisscoreboard.domain.Score;
import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.service.MatchScoreService;
import org.roadmap.tennisscoreboard.service.MatchService;

import static org.mockito.Mockito.mock;

public class MatchScoreServiceTest {
    private MatchScoreService matchScoreService;
    private OngoingMatch match;
    private Player firstPlayer;
    private Player secondPlayer;

    @BeforeEach
    void setup() {
        MatchService mockMatchService = mock(MatchService.class);
        matchScoreService = new MatchScoreService(mockMatchService);
        firstPlayer = new Player(1, "TestNameA");
        secondPlayer = new Player(2, "TestNameB");

        match = new OngoingMatch(
                1,
                firstPlayer,
                secondPlayer,
                new Score()
        );
    }

    @Test
    void givenZeroPoints_whenPlayerScores_thenPointIncrements() {
        matchScoreService.givePoint(firstPlayer.getId(), match);

        Assertions.assertEquals(Point.FIFTEEN, match.getScore().getFirstPlayerPoint());
    }

    @Test
    void givenFortyPoints_whenPlayerScores_thenPlayerWinGame() {
        for (int i = 0; i < 4; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), match);
        }
        Assertions.assertEquals(1, match.getScore().getFirstPlayerGame());
    }

    @Test
    void givenFortyFortyPoints_whenPlayerScores_thenPlayerGetAdvantage() {
        for (int i = 0; i < 3; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), match);
            matchScoreService.givePoint(secondPlayer.getId(), match);
        }

        matchScoreService.givePoint(firstPlayer.getId(), match);
        Assertions.assertEquals(Point.AD, match.getScore().getFirstPlayerPoint());

        Assertions.assertEquals(0, match.getScore().getFirstPlayerGame());
        Assertions.assertEquals(Point.FORTY, match.getScore().getSecondPlayerPoint());
    }

    @Test
    void givenFortyFortyPoints_whenFirstPlayerScoresAndSecondPlayerScoresEvenly_thenGameNotEnd() {
        for (int i = 0; i < 12; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), match);
            matchScoreService.givePoint(secondPlayer.getId(), match);
        }

        Assertions.assertEquals(0, match.getScore().getFirstPlayerGame());
        Assertions.assertEquals(0, match.getScore().getSecondPlayerGame());
    }

    @Test
    void givenFortyFortyPoints_whenPlayerScoresTwiceInARow_thenPlayerWinGame() {
        for (int i = 0; i < 3; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), match);
            matchScoreService.givePoint(secondPlayer.getId(), match);
        }
        for (int i = 0; i < 2; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), match);
        }
        Assertions.assertEquals(1, match.getScore().getFirstPlayerGame());
    }

    @Test
    void givenAdvantagePoint_whenOpponentPlayerScores_thenPlayerLoseAdvantagePoint() {
        for (int i = 0; i < 3; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), match);
            matchScoreService.givePoint(secondPlayer.getId(), match);
        }
        matchScoreService.givePoint(firstPlayer.getId(), match);
        Assertions.assertEquals(Point.AD, match.getScore().getFirstPlayerPoint());

        matchScoreService.givePoint(secondPlayer.getId(), match);
        Assertions.assertEquals(Point.FORTY, match.getScore().getFirstPlayerPoint());
    }

    // Tiebreak = when two games are 6:6 and difference between games < 2
    @Test
    void givenTieBreak_whenPlayerScores_thenPointBecomeOne() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), match);
            }
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(secondPlayer.getId(), match);
            }
        }
        matchScoreService.givePoint(firstPlayer.getId(), match);
        Assertions.assertEquals(1, match.getScore().getFirstPlayerTiebreakPoint());
    }

    @Test
    void givenTiebreak_whenFirstPlayerScoresAndSecondPlayerScoresEvenly_thenGameNotEnd() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), match);
            }
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(secondPlayer.getId(), match);
            }
        }

        for (int i = 0; i < 12; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), match);
            matchScoreService.givePoint(secondPlayer.getId(), match);
        }

        Assertions.assertEquals(6, match.getScore().getFirstPlayerGame());
        Assertions.assertEquals(6, match.getScore().getSecondPlayerGame());
    }

    @Test
    void givenTiebreak_whenPlayerScoresSevenTimes_thenPlayerWinSet() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), match);
            }
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(secondPlayer.getId(), match);
            }
        }
        for (int i = 0; i < 7; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), match);
        }

        Assertions.assertEquals(1, match.getScore().getFirstPlayerSet());
        Assertions.assertEquals(0, match.getScore().getFirstPlayerGame());
        Assertions.assertEquals(0, match.getScore().getSecondPlayerGame());
    }

    @Test
    void givenTiebreakPointsSevenSeven_whenPlayerScoresTwiceInARow_thenPlayerWinSet() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), match);
            }
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(secondPlayer.getId(), match);
            }
        }
        for (int i = 0; i < 7; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), match);
            matchScoreService.givePoint(secondPlayer.getId(), match);
        }
        for (int i = 0; i < 2; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), match);
        }
        Assertions.assertEquals(1, match.getScore().getFirstPlayerSet());
    }

    // TODO исправить: ошибка в логике счёта - даже при гейме 6 сет побеждается только после метода .givePoint()
    @Test
    @Disabled
    void givenFiveGames_whenPlayerWinGame_thenPlayerWinSet() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), match);
            }
        }
        for (int j = 0; j < 4; j++) {
            matchScoreService.givePoint(firstPlayer.getId(), match);
        }
        Assertions.assertEquals(1, match.getScore().getFirstPlayerSet());
    }

    @Test
    void givenFiveFiveGames_whenPlayerWinGame_thenGameNotEnd() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), match);
            }
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(secondPlayer.getId(), match);
            }
        }
        for (int j = 0; j < 4; j++) {
            matchScoreService.givePoint(firstPlayer.getId(), match);
        }
        Assertions.assertEquals(0, match.getScore().getFirstPlayerSet());
        Assertions.assertNotEquals(0, match.getScore().getFirstPlayerGame());
    }

    // TODO: заработает после фикса с геймами
    @Test
    @Disabled
    void givenFiveFiveGames_whenPlayerWinGameTwiceInARow_thenPlayerWinSet() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), match);
            }
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(secondPlayer.getId(), match);
            }
        }
        for (int j = 0; j < 4; j++) {
            matchScoreService.givePoint(firstPlayer.getId(), match);
        }
        Assertions.assertEquals(1, match.getScore().getFirstPlayerSet());
    }

    // TODO: заработает после фикса с геймами
    @Test
    @Disabled
    void givenOneOneSet_whenPlayerWinSet_thenMatchEnd() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), match);
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(secondPlayer.getId(), match);
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), match);
            }
        }
        Assertions.assertTrue(matchScoreService.isMatchFinished(match.getScore()));
    }

    // TODO исправить: не удаляю матч из игры, поэтому продолжают начисляться очки
    @Test
    @Disabled
    void givenFinishedMatch_whenPlayerScores_thenNothingChange() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), match);
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(secondPlayer.getId(), match);
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), match);
            }
        }
        Point firstPlayerPoint = match.getScore().getFirstPlayerPoint();
        Point secondPlayerPoint = match.getScore().getSecondPlayerPoint();

        matchScoreService.givePoint(firstPlayer.getId(), match);

        Assertions.assertEquals(firstPlayerPoint, match.getScore().getFirstPlayerPoint());
        Assertions.assertEquals(secondPlayerPoint, match.getScore().getSecondPlayerPoint());
    }
}

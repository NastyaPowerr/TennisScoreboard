package org.roadmap.tenisscoreboard.service;

import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.roadmap.tennisscoreboard.domain.Point;
import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.service.FinishedMatchesPersistenceService;
import org.roadmap.tennisscoreboard.service.MatchScoreService;
import org.roadmap.tennisscoreboard.service.OngoingMatchService;

import java.util.UUID;

import static org.mockito.Mockito.mock;

public class MatchScoreServiceTest {
    private MatchScoreService matchScoreService;
    private OngoingMatchService ongoingMatchService;
    private OngoingMatch match;
    private UUID matchId;
    private Player firstPlayer;
    private Player secondPlayer;

    @BeforeEach
    void setup() {
        ongoingMatchService = new OngoingMatchService();

        FinishedMatchesPersistenceService finishedMatchesService = mock(FinishedMatchesPersistenceService.class);
        matchScoreService = new MatchScoreService(ongoingMatchService, finishedMatchesService);

        firstPlayer = new Player(1, "TestNameA");
        secondPlayer = new Player(2, "TestNameB");
        match = new OngoingMatch(
                firstPlayer,
                secondPlayer
        );
        matchId = ongoingMatchService.create(match);
    }

    @AfterEach
    void deleteMatch() {
        ongoingMatchService.delete(matchId);
    }

    @Test
    void givenZeroPoints_whenPlayerScores_thenPointIncrements() {
        matchScoreService.givePoint(firstPlayer.getId(), matchId);

        Assertions.assertEquals(Point.FIFTEEN, match.getScoreModel().getFirstPlayerScore().getPlayerPoint());
    }

    @Test
    void givenFortyPoints_whenPlayerScores_thenPlayerWinGame() {
        for (int i = 0; i < 4; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), matchId);
        }
        Assertions.assertEquals(Point.ZERO, match.getScoreModel().getFirstPlayerScore().getPlayerPoint());
        Assertions.assertEquals(1, match.getScoreModel().getFirstPlayerScore().getPlayerGame());
    }

    @Test
    void givenFortyFortyPoints_whenPlayerScores_thenPlayerGetAdvantage() {
        for (int i = 0; i < 3; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), matchId);
            matchScoreService.givePoint(secondPlayer.getId(), matchId);
        }

        matchScoreService.givePoint(firstPlayer.getId(), matchId);
        Assertions.assertEquals(Point.AD, match.getScoreModel().getFirstPlayerScore().getPlayerPoint());

        Assertions.assertEquals(0, match.getScoreModel().getFirstPlayerScore().getPlayerGame());
        Assertions.assertEquals(Point.FORTY, match.getScoreModel().getSecondPlayerScore().getPlayerPoint());
    }

    @Test
    void givenFortyFortyPoints_whenFirstPlayerScoresAndSecondPlayerScoresEvenly_thenGameNotEnd() {
        for (int i = 0; i < 12; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), matchId);
            matchScoreService.givePoint(secondPlayer.getId(), matchId);
        }

        Assertions.assertEquals(0, match.getScoreModel().getFirstPlayerScore().getPlayerGame());
        Assertions.assertEquals(0, match.getScoreModel().getSecondPlayerScore().getPlayerGame());
    }

    @Test
    void givenFortyFortyPoints_whenPlayerScoresTwiceInARow_thenPlayerWinGame() {
        for (int i = 0; i < 3; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), matchId);
            matchScoreService.givePoint(secondPlayer.getId(), matchId);
        }
        for (int i = 0; i < 2; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), matchId);
        }
        Assertions.assertEquals(1, match.getScoreModel().getFirstPlayerScore().getPlayerGame());
    }

    @Test
    void givenAdvantagePoint_whenOpponentPlayerScores_thenPlayerLoseAdvantagePoint() {
        for (int i = 0; i < 3; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), matchId);
            matchScoreService.givePoint(secondPlayer.getId(), matchId);
        }
        matchScoreService.givePoint(firstPlayer.getId(), matchId);
        Assertions.assertEquals(Point.AD, match.getScoreModel().getFirstPlayerScore().getPlayerPoint());

        matchScoreService.givePoint(secondPlayer.getId(), matchId);
        Assertions.assertEquals(Point.FORTY, match.getScoreModel().getFirstPlayerScore().getPlayerPoint());
    }

    @Test
    void givenTieBreak_whenPlayerScores_thenPointBecomeOne() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), matchId);
            }
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(secondPlayer.getId(), matchId);
            }
        }
        matchScoreService.givePoint(firstPlayer.getId(), matchId);
        Assertions.assertEquals(1, match.getScoreModel().getFirstPlayerScore().getTiebreakPoints());
    }

    @Test
    void givenTiebreak_whenFirstPlayerScoresAndSecondPlayerScoresEvenly_thenGameNotEnd() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), matchId);
            }
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(secondPlayer.getId(), matchId);
            }
        }

        for (int i = 0; i < 12; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), matchId);
            matchScoreService.givePoint(secondPlayer.getId(), matchId);
        }

        Assertions.assertEquals(6, match.getScoreModel().getFirstPlayerScore().getPlayerGame());
        Assertions.assertEquals(6, match.getScoreModel().getSecondPlayerScore().getPlayerGame());
    }

    @Test
    void givenTiebreak_whenPlayerScoresSevenTimes_thenPlayerWinSet() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), matchId);
            }
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(secondPlayer.getId(), matchId);
            }
        }
        for (int i = 0; i < 7; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), matchId);
        }
        Assertions.assertEquals(1, match.getScoreModel().getFirstPlayerScore().getPlayerSet());
        Assertions.assertEquals(0, match.getScoreModel().getFirstPlayerScore().getPlayerGame());
        Assertions.assertEquals(0, match.getScoreModel().getSecondPlayerScore().getPlayerGame());
    }

    @Test
    void givenTiebreakPointsSevenSeven_whenPlayerScoresTwiceInARow_thenPlayerWinSet() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), matchId);
            }
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(secondPlayer.getId(), matchId);
            }
        }
        for (int i = 0; i < 7; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), matchId);
            matchScoreService.givePoint(secondPlayer.getId(), matchId);
        }
        for (int i = 0; i < 2; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), matchId);
        }
        Assertions.assertEquals(1, match.getScoreModel().getFirstPlayerScore().getPlayerSet());
    }

    @Test
    void givenFiveGames_whenPlayerWinGame_thenPlayerWinSet() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), matchId);
            }
        }
        for (int j = 0; j < 4; j++) {
            matchScoreService.givePoint(firstPlayer.getId(), matchId);
        }
        Assertions.assertEquals(1, match.getScoreModel().getFirstPlayerScore().getPlayerSet());
    }

    @Test
    void givenFiveFiveGames_whenPlayerWinGame_thenSetNotEnd() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), matchId);
            }
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(secondPlayer.getId(), matchId);
            }
        }
        for (int j = 0; j < 4; j++) {
            matchScoreService.givePoint(firstPlayer.getId(), matchId);
        }
        Assertions.assertEquals(0, match.getScoreModel().getFirstPlayerScore().getPlayerSet());
        Assertions.assertNotEquals(0, match.getScoreModel().getFirstPlayerScore().getPlayerGame());
    }

    @Test
    void givenFiveFiveGames_whenPlayerWinGameTwiceInARow_thenPlayerWinSet() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), matchId);
            }
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(secondPlayer.getId(), matchId);
            }
        }
        for (int j = 0; j < 8; j++) {
            matchScoreService.givePoint(firstPlayer.getId(), matchId);
        }
        Assertions.assertEquals(1, match.getScoreModel().getFirstPlayerScore().getPlayerSet());
    }

    @Test
    void givenOneOneSet_whenPlayerWinSet_thenMatchEnd() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), matchId);
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(secondPlayer.getId(), matchId);
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), matchId);
            }
        }
        Assertions.assertTrue(ongoingMatchService.getById(matchId).isFinished());
    }

    @Test
    void givenFinishedMatch_whenPlayerScores_thenNothingChange() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), matchId);
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(secondPlayer.getId(), matchId);
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), matchId);
            }
        }
        Point firstPlayerPoint = match.getScoreModel().getFirstPlayerScore().getPlayerPoint();
        Point secondPlayerPoint = match.getScoreModel().getSecondPlayerScore().getPlayerPoint();

        Assertions.assertThrows(ObjectNotFoundException.class, () -> matchScoreService.givePoint(firstPlayer.getId(), matchId));

        Assertions.assertEquals(firstPlayerPoint, match.getScoreModel().getFirstPlayerScore().getPlayerPoint());
        Assertions.assertEquals(secondPlayerPoint, match.getScoreModel().getSecondPlayerScore().getPlayerPoint());
    }
}

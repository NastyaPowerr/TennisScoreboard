import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    void simpleWinPoint() {
        matchScoreService.givePoint(firstPlayer.getId(), match);

        Assertions.assertEquals(Point.FIFTEEN, match.getScore().getFirstPlayerPoint());
    }

    @Test
    void simpleWinGame() {
        for (int i = 0; i < 4; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), match);
        }

        Assertions.assertEquals(1, match.getScore().getFirstPlayerGame());
        Assertions.assertEquals(Point.ZERO, match.getScore().getFirstPlayerPoint());
    }

    // should not win game after 40-40
    @Test
    void advantageSituation() {
        for (int i = 0; i < 3; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), match);
            matchScoreService.givePoint(secondPlayer.getId(), match);
        }

        Assertions.assertEquals(Point.FORTY, match.getScore().getFirstPlayerPoint());
        Assertions.assertEquals(Point.FORTY, match.getScore().getSecondPlayerPoint());

        matchScoreService.givePoint(firstPlayer.getId(), match);
        Assertions.assertEquals(0, match.getScore().getFirstPlayerGame());
        Assertions.assertEquals(Point.AD, match.getScore().getFirstPlayerPoint());
        Assertions.assertEquals(Point.FORTY, match.getScore().getSecondPlayerPoint());
    }

    @Test
    void tiebreakSituation() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), match);
            }
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(secondPlayer.getId(), match);
            }
        }

        Assertions.assertEquals(6, match.getScore().getFirstPlayerGame());
        Assertions.assertEquals(6, match.getScore().getSecondPlayerGame());
        Assertions.assertEquals(0, match.getScore().getFirstPlayerSet());
        Assertions.assertEquals(0, match.getScore().getSecondPlayerSet());

        for (int i = 0; i < 7; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), match);
        }

        Assertions.assertEquals(1, match.getScore().getFirstPlayerSet());
        Assertions.assertEquals(0, match.getScore().getFirstPlayerGame());
        Assertions.assertEquals(0, match.getScore().getSecondPlayerGame());
    }

    @Test
    void tiebreakSituationDifferenceInPointsLessThan2() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(firstPlayer.getId(), match);
            }
            for (int j = 0; j < 4; j++) {
                matchScoreService.givePoint(secondPlayer.getId(), match);
            }
        }

        for (int i = 0; i < 6; i++) {
            matchScoreService.givePoint(firstPlayer.getId(), match);
            matchScoreService.givePoint(secondPlayer.getId(), match);
        }
        matchScoreService.givePoint(firstPlayer.getId(), match);
        Assertions.assertEquals(0, match.getScore().getFirstPlayerSet());
        Assertions.assertEquals(6, match.getScore().getFirstPlayerGame());
        Assertions.assertEquals(6, match.getScore().getSecondPlayerGame());
        Assertions.assertEquals(7, match.getScore().getFirstPlayerTiebreakPoint());
        Assertions.assertEquals(6, match.getScore().getSecondPlayerTiebreakPoint());

        matchScoreService.givePoint(firstPlayer.getId(), match);
        Assertions.assertEquals(1, match.getScore().getFirstPlayerSet());
        Assertions.assertEquals(0, match.getScore().getFirstPlayerGame());
        Assertions.assertEquals(0, match.getScore().getSecondPlayerGame());
        Assertions.assertEquals(0, match.getScore().getFirstPlayerTiebreakPoint());
        Assertions.assertEquals(0, match.getScore().getSecondPlayerTiebreakPoint());
    }
}

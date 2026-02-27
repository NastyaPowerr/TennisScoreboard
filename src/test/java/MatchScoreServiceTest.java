import org.junit.jupiter.api.Assertions;
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
    private Player firstPlayer;
    private Player secondPlayer;
    private Score score;

    @Test
    void simpleWinPoint() {
        MatchService mockMatchService = mock(MatchService.class);
        matchScoreService = new MatchScoreService(mockMatchService);
        firstPlayer = new Player(1, "TestNameA");
        secondPlayer = new Player(2, "TestNameB");
        score = new Score();

        OngoingMatch match = new OngoingMatch(
                1,
                firstPlayer,
                secondPlayer,
                score
        );

        matchScoreService.givePoint(firstPlayer.getId(), match);

        Assertions.assertEquals(Point.FIFTEEN, match.getScore().getFirstPlayerPoint());
    }
}

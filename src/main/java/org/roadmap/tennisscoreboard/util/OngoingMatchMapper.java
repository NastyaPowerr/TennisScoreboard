package org.roadmap.tennisscoreboard.util;

import org.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.roadmap.tennisscoreboard.domain.SetScoreInfo;
import org.roadmap.tennisscoreboard.dto.view.FinishedMatchView;
import org.roadmap.tennisscoreboard.dto.view.MatchView;

import java.util.Map;
import java.util.UUID;

public final class OngoingMatchMapper {
    private OngoingMatchMapper() {
    }

    public static MatchView toDefaultMatchView(OngoingMatch match, UUID matchId) {
        return new MatchView(
                matchId,
                match.getFirstPlayer(),
                match.getSecondPlayer(),
                match.getScoreModel().getFirstPlayerScore().getPlayerPoint().toString(),
                match.getScoreModel().getSecondPlayerScore().getPlayerPoint().toString(),
                match.getScoreModel().getFirstPlayerScore().getPlayerGame(),
                match.getScoreModel().getSecondPlayerScore().getPlayerGame(),
                match.getScoreModel().getFirstPlayerScore().getPlayerSet(),
                match.getScoreModel().getSecondPlayerScore().getPlayerSet()
        );
    }

    public static MatchView toTiebreakMatchView(OngoingMatch match, UUID matchId) {
        return new MatchView(
                matchId,
                match.getFirstPlayer(),
                match.getSecondPlayer(),
                String.valueOf(match.getScoreModel().getFirstPlayerScore().getTiebreakPoints()),
                String.valueOf(match.getScoreModel().getSecondPlayerScore().getTiebreakPoints()),
                match.getScoreModel().getFirstPlayerScore().getPlayerGame(),
                match.getScoreModel().getSecondPlayerScore().getPlayerGame(),
                match.getScoreModel().getFirstPlayerScore().getPlayerSet(),
                match.getScoreModel().getSecondPlayerScore().getPlayerSet()
        );
    }

    public static FinishedMatchView toFinishedMatchView(OngoingMatch match, UUID matchId, Map<Integer, SetScoreInfo> setsHistory) {
        return new FinishedMatchView(
                matchId,
                match.getFirstPlayer(),
                match.getSecondPlayer(),
                match.getScoreModel().getWinner(match.getFirstPlayer(), match.getSecondPlayer()).get(),
                setsHistory.get(0).firstPlayerGames(),
                setsHistory.get(0).secondPlayerGames(),
                setsHistory.get(1).firstPlayerGames(),
                setsHistory.get(1).secondPlayerGames(),
                setsHistory.get(2) != null ? setsHistory.get(2).firstPlayerGames() : 0,
                setsHistory.get(2) != null ? setsHistory.get(2).secondPlayerGames() : 0
        );
    }
}

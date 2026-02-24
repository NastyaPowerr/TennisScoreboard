package org.roadmap.tennisscoreboard.dto.view;

import org.roadmap.tennisscoreboard.domain.Score;
import org.roadmap.tennisscoreboard.entity.Player;

public record MatchView(
        Player firstPlayer,
        Player secondPlayer,
        Player winner,
        Score score,
        int firstPlayerFirstSet,
        int secondPlayerFirstSet,
        int firstPlayerSecondSet,
        int secondPlayerSecondSet,
        int firstPlayerThirdSet,
        int secondPlayerThirdSet
) {
}

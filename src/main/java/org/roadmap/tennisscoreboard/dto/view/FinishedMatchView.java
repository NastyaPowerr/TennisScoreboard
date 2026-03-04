package org.roadmap.tennisscoreboard.dto.view;

import org.roadmap.tennisscoreboard.entity.Player;

public record FinishedMatchView(
        Player firstPlayer,
        Player secondPlayer,
        Player winner,
        int firstPlayerFirstSet,
        int secondPlayerFirstSet,
        int firstPlayerSecondSet,
        int secondPlayerSecondSet,
        int firstPlayerThirdSet,
        int secondPlayerThirdSet
) {
}

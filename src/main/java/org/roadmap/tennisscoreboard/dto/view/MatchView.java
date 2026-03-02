package org.roadmap.tennisscoreboard.dto.view;

import org.roadmap.tennisscoreboard.entity.Player;

public record MatchView(
        Player firstPlayer,
        Player secondPlayer,
        Player winner,
        String firstPlayerPoints,
        String secondPlayerPoints,
        int firstPlayerGame,
        int secondPlayerGame,
        int firstPlayerSet,
        int secondPlayerSet,

        int firstPlayerFirstSet,
        int secondPlayerFirstSet,
        int firstPlayerSecondSet,
        int secondPlayerSecondSet,
        int firstPlayerThirdSet,
        int secondPlayerThirdSet
) {
}

package org.roadmap.tennisscoreboard.dto.view;

import org.roadmap.tennisscoreboard.entity.Player;

public record MatchView(
        Player firstPlayer,
        Player secondPlayer,
        String firstPlayerPoints,
        String secondPlayerPoints,
        int firstPlayerGame,
        int secondPlayerGame,
        int firstPlayerSet,
        int secondPlayerSet
) {
}

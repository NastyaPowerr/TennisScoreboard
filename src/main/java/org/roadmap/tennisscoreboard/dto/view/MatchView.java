package org.roadmap.tennisscoreboard.dto.view;

import org.roadmap.tennisscoreboard.dto.PlayerDto;

public record MatchView(
        PlayerDto firstPlayer,
        PlayerDto secondPlayer,
        String firstPlayerPoints,
        String secondPlayerPoints,
        int firstPlayerGame,
        int secondPlayerGame,
        int firstPlayerSet,
        int secondPlayerSet
) {
}

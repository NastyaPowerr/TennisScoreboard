package org.roadmap.tennisscoreboard.dto.view;

import org.roadmap.tennisscoreboard.dto.PlayerDto;

import java.util.UUID;

public record MatchView(
        UUID id,
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

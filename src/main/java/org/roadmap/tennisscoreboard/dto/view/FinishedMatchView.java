package org.roadmap.tennisscoreboard.dto.view;

import org.roadmap.tennisscoreboard.dto.PlayerDto;

import java.util.UUID;

public record FinishedMatchView(
        UUID id,
        PlayerDto firstPlayer,
        PlayerDto secondPlayer,
        PlayerDto winner,
        int firstPlayerFirstSet,
        int secondPlayerFirstSet,
        int firstPlayerSecondSet,
        int secondPlayerSecondSet,
        int firstPlayerThirdSet,
        int secondPlayerThirdSet
) {
}

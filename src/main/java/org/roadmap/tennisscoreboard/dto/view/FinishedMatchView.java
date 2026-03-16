package org.roadmap.tennisscoreboard.dto.view;

import org.roadmap.tennisscoreboard.dto.PlayerDto;

public record FinishedMatchView(
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

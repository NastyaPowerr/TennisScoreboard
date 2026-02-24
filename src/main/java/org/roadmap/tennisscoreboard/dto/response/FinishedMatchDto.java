package org.roadmap.tennisscoreboard.dto.response;

import org.roadmap.tennisscoreboard.dto.PlayerDto;

public record FinishedMatchDto(
        Integer id,
        PlayerDto firstPlayer,
        PlayerDto secondPlayer,
        PlayerDto winner
) {
}

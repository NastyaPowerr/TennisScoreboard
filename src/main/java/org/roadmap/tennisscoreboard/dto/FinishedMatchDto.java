package org.roadmap.tennisscoreboard.dto;

public record FinishedMatchDto(
        Integer id,
        PlayerDto firstPlayer,
        PlayerDto secondPlayer,
        PlayerDto winner
) {
}

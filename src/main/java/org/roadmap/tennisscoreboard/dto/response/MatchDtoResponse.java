package org.roadmap.tennisscoreboard.dto.response;

public record MatchDtoResponse (PlayerDtoResponse firstPlayer, PlayerDtoResponse secondPlayer, PlayerDtoResponse winner) {
}

package org.roadmap.dto.response;

public record MatchDtoResponse (PlayerDtoResponse firstPlayer, PlayerDtoResponse secondPlayer, PlayerDtoResponse winner) {
}

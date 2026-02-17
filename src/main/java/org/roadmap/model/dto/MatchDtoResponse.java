package org.roadmap.model.dto;

public record MatchDtoResponse (PlayerDtoResponse firstPlayer, PlayerDtoResponse secondPlayer, PlayerDtoResponse winner) {
}

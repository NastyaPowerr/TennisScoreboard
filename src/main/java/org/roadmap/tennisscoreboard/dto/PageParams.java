package org.roadmap.tennisscoreboard.dto;

public record PageParams(
        int pageNumber,
        int pageQuantity,
        String filterName,
        String errorMessage
) {
}
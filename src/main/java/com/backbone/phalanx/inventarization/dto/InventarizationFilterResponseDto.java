package com.backbone.phalanx.inventarization.dto;

import java.util.List;

public record InventarizationFilterResponseDto(
    int totalPages,
    int totalElements,
    int currentPage,
    List<InventarizationResponseDto> items
) {
}
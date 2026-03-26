package com.backbone.phalanx.inventarization.dto;

import com.backbone.phalanx.inventarization.model.InventarizationStatus;
import java.time.LocalDateTime;
import java.util.List;

public record InventarizationResponseDto(
    Long id,
    InventarizationStatus status,
    LocalDateTime startedAt,
    LocalDateTime completedAt,
    String conductedBy,
    String createdBy,
    String closedBy,
    List<InventarizationItemResponseDto> items,
    List<InventarizationItemResponseDto> discrepancies
) {
}

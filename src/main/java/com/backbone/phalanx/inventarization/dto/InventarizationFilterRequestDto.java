package com.backbone.phalanx.inventarization.dto;

import com.backbone.phalanx.inventarization.model.InventarizationStatus;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record InventarizationFilterRequestDto(
        Integer page,
        Integer pageSize,
        InventarizationStatus status,
        String conductedBy,
        LocalDateTime startedFrom,
        LocalDateTime startedTo,
        LocalDateTime completedFrom,
        LocalDateTime completedTo,
        String sortBy,
        String sortDirection) {
}
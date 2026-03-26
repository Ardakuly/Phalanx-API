package com.backbone.phalanx.inventarization.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InventarizationItemResponseDto(
    Long id,
    Long productId,
    String productName,
    BigDecimal expectedQuantity,
    BigDecimal countedQuantity,
    BigDecimal finalQuantity,
    BigDecimal difference,
    LocalDateTime updatedAt
) {
}

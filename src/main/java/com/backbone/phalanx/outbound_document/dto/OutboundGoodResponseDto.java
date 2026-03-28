package com.backbone.phalanx.outbound_document.dto;

import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record OutboundGoodResponseDto(
        String externalId,
        String name,
        String sku,
        String barcode,
        String unit,
        BigDecimal purchasedPrice,
        BigDecimal sellingPrice,
        BigDecimal quantity,
        String photoUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

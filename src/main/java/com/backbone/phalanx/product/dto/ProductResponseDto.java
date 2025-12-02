package com.backbone.phalanx.product.dto;

import com.backbone.phalanx.product.model.Category;
import com.backbone.phalanx.product.model.Unit;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDto(
        String externalId,
        String name,
        String sku,
        String barcode,
        String unit,
        Category category,
        BigDecimal purchasedPrice,
        BigDecimal sellingPrice,
        BigDecimal stockBalance,
        String photoUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
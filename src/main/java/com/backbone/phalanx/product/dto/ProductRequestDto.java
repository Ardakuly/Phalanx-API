package com.backbone.phalanx.product.dto;

import com.backbone.phalanx.product.model.Unit;

import java.math.BigDecimal;

public record ProductRequestDto(
        String externalId,
        String name,
        String sku,
        String barcode,
        Unit unit,
        String category,
        BigDecimal purchasedPrice,
        BigDecimal sellingPrice,
        BigDecimal stockBalance,
        String photoUrl
) {
}

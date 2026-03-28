package com.backbone.phalanx.good_return_document.dto;

import com.backbone.phalanx.product.model.Unit;

import java.math.BigDecimal;

public record ReturnedGoodResponseDto(
        String externalId,
        String name,
        String barcode,
        Unit unit,
        String categoryName,
        BigDecimal purchasedPrice,
        BigDecimal sellingPrice,
        BigDecimal quantity
) {
}

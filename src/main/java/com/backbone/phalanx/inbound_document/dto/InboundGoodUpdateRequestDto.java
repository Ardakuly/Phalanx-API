package com.backbone.phalanx.inbound_document.dto;

import com.backbone.phalanx.product.model.Unit;

import java.math.BigDecimal;

public record InboundGoodUpdateRequestDto(
        String externalId,
        BigDecimal purchasedPrice,
        BigDecimal sellingPrice,
        BigDecimal quantity,
        Unit unit,
        String name,
        String category
) {
}
package com.backbone.phalanx.product.dto;

import java.math.BigDecimal;

public record ProductSellDto(
        String externalId,
        String barcode,
        BigDecimal quantity
) {

}

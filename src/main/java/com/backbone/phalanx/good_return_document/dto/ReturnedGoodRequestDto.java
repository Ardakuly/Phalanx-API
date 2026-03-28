package com.backbone.phalanx.good_return_document.dto;

import java.math.BigDecimal;

public record ReturnedGoodRequestDto(
        String barcode,
        BigDecimal quantity
) {
}

package com.backbone.phalanx.outbound_document.dto;

import com.backbone.phalanx.outbound_document.model.PaymentType;
import com.backbone.phalanx.product.dto.ProductSellDto;

import java.util.List;

public record OutboundDocumentDto(
        List<ProductSellDto> products,
        PaymentType paymentType,
        String comment
) {
}
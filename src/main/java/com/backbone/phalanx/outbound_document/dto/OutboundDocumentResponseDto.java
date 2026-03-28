package com.backbone.phalanx.outbound_document.dto;

import com.backbone.phalanx.outbound_document.model.PaymentType;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OutboundDocumentResponseDto(
        String externalId,
        String documentNumber,
        PaymentType paymentType,
        String seller,
        String comment,
        List<OutboundGoodResponseDto> outboundGoods,
        Double totalIncome,
        Double margin,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

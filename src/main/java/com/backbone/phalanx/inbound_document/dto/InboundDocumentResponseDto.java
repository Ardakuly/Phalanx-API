package com.backbone.phalanx.inbound_document.dto;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record InboundDocumentResponseDto(
        String externalId,
        String documentNumber,
        List<InboundGoodResponseDto> inboundGoods,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

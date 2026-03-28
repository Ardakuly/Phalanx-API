package com.backbone.phalanx.good_return_document.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record GoodReturnDocumentResponseDto(
        String externalId,
        String documentNumber,
        String outboundDocumentExternalId,
        BigDecimal refundAmount,
        String comment,
        List<ReturnedGoodResponseDto> returnedGoods,
        LocalDateTime createdAt
) {
}

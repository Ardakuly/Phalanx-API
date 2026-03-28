package com.backbone.phalanx.good_return_document.dto;

import java.util.List;

public record GoodReturnDocumentRequestDto(
        String outboundDocumentNumber,
        String comment,
        List<ReturnedGoodRequestDto> goods
) {
}

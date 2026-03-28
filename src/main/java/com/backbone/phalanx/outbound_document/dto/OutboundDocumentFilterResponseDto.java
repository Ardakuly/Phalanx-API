package com.backbone.phalanx.outbound_document.dto;

import java.util.List;

public record OutboundDocumentFilterResponseDto(
        int totalPages,
        int totalElements,
        int currentPage,
        List<OutboundDocumentResponseDto> outboundDocuments
) {
}

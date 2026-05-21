package com.backbone.phalanx.inbound_document.dto;

import java.util.List;

public record InboundDocumentFilterResponseDto(
        int totalPages,
        long totalElements,
        int currentPage,
        List<InboundDocumentResponseDto> inboundDocuments
) {
}

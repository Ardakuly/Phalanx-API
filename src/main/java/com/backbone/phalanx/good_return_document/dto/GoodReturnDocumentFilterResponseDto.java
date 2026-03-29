package com.backbone.phalanx.good_return_document.dto;

import java.util.List;

public record GoodReturnDocumentFilterResponseDto(
        int totalPages,
        int totalElements,
        int currentPage,
        List<GoodReturnDocumentResponseDto> goodReturnDocuments
) {
}
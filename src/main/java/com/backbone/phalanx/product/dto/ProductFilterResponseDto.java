package com.backbone.phalanx.product.dto;

import java.util.List;

public record ProductFilterResponseDto(
        int totalPages,
        int totalElements,
        int currentPage,
        List<ProductResponseDto> products
) {
}

package com.backbone.phalanx.product.dto;

import com.backbone.phalanx.product.model.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductFilterRequestDto(
        Integer page,
        Integer pageSize,
        String search,
        Category category,
        BigDecimal priceFrom,
        BigDecimal priceTo,
        BigDecimal stockBalanceFrom,
        BigDecimal stockBalanceTo,
        LocalDateTime createdFrom,
        LocalDateTime createdTo,
        String sortBy,
        String sortDirection
) {
}

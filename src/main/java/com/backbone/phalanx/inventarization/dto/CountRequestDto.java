package com.backbone.phalanx.inventarization.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CountRequestDto(
    @NotNull(message = "Product ID is mandatory")
    Long productId,
    
    @NotNull(message = "Counted quantity is mandatory")
    BigDecimal countedQuantity
) {
}

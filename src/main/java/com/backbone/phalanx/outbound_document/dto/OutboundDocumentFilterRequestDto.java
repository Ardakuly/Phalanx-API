package com.backbone.phalanx.outbound_document.dto;

import com.backbone.phalanx.outbound_document.model.PaymentType;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record OutboundDocumentFilterRequestDto(
                Integer page,
                Integer pageSize,
                String search,
                PaymentType paymentType,
                LocalDateTime createdFrom,
                LocalDateTime createdTo,
                String sortBy,
                String sortDirection
) {
}

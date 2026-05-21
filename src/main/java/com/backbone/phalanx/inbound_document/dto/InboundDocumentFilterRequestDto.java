package com.backbone.phalanx.inbound_document.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record InboundDocumentFilterRequestDto(
                Integer page,
                Integer pageSize,
                String search,
                LocalDateTime createdFrom,
                LocalDateTime createdTo,
                String sortBy,
                String sortDirection) {
}
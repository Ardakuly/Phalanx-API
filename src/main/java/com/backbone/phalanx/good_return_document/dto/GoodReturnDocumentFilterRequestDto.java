package com.backbone.phalanx.good_return_document.dto;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record GoodReturnDocumentFilterRequestDto(
                Integer page,
                Integer pageSize,
                String search,
                LocalDateTime createdFrom,
                LocalDateTime createdTo,
                String sortBy,
                String sortDirection
) {
}
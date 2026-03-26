package com.backbone.phalanx.inventarization.mapper;

import com.backbone.phalanx.inventarization.dto.InventarizationItemResponseDto;
import com.backbone.phalanx.inventarization.dto.InventarizationResponseDto;
import com.backbone.phalanx.inventarization.model.Inventarization;
import com.backbone.phalanx.inventarization.model.InventarizationItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface InventarizationMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    InventarizationItemResponseDto toItemDto(InventarizationItem entity);

    default InventarizationResponseDto toDto(Inventarization inventarization, List<InventarizationItem> items) {
        List<InventarizationItemResponseDto> itemDtos = items.stream().map(this::toItemDto).toList();

        List<InventarizationItemResponseDto> discrepancies = itemDtos.stream()
                .filter(item -> item.difference() != null && item.difference().compareTo(BigDecimal.ZERO) != 0)
                .toList();

        return new InventarizationResponseDto(
                inventarization.getId(),
                inventarization.getStatus(),
                inventarization.getStartedAt(),
                inventarization.getCompletedAt(),
                inventarization.getConductedBy(),
                inventarization.getCreatedBy(),
                inventarization.getClosedBy(),
                itemDtos,
                discrepancies
        );
    }
}

package com.backbone.phalanx.product.mapper;

import com.backbone.phalanx.product.dto.ProductResponseDto;
import com.backbone.phalanx.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "unit", source = "unit.name")
    ProductResponseDto toDto(Product entity);

}

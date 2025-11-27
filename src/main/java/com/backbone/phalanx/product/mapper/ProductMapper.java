package com.backbone.phalanx.product.mapper;

import com.backbone.phalanx.product.dto.ProductRequestDto;
import com.backbone.phalanx.product.dto.ProductResponseDto;
import com.backbone.phalanx.product.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDto toDto(Product entity);

}

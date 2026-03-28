package com.backbone.phalanx.outbound_document.mapper;

import com.backbone.phalanx.outbound_document.dto.OutboundDocumentResponseDto;
import com.backbone.phalanx.outbound_document.dto.OutboundGoodResponseDto;
import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import com.backbone.phalanx.outbound_document.model.OutboundGood;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OutboundDocumentMapper {

    @Mapping(target = "seller", source = "seller.email")
    @Mapping(target = "totalIncome", expression = "java(entity.getTotalIncome())")
    @Mapping(target = "margin", expression = "java(entity.getMargin())")
    OutboundDocumentResponseDto toDto(OutboundDocument entity);

    @Mapping(target = "unit", source = "unit.name")
    OutboundGoodResponseDto toGoodDto(OutboundGood entity);
}
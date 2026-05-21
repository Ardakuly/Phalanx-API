package com.backbone.phalanx.inbound_document.mapper;

import com.backbone.phalanx.inbound_document.dto.InboundDocumentResponseDto;
import com.backbone.phalanx.inbound_document.dto.InboundGoodResponseDto;
import com.backbone.phalanx.inbound_document.model.InboundDocument;
import com.backbone.phalanx.inbound_document.model.InboundGood;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InboundDocumentMapper {

    InboundDocumentResponseDto toDto(InboundDocument entity);

    @Mapping(target = "unit", source = "unit.name")
    InboundGoodResponseDto toGoodDto(InboundGood entity);
}

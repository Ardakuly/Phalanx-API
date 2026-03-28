package com.backbone.phalanx.good_return_document.mapper;

import com.backbone.phalanx.good_return_document.dto.GoodReturnDocumentResponseDto;
import com.backbone.phalanx.good_return_document.dto.ReturnedGoodResponseDto;
import com.backbone.phalanx.good_return_document.model.GoodReturnDocument;
import com.backbone.phalanx.good_return_document.model.ReturnedGood;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GoodReturnDocumentMapper {

    @Mapping(target = "outboundDocumentExternalId", source = "outboundDocument.externalId")
    GoodReturnDocumentResponseDto toDto(GoodReturnDocument entity);

    @Mapping(target = "categoryName", source = "category.name")
    ReturnedGoodResponseDto toReturnedGoodDto(ReturnedGood entity);
}

package com.backbone.phalanx.inbound_document.service;

import com.backbone.phalanx.inbound_document.model.InboundDocument;
import com.backbone.phalanx.product.dto.ProductRequestDto;

import java.util.List;

public interface InboundDocumentService {

    // TODO: Generate information
    InboundDocument creatInboundDocument(List<ProductRequestDto> products);
}

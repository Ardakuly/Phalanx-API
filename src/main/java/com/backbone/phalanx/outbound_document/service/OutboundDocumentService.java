package com.backbone.phalanx.outbound_document.service;

import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import com.backbone.phalanx.product.dto.ProductSellDto;

import java.util.List;

public interface OutboundDocumentService {

    // TODO: Generate information
    OutboundDocument createOutboundDocument(List<ProductSellDto> products);
}

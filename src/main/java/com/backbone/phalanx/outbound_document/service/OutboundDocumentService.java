package com.backbone.phalanx.outbound_document.service;

import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import com.backbone.phalanx.product.dto.ProductSellDto;

import java.util.List;

public interface OutboundDocumentService {

    /**
     * Creates a new outbound document based on the provided list of products to be sold.
     *
     * @param products the list of products to be included in the outbound document, each represented
     *                 by a ProductSellDto containing details such as external ID, barcode, and quantity.
     * @return the newly created OutboundDocument containing details of the sold products.
     */
    OutboundDocument createOutboundDocument(List<ProductSellDto> products);
}

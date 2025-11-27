package com.backbone.phalanx.inbound_document.service;

import com.backbone.phalanx.inbound_document.model.InboundDocument;
import com.backbone.phalanx.inbound_document.model.InboundGood;
import com.backbone.phalanx.product.model.Product;

public interface InboundGoodService {

    /**
     * Creates a new InboundGood entity based on the provided product data.
     *
     * @param productInStock the product in stock to be used for creating the inbound good
     * @return the created InboundGood entity
     */
    InboundGood createInboundGood(InboundDocument inboundDocument, Product productInStock);
}

package com.backbone.phalanx.inbound_document.service;

import com.backbone.phalanx.inbound_document.model.InboundDocument;
import com.backbone.phalanx.inbound_document.model.InboundGood;
import com.backbone.phalanx.product.model.Product;

import com.backbone.phalanx.inbound_document.dto.InboundGoodUpdateRequestDto;
import com.backbone.phalanx.inbound_document.dto.InboundGoodResponseDto;
import com.backbone.phalanx.product.model.Product;

public interface InboundGoodService {

    /**
     * Creates a new InboundGood entity based on the provided product data.
     *
     * @param inboundDocument the inbound document to which the good belongs
     * @param productInStock the product in stock to be used for creating the inbound good
     * @return the created InboundGood entity
     */
    InboundGood createInboundGood(InboundDocument inboundDocument, Product productInStock);

    /**
     * Updates an existing InboundGood and recalculates the associated Product's total and stock.
     *
     * @param request the updated good information
     * @return the updated InboundGood dto
     */
    InboundGoodResponseDto updateInboundGood(InboundGoodUpdateRequestDto request);
}

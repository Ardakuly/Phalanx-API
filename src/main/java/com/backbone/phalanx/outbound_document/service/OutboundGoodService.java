package com.backbone.phalanx.outbound_document.service;

import com.backbone.phalanx.outbound_document.model.OutboundGood;
import com.backbone.phalanx.product.dto.ProductSellDto;
import com.backbone.phalanx.product.model.Product;

public interface OutboundGoodService {

    // TODO: Generate information
    OutboundGood createOutboundGood(ProductSellDto productToSell, Product productInStock);
}

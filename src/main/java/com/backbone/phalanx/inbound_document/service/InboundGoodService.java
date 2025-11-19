package com.backbone.phalanx.inbound_document.service;

import com.backbone.phalanx.inbound_document.model.InboundGood;
import com.backbone.phalanx.product.model.Product;

public interface InboundGoodService {

    // TODO: Generate information
    InboundGood createInboundGood(Product productInStock);

}

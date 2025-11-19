package com.backbone.phalanx.outbound_document.service;

import com.backbone.phalanx.outbound_document.model.OutboundGood;
import com.backbone.phalanx.product.dto.ProductSellDto;
import com.backbone.phalanx.product.model.Product;

public interface OutboundGoodService {

    /**
     * Creates a new OutboundGood instance based on the provided product to sell and
     * the corresponding product in stock.
     *
     * @param productToSell the details of the product being sold, including external ID, barcode, and quantity.
     * @param productInStock the existing product in stock that corresponds to the product being sold.
     * @return the created OutboundGood instance representing the sold product.
     */
    OutboundGood createOutboundGood(ProductSellDto productToSell, Product productInStock);
}

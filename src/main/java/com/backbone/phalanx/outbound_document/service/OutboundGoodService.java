package com.backbone.phalanx.outbound_document.service;

import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import com.backbone.phalanx.outbound_document.model.OutboundGood;
import com.backbone.phalanx.product.dto.ProductSellDto;
import com.backbone.phalanx.product.model.Product;

public interface OutboundGoodService {

    /**
     * Creates an OutboundGood entity representing a product that has been sold or is ready for shipment.
     *
     * @param outboundDocument the associated OutboundDocument containing details of the overall outbound transaction.
     * @param productToSell the ProductSellDto containing details such as external ID, barcode, and quantity to sell.
     * @param productInStock the Product entity representing the product available in stock.
     * @return the newly created OutboundGood entity containing details related to the product and its outbound transaction.
     */
    OutboundGood createOutboundGood(
            OutboundDocument outboundDocument, ProductSellDto productToSell, Product productInStock
    );
}

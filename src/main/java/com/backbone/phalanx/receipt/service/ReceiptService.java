package com.backbone.phalanx.receipt.service;

import com.backbone.phalanx.outbound_document.model.OutboundDocument;

public interface ReceiptService {
    /**
     * Prints a receipt for the given outbound document to a connected USB printer.
     *
     * @param document the outbound document containing sale details.
     */
    void printReceipt(OutboundDocument document);
}

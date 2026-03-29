package com.backbone.phalanx.receipt.service;

import com.backbone.phalanx.outbound_document.model.OutboundDocument;

public interface ReceiptService {
    /**
     * Prints a receipt for the given outbound document to a connected USB printer.
     *
     * @param document the outbound document containing sale details.
     */
    void printReceipt(OutboundDocument document);

    /**
     * Prints a return receipt for the given good return document to a connected USB printer.
     *
     * @param document the good return document containing return details.
     */
    void printReturnReceipt(com.backbone.phalanx.good_return_document.model.GoodReturnDocument document);
}
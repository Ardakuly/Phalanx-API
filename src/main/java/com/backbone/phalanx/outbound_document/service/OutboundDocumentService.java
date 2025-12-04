package com.backbone.phalanx.outbound_document.service;

import com.backbone.phalanx.outbound_document.dto.OutboundDocumentDto;
import com.backbone.phalanx.outbound_document.model.OutboundDocument;

import java.time.LocalDateTime;
import java.util.List;

public interface OutboundDocumentService {

    /**
     * Retrieves a list of outbound documents that were created within the specified time interval.
     *
     * @param from the starting point of the interval (inclusive) as a LocalDateTime.
     * @param to the ending point of the interval (inclusive) as a LocalDateTime.
     * @return a list of OutboundDocument entities created within the given interval.
     */
    List<OutboundDocument> getAllOutboundDocumentByInterval(LocalDateTime from, LocalDateTime to);

    /**
     * Creates and stores an outbound document representing a transaction of sold products.
     *
     * @param outboundDocumentDto the data transfer object containing details of the products being sold,
     *                            payment type, and an optional comment associated with the transaction.
     * @param sellerEmail the email address of the seller initiating the transaction.
     * @return the created {@link OutboundDocument} entity containing details of the transaction,
     *         including seller information and associated products.
     */
    OutboundDocument createOutboundDocument(OutboundDocumentDto outboundDocumentDto, String sellerEmail);
}

package com.backbone.phalanx.outbound_document.service;

import com.backbone.phalanx.outbound_document.dto.OutboundDocumentDto;
import com.backbone.phalanx.outbound_document.model.OutboundDocument;

import java.time.LocalDateTime;
import java.util.List;

import com.backbone.phalanx.outbound_document.dto.OutboundDocumentFilterRequestDto;
import com.backbone.phalanx.outbound_document.dto.OutboundDocumentFilterResponseDto;

public interface OutboundDocumentService {

    /**
     * Retrieves a filtered and paginated list of outbound documents based on specified criteria.
     *
     * @param filter the filtering and pagination criteria
     * @return a wrapped response containing the current page's results and metadata
     */
    OutboundDocumentFilterResponseDto getAllOutboundDocumentsByFiltering(OutboundDocumentFilterRequestDto filter);

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

    /**
     * Finds an outbound document by its unique document number.
     *
     * @param documentNumber the document number to search for
     * @return the found OutboundDocument entity
     * @throws RuntimeException if the document is not found
     */
    OutboundDocument findByDocumentNumber(String documentNumber);
}

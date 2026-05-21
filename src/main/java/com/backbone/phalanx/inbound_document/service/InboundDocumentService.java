package com.backbone.phalanx.inbound_document.service;

import com.backbone.phalanx.inbound_document.model.InboundDocument;
import com.backbone.phalanx.inbound_document.dto.InboundDocumentFilterRequestDto;
import com.backbone.phalanx.inbound_document.dto.InboundDocumentFilterResponseDto;
import com.backbone.phalanx.product.dto.ProductRequestDto;

import java.util.List;

public interface InboundDocumentService {

    /**
     * Creates a new InboundDocument based on the provided list of products.
     *
     * @param products a list of ProductRequestDto objects containing information about the products to include in the new inbound document
     * @return the created InboundDocument containing relevant details of the operation
     */
    InboundDocument creatInboundDocument(List<ProductRequestDto> products);

    /**
     * Retrieves all inbound documents based on filtering, sorting and pagination.
     *
     * @param filter request containing filter criteria, sorting, and pagination details
     * @return response containing paginated inbound documents
     */
    InboundDocumentFilterResponseDto getAllInboundDocumentsByFiltering(InboundDocumentFilterRequestDto filter);
}

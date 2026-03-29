package com.backbone.phalanx.good_return_document.service;

import com.backbone.phalanx.good_return_document.dto.GoodReturnDocumentFilterRequestDto;
import com.backbone.phalanx.good_return_document.dto.GoodReturnDocumentFilterResponseDto;
import com.backbone.phalanx.good_return_document.dto.GoodReturnDocumentRequestDto;
import com.backbone.phalanx.good_return_document.dto.GoodReturnDocumentResponseDto;

public interface GoodReturnDocumentService {

    /**
     * Creates a new good return document based on the provided request.
     * 
     * @param request The request containing the details of the good return document
     *                to be created.
     * @return The created good return document.
     */
    GoodReturnDocumentResponseDto createGoodReturnDocument(GoodReturnDocumentRequestDto request);

    /**
     * Retrieves all good return documents that match the provided filtering
     * criteria.
     * 
     * @param filter The filtering criteria to apply.
     * @return The filtered and paginated good return documents.
     */
    GoodReturnDocumentFilterResponseDto getAllGoodReturnDocumentsByFiltering(GoodReturnDocumentFilterRequestDto filter);
}

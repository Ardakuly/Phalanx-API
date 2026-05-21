package com.backbone.phalanx.inbound_document.controller;

import com.backbone.phalanx.inbound_document.dto.InboundDocumentFilterRequestDto;
import com.backbone.phalanx.inbound_document.dto.InboundDocumentFilterResponseDto;
import com.backbone.phalanx.inbound_document.dto.InboundGoodResponseDto;
import com.backbone.phalanx.inbound_document.dto.InboundGoodUpdateRequestDto;
import com.backbone.phalanx.inbound_document.model.InboundDocument;
import com.backbone.phalanx.inbound_document.service.InboundDocumentService;
import com.backbone.phalanx.inbound_document.service.InboundGoodService;
import com.backbone.phalanx.product.dto.ProductRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/inbound-document")
@Tag(name = "Inbound Document for Admin", description = "Administration operations for Inbound Document")
@RequiredArgsConstructor
@Slf4j
public class InboundDocumentController {

    private final InboundDocumentService inboundDocumentService;
    private final InboundGoodService inboundGoodService;

    @PostMapping("/add")
    @Operation(summary = "Add inbound document", description = "Post request to create an inbound document using a list of products")
    public ResponseEntity<InboundDocument> add(@RequestBody List<ProductRequestDto> productRequestDto) {
        InboundDocument inboundDocument = inboundDocumentService.creatInboundDocument(productRequestDto);
        return ResponseEntity.ok().body(inboundDocument);
    }

    @PostMapping("/search")
    @Operation(summary = "Retrieve all inbound documents", description = "Post request to retrieve all inbound documents with pagination and filtering")
    public InboundDocumentFilterResponseDto getAllInboundDocumentsByFiltering(
            @RequestBody InboundDocumentFilterRequestDto inboundDocumentFilterRequestDto) {
        return inboundDocumentService.getAllInboundDocumentsByFiltering(inboundDocumentFilterRequestDto);
    }

    @PutMapping("/update")
    @Operation(
            summary = "Update an inbound document good", description = "Updates an inbound good and recalculates product totals"
    )
    public ResponseEntity<InboundGoodResponseDto> updateGood(@RequestBody InboundGoodUpdateRequestDto request) {
        InboundGoodResponseDto response = inboundGoodService.updateInboundGood(request);
        return ResponseEntity.ok(response);
    }
}

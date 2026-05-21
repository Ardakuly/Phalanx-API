package com.backbone.phalanx.inbound_document.controller;

import com.backbone.phalanx.inbound_document.model.InboundDocument;
import com.backbone.phalanx.inbound_document.service.InboundDocumentService;
import com.backbone.phalanx.product.dto.ProductRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backbone.phalanx.inbound_document.dto.InboundDocumentFilterRequestDto;
import com.backbone.phalanx.inbound_document.dto.InboundDocumentFilterResponseDto;

import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/inbound-document")
@Tag(name = "Inbound Document for Admin", description = "Administration operations for Inbound Document")
@RequiredArgsConstructor
@Slf4j
public class InboundDocumentController {

    private final InboundDocumentService inboundDocumentService;

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
}

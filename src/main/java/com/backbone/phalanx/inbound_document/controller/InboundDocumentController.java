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

import java.util.List;

@RestController
@RequestMapping(value = "/api/public/admin/inbound_document")
@Tag(name = "Inbound Document for Admin", description = "Administration operations for Inbound Document")
@RequiredArgsConstructor
@Slf4j
public class InboundDocumentController {

    private final InboundDocumentService inboundDocumentService;

    @PostMapping("/add")
    @Operation(summary = "", description = "Post request to add the product")
    public ResponseEntity<InboundDocument> add(@RequestBody List<ProductRequestDto> productRequestDto) {
        InboundDocument inboundDocument = inboundDocumentService.creatInboundDocument(productRequestDto);
        return ResponseEntity.ok().body(inboundDocument);
    }
}

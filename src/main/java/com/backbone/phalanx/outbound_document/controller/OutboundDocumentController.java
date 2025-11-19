package com.backbone.phalanx.outbound_document.controller;

import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import com.backbone.phalanx.outbound_document.service.OutboundDocumentService;
import com.backbone.phalanx.product.dto.ProductSellDto;
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
@RequestMapping(value = "/api/public/admin/outbound_document")
@Tag(name = "Outbound Document for Admin", description = "Administration operations for Outbound Document")
@RequiredArgsConstructor
@Slf4j
public class OutboundDocumentController {

    private final OutboundDocumentService outboundDocumentService;

    @PostMapping("/sell")
    @Operation(summary = "Sell products", description = "Post request to sell products")
    public ResponseEntity<OutboundDocument> sell(@RequestBody List<ProductSellDto> products) {
        OutboundDocument outboundDocument = outboundDocumentService.createOutboundDocument(products);
        return ResponseEntity.ok().body(outboundDocument);
    }
}

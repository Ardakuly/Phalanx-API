package com.backbone.phalanx.good_return_document.controller;

import com.backbone.phalanx.good_return_document.dto.GoodReturnDocumentFilterRequestDto;
import com.backbone.phalanx.good_return_document.dto.GoodReturnDocumentFilterResponseDto;
import com.backbone.phalanx.good_return_document.dto.GoodReturnDocumentRequestDto;
import com.backbone.phalanx.good_return_document.dto.GoodReturnDocumentResponseDto;
import com.backbone.phalanx.good_return_document.service.GoodReturnDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/good-return-documents")
@Tag(name = "Good Return Document for Admin", description = "Administration operations for Good Return Document")
@RequiredArgsConstructor
public class GoodReturnDocumentController {

    private final GoodReturnDocumentService goodReturnDocumentService;

    @PostMapping
    @Operation(summary = "Retrieve all good return documents", description = "Post request to retrieve all good return documents")
    public GoodReturnDocumentFilterResponseDto getAllGoodReturnDocumentsByFiltering(
            @RequestBody GoodReturnDocumentFilterRequestDto filter
    ) {
        return goodReturnDocumentService.getAllGoodReturnDocumentsByFiltering(filter);
    }

    @PostMapping("/create")
    @Operation(summary = "Create good return document", description = "Post request to create a good return document")
    public ResponseEntity<GoodReturnDocumentResponseDto> createGoodReturnDocument(
            @RequestBody GoodReturnDocumentRequestDto request
    ) {
        return ResponseEntity.ok(goodReturnDocumentService.createGoodReturnDocument(request));
    }
}

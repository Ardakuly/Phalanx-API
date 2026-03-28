package com.backbone.phalanx.good_return_document.controller;

import com.backbone.phalanx.good_return_document.dto.GoodReturnDocumentRequestDto;
import com.backbone.phalanx.good_return_document.dto.GoodReturnDocumentResponseDto;
import com.backbone.phalanx.good_return_document.service.GoodReturnDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/good-return-documents")
@RequiredArgsConstructor
public class GoodReturnDocumentController {

    private final GoodReturnDocumentService goodReturnDocumentService;

    @PostMapping
    public ResponseEntity<GoodReturnDocumentResponseDto> createGoodReturnDocument(
            @RequestBody GoodReturnDocumentRequestDto request) {
        return ResponseEntity.ok(goodReturnDocumentService.createGoodReturnDocument(request));
    }
}

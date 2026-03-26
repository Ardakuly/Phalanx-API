package com.backbone.phalanx.inventarization.controller;

import com.backbone.phalanx.inventarization.dto.CountRequestDto;
import com.backbone.phalanx.inventarization.dto.InventarizationResponseDto;
import com.backbone.phalanx.inventarization.service.InventarizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventarization")
@RequiredArgsConstructor
public class InventarizationController {

    private final InventarizationService inventarizationService;

    @PostMapping("/start")
    public ResponseEntity<InventarizationResponseDto> start() {
        return ResponseEntity.ok(inventarizationService.start());
    }

    @PostMapping("/{id}/count")
    public ResponseEntity<InventarizationResponseDto> count(
            @PathVariable Long id,
            @Valid @RequestBody CountRequestDto countRequestDto) {
        return ResponseEntity.ok(inventarizationService.count(id, countRequestDto));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<InventarizationResponseDto> complete(@PathVariable Long id) {
        return ResponseEntity.ok(inventarizationService.complete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarizationResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(inventarizationService.getById(id));
    }
}
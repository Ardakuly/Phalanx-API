package com.backbone.phalanx.inventarization.controller;

import com.backbone.phalanx.inventarization.dto.CountRequestDto;
import com.backbone.phalanx.inventarization.dto.InventarizationFilterRequestDto;
import com.backbone.phalanx.inventarization.dto.InventarizationFilterResponseDto;
import com.backbone.phalanx.inventarization.dto.InventarizationResponseDto;
import com.backbone.phalanx.inventarization.service.InventarizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventarization")
@RequiredArgsConstructor
public class InventarizationController {

    private final InventarizationService inventarizationService;

    @PostMapping
    public ResponseEntity<InventarizationFilterResponseDto> getFiltered(
            @RequestBody InventarizationFilterRequestDto filter) {
        return ResponseEntity.ok(inventarizationService.getFiltered(filter));
    }

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

    @GetMapping("/active")
    public ResponseEntity<InventarizationResponseDto> getActive() {
        return ResponseEntity.ok(inventarizationService.getActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarizationResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(inventarizationService.getById(id));
    }
}
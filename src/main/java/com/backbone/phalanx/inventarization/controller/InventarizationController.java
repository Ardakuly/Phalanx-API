package com.backbone.phalanx.inventarization.controller;

import com.backbone.phalanx.inventarization.dto.CountRequestDto;
import com.backbone.phalanx.inventarization.dto.InventarizationFilterRequestDto;
import com.backbone.phalanx.inventarization.dto.InventarizationFilterResponseDto;
import com.backbone.phalanx.inventarization.dto.InventarizationResponseDto;
import com.backbone.phalanx.inventarization.service.InventarizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventarization")
@Tag(name = "Inventarization", description = "Operations related to stock checking")
@RequiredArgsConstructor
public class InventarizationController {

    private final InventarizationService inventarizationService;

    @PostMapping
    @Operation(summary = "Get filtered inventarizations", description = "Retrieves a paginated list of inventarizations based on filter criteria")
    public ResponseEntity<InventarizationFilterResponseDto> getFiltered(
            @RequestBody InventarizationFilterRequestDto filter) {
        return ResponseEntity.ok(inventarizationService.getFiltered(filter));
    }

    @PostMapping("/start")
    @Operation(summary = "Start inventarization", description = "Starts a new inventarization process")
    public ResponseEntity<InventarizationResponseDto> start() {
        return ResponseEntity.ok(inventarizationService.start());
    }

    @PostMapping("/{id}/count")
    @Operation(summary = "Register count", description = "Registers a product count for a specific inventarization")
    public ResponseEntity<InventarizationResponseDto> count(
            @PathVariable Long id,
            @Valid @RequestBody CountRequestDto countRequestDto) {
        return ResponseEntity.ok(inventarizationService.count(id, countRequestDto));
    }

    @PostMapping("/{id}/complete")
    @Operation(summary = "Complete inventarization", description = "Completes an active inventarization process")
    public ResponseEntity<InventarizationResponseDto> complete(@PathVariable Long id) {
        return ResponseEntity.ok(inventarizationService.complete(id));
    }

    @GetMapping("/active")
    @Operation(summary = "Get active inventarization", description = "Retrieves the currently active inventarization if any")
    public ResponseEntity<InventarizationResponseDto> getActive() {
        return ResponseEntity.ok(inventarizationService.getActive());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get inventarization by ID", description = "Retrieves details of a specific inventarization")
    public ResponseEntity<InventarizationResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(inventarizationService.getById(id));
    }
}
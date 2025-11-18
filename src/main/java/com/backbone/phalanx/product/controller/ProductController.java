package com.backbone.phalanx.product.controller;

import com.backbone.phalanx.product.dto.*;
import com.backbone.phalanx.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/public/product")
@Tag(name = "Product", description = "Operations with products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Retrieve all products", description = "Get request to retrieve all products")
    public ProductFilterResponseDto getAllProductsByFiltering(
            @RequestBody ProductFilterRequestDto productFilterRequestDto
    ) {
        return productService.getAllProductsByFiltering(productFilterRequestDto);
    }

    @GetMapping
    @Operation(summary = "Retrieve product by barcode", description = "Get request to retrieve product by barcode")
    public ProductResponseDto getProductByBarcode(String barcode) {
        return productService.getProductByBarcode(barcode);
    }

    @PostMapping
    @Operation(summary = "Sell products", description = "Post request to sell products")
    public ResponseEntity<Void> sell(@RequestBody List<ProductSellDto> products) {
        return ResponseEntity.ok().build();
    }
}

package com.backbone.phalanx.product.controller;

import com.backbone.phalanx.product.dto.ProductFilterRequestDto;
import com.backbone.phalanx.product.dto.ProductFilterResponseDto;
import com.backbone.phalanx.product.dto.ProductResponseDto;
import com.backbone.phalanx.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/product")
@Tag(name = "Product", description = "Operations with products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Retrieve all products", description = "Get request to retrieve all products")
    public ProductFilterResponseDto getAllProductsByFiltering(
            @RequestBody ProductFilterRequestDto productFilterRequestDto
    ) {
        return productService.getAllProductsByFiltering(productFilterRequestDto);
    }

    @GetMapping("/barcode/{barcode}")
    @Operation(summary = "Retrieve product by barcode", description = "Get request to retrieve product by barcode")
    public ProductResponseDto getProductByBarcode(@PathVariable("barcode") String barcode) {
        return productService.getProductByBarcode(barcode);
    }
}

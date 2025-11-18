package com.backbone.phalanx.product.controller;

import com.backbone.phalanx.product.dto.ProductFilterRequestDto;
import com.backbone.phalanx.product.dto.ProductFilterResponseDto;
import com.backbone.phalanx.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

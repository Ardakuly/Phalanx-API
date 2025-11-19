package com.backbone.phalanx.product.controller;


import com.backbone.phalanx.product.dto.ProductRequestDto;
import com.backbone.phalanx.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/public/admin/product")
@Tag(name = "Product for Admin", description = "Administration operations with products")
@RequiredArgsConstructor
@Slf4j
public class ProductAdminController {

    private final ProductService productService;

    @PutMapping("/update")
    @Operation(
            summary = "Update information of the product",
            description = "Put request to update information of the product"
    )
    public ResponseEntity<Void> update(@RequestBody ProductRequestDto productRequestDto) {
        productService.updateProduct(productRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/remove/{externalId}")
    @Operation(summary = "Remove the product", description = "Post request to remove the product")
    public ResponseEntity<Void> remove(@PathVariable("externalId") String externalId) {
        productService.deleteProduct(externalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

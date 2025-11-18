package com.backbone.phalanx.product.service;

import com.backbone.phalanx.product.dto.ProductFilterRequestDto;
import com.backbone.phalanx.product.dto.ProductFilterResponseDto;
import com.backbone.phalanx.product.dto.ProductRequestDto;

import java.math.BigDecimal;

public interface ProductService {

    /**
     * Retrieves a filtered list of products based on the specified criteria.
     *
     * @param productFilterRequestDto the data transfer object containing the filtering criteria,
     *                                including pagination, search parameters, category, price range,
     *                                stock balance range, creation date range, and sorting options.
     * @return a data transfer object containing the filtered list of products,
     *         along with pagination information such as total pages, total elements, and current page.
     */
    ProductFilterResponseDto getAllProductsByFiltering(ProductFilterRequestDto productFilterRequestDto);

    /**
     * Creates a new product based on the provided product data.
     *
     * @param productRequestDto the data transfer object containing details of the product to be created.
     *                          It includes information such as the external ID, name, SKU, barcode, unit,
     *                          category, purchased price, selling price, stock balance, and photo URL.
     */
    void createProduct(ProductRequestDto productRequestDto);

    /**
     * Updates an existing product based on the provided product data.
     *
     * @param productRequestDto the data transfer object containing the updated details of the product.
     *                          It includes information such as the external ID, name, SKU, barcode, unit,
     *                          category, purchased price, selling price, stock balance, and photo URL.
     */
    void updateProduct(ProductRequestDto productRequestDto);

    /**
     * Deletes the product identified by the given external ID.
     *
     * @param externalId the unique identifier of the product to be deleted
     */
    void deleteProduct(String externalId);
}

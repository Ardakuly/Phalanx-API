package com.backbone.phalanx.product.service;

import com.backbone.phalanx.product.dto.*;
import com.backbone.phalanx.product.model.Product;

import java.util.Map;

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
     * Retrieves the product details for the given barcode.
     *
     * @param barcode the unique identifier of the product in barcode format
     * @return a data transfer object containing the product details, including
     *         external ID, name, SKU, barcode, unit, category, prices, stock balance,
     *         photo URL, and timestamps for creation and update
     */
    ProductResponseDto getProductByBarcode(String barcode);

    /**
     * Creates a new product based on the provided product data.
     *
     * @param productRequestDto the data transfer object containing details of the product to be created.
     *                          It includes information such as the external ID, name, SKU, barcode, unit,
     *                          category, purchased price, selling price, stock balance, and photo URL.
     */
    // TODO: Regenerate information
    Product createProduct(ProductRequestDto productRequestDto);

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

    /**
     * Processes the sale of a list of products. Each product is identified
     * by its external ID or barcode and includes the quantity to be sold.
     *
     * @param products a list of {@link ProductSellDto} objects representing the products
     *                 to be sold. Each object includes details such as the product's
     *                 external ID, barcode, and the quantity being sold.
     */
    // TODO: Regenerate information
    Map.Entry<ProductSellDto, Product> sell(ProductSellDto products);
}

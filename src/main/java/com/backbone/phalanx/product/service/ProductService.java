package com.backbone.phalanx.product.service;

import com.backbone.phalanx.product.dto.*;
import com.backbone.phalanx.product.model.Product;

import java.util.List;
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
     * Retrieves a list of all products available in the system.
     *
     * @return a list of ProductResponseDto objects, where each object contains
     *         detailed information about a product, including external ID, name,
     *         SKU, barcode, unit, category, prices, stock balance, photo URL, and
     *         timestamps for creation and update.
     */
    List<ProductResponseDto> getAllProducts();

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
     * Retrieves the product details based on the provided product name.
     *
     * @param name the name of the product to be retrieved
     * @return a ProductResponseDto containing detailed information about the product,
     *         including external ID, name, SKU, barcode, unit, category, prices,
     *         stock balance, photo URL, and timestamps for creation and update
     */
    ProductResponseDto getByName(String name);

    /**
     * Creates a new product entity based on the provided product request data.
     *
     * @param productRequestDto the data transfer object containing the details of the product to be created.
     *                          It includes information such as the external ID, name, SKU, barcode, unit,
     *                          category, purchased price, selling price, stock balance, and photo URL.
     * @return the created Product entity containing all persisted details, including generated fields
     *         such as ID, createdAt, and updatedAt timestamps.
     */
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
     * Processes the sale of products based on the provided details and updates the stock balance.
     *
     * @param products the data transfer object containing the details of the products to be sold,
     *                 including external ID, barcode, and quantity.
     * @return a Map.Entry where the key is the ProductSellDto containing the details of the sale,
     *         and the value is the corresponding Product entity updated with the new stock balance.
     */
    Map.Entry<ProductSellDto, Product> sell(ProductSellDto products);
}

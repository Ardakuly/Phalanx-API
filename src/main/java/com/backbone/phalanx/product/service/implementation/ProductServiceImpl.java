package com.backbone.phalanx.product.service.implementation;

import com.backbone.phalanx.exception.ProductNotFoundException;
import com.backbone.phalanx.exception.ProductStockBalanceNotSufficientException;
import com.backbone.phalanx.product.dto.*;
import com.backbone.phalanx.product.mapper.ProductMapper;
import com.backbone.phalanx.product.model.Product;
import com.backbone.phalanx.product.repository.ProductRepository;
import com.backbone.phalanx.product.service.CategoryService;
import com.backbone.phalanx.product.service.ProductService;
import com.backbone.phalanx.specification.ProductSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;

    @Override
    public ProductFilterResponseDto getAllProductsByFiltering(ProductFilterRequestDto productFilterRequestDto) {

        Sort sort = ProductSpecification.getSort(
                productFilterRequestDto.sortBy(), productFilterRequestDto.sortDirection()
        );

        Pageable pageable = PageRequest.of(
                productFilterRequestDto.page(),
                productFilterRequestDto.pageSize(),
                sort
        );

        Specification<Product> specification = ProductSpecification.filterBy(
                productFilterRequestDto
        );

        Page<Product> pageResult = productRepository.findAll(specification, pageable);

        ProductFilterResponseDto productFilterResponseDto = new ProductFilterResponseDto(
                pageResult.getTotalPages(),
                (int) pageResult.getTotalElements(),
                productFilterRequestDto.page(),
                pageResult.map(productMapper::toDto).toList()
        );

        log.info("Filtered response has {} found records.", productFilterResponseDto.totalElements());

        return productFilterResponseDto;
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::toDto).toList();
    }

    @Override
    public ProductResponseDto getProductByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode).map(productMapper::toDto).orElseThrow(
                () -> new ProductNotFoundException(barcode)
        );
    }

    @Override
    @Transactional
    public Product createProduct(ProductRequestDto productRequestDto) {
        Product product = productRepository.save(
                Product.builder()
                        .externalId(UUID.randomUUID().toString())
                        .name(productRequestDto.name())
                        .sku(productRequestDto.sku())
                        .barcode(productRequestDto.barcode())
                        .unit(productRequestDto.unit())
                        .category(categoryService.createCategoryIfNotExists(productRequestDto.category().getName()))
                        .purchasedPrice(productRequestDto.purchasedPrice())
                        .sellingPrice(productRequestDto.sellingPrice())
                        .stockBalance(productRequestDto.stockBalance())
                        .photoUrl(productRequestDto.photoUrl())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );

        log.info("Product created with name: {} and externalId: {}", product.getName(), product.getExternalId());

        return product;
    }

    @Override
    @Transactional
    public void updateProduct(ProductRequestDto productRequestDto) {
        Product product = productRepository.findByExternalId(
                productRequestDto.externalId()
        ).orElseThrow(() -> new ProductNotFoundException(productRequestDto.externalId()));

        product.setName(productRequestDto.name() != null ? productRequestDto.name() : product.getName());
        product.setSku(productRequestDto.sku() != null ? productRequestDto.sku() : product.getSku());
        product.setBarcode(productRequestDto.barcode() != null ? productRequestDto.barcode() : product.getBarcode());
        product.setUnit(productRequestDto.unit() != null ? productRequestDto.unit() : product.getUnit());
        product.setCategory(
                productRequestDto.category() != null ? productRequestDto.category() : product.getCategory()
        );
        product.setPurchasedPrice(
                productRequestDto.purchasedPrice() != null ?
                        productRequestDto.purchasedPrice() :
                        product.getPurchasedPrice()
        );
        product.setSellingPrice(
                productRequestDto.sellingPrice() != null ?
                        productRequestDto.sellingPrice() :
                        product.getSellingPrice()
        );
        product.setStockBalance(
                productRequestDto.stockBalance() != null ?
                        productRequestDto.stockBalance() :
                        product.getStockBalance()
        );
        product.setPhotoUrl(
                productRequestDto.photoUrl() != null ? productRequestDto.photoUrl() : product.getPhotoUrl()
        );
        product.setUpdatedAt(LocalDateTime.now());

        log.info("Product updated with name: {} and externalId: {}", product.getName(), product.getExternalId());
    }

    @Override
    public void deleteProduct(String externalId) {
        productRepository.deleteByExternalId(externalId);

        log.info("Product deleted with externalId: {}", externalId);
    }

    @Override
    @Transactional
    public Map.Entry<ProductSellDto, Product> sell(ProductSellDto product) {
       Product productInStock = productRepository.findByExternalId(product.externalId()).orElseThrow(
               () -> new ProductNotFoundException(product.externalId())
       );

       if (productInStock.getStockBalance().subtract(product.quantity()).compareTo(BigDecimal.ZERO) < 0) {
           throw new ProductStockBalanceNotSufficientException(productInStock.getName());
       }

        productInStock.setStockBalance(productInStock.getStockBalance().subtract(product.quantity()));
        productInStock.setUpdatedAt(LocalDateTime.now());

        log.info(
                "Product sold with name: {}, externalId: {} and with quantity: {}",
                productInStock.getName(),
                productInStock.getExternalId(),
                product.quantity()
        );

        return  Map.entry(product, productInStock);
    }
}
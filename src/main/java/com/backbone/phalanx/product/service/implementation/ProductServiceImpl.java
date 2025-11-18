package com.backbone.phalanx.product.service.implementation;

import com.backbone.phalanx.exception.ProductNotFoundException;
import com.backbone.phalanx.product.dto.ProductFilterRequestDto;
import com.backbone.phalanx.product.dto.ProductFilterResponseDto;
import com.backbone.phalanx.product.dto.ProductRequestDto;
import com.backbone.phalanx.product.mapper.ProductMapper;
import com.backbone.phalanx.product.model.Product;
import com.backbone.phalanx.product.repository.ProductRepository;
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

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


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
    public void createProduct(ProductRequestDto productRequestDto) {
        Product product = productRepository.save(
                Product.builder()
                        .externalId(UUID.randomUUID().toString())
                        .name(productRequestDto.name())
                        .sku(productRequestDto.sku())
                        .barcode(productRequestDto.barcode())
                        .unit(productRequestDto.unit())
                        .category(productRequestDto.category())
                        .purchasedPrice(productRequestDto.purchasedPrice())
                        .sellingPrice(productRequestDto.sellingPrice())
                        .stockBalance(productRequestDto.stockBalance())
                        .photoUrl(productRequestDto.photoUrl())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );

        log.info("Product created with name: {} and externalId: {}", product.getName(), product.getExternalId());
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
}
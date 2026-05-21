package com.backbone.phalanx.inbound_document.service.implementation;

import com.backbone.phalanx.inbound_document.dto.InboundGoodResponseDto;
import com.backbone.phalanx.inbound_document.dto.InboundGoodUpdateRequestDto;
import com.backbone.phalanx.inbound_document.mapper.InboundDocumentMapper;
import com.backbone.phalanx.inbound_document.model.InboundDocument;
import com.backbone.phalanx.inbound_document.model.InboundGood;
import com.backbone.phalanx.inbound_document.repository.InboundGoodRepository;
import com.backbone.phalanx.inbound_document.service.InboundGoodService;
import com.backbone.phalanx.product.model.Product;
import com.backbone.phalanx.product.repository.ProductRepository;
import com.backbone.phalanx.product.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
public class InboundGoodServiceImpl implements InboundGoodService {

    private final InboundGoodRepository inboundGoodRepository;
    private final CategoryService categoryService;
    private final ProductRepository productRepository;
    private final InboundDocumentMapper inboundDocumentMapper;

    @Override
    @Transactional
    public InboundGood createInboundGood(InboundDocument inboundDocument, Product productInStock) {

        return inboundGoodRepository.save(
                InboundGood.builder()
                        .externalId(java.util.UUID.randomUUID().toString())
                        .name(productInStock.getName())
                        .sku(productInStock.getSku())
                        .barcode(productInStock.getBarcode())
                        .unit(productInStock.getUnit())
                        .category(categoryService.createCategoryIfNotExists(productInStock.getCategory().getName()))
                        .inboundDocument(inboundDocument)
                        .purchasedPrice(productInStock.getPurchasedPrice())
                        .sellingPrice(productInStock.getSellingPrice())
                        .quantity(productInStock.getStockBalance())
                        .photoUrl(productInStock.getPhotoUrl())
                        .createdAt(java.time.LocalDateTime.now())
                        .updatedAt(java.time.LocalDateTime.now())
                        .build()
        );
    }

    @Override
    @Transactional
    public InboundGoodResponseDto updateInboundGood(InboundGoodUpdateRequestDto request) {
        InboundGood inboundGood = inboundGoodRepository.findByExternalId(request.externalId())
                .orElseThrow(() -> new IllegalArgumentException("Inbound good not found with external id: " + request.externalId()));

        Product product = productRepository.findByBarcode(inboundGood.getBarcode())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with barcode: " + inboundGood.getBarcode()));

        BigDecimal newPurchasedPrice = request.purchasedPrice() != null ? request.purchasedPrice() : inboundGood.getPurchasedPrice();
        BigDecimal newQuantity = request.quantity() != null ? request.quantity() : inboundGood.getQuantity();
        BigDecimal newSellingPrice = request.sellingPrice() != null ? request.sellingPrice() : inboundGood.getSellingPrice();

        // Revert old inbound good's effect on the product
        BigDecimal currentTotal = product.getPurchasedPrice().multiply(product.getStockBalance());
        BigDecimal oldGoodEffect = inboundGood.getPurchasedPrice().multiply(inboundGood.getQuantity());

        BigDecimal totalWithoutOld = currentTotal.subtract(oldGoodEffect);
        BigDecimal stockWithoutOld = product.getStockBalance().subtract(inboundGood.getQuantity());

        // Apply new inbound good's effect
        BigDecimal newGoodEffect = newPurchasedPrice.multiply(newQuantity);
        BigDecimal newTotal = totalWithoutOld.add(newGoodEffect);
        BigDecimal newStock = stockWithoutOld.add(newQuantity);

        product.setStockBalance(newStock);
        if (newStock.compareTo(BigDecimal.ZERO) <= 0) {
            product.setPurchasedPrice(BigDecimal.ZERO);
        } else {
            product.setPurchasedPrice(newTotal.divide(newStock, 2, RoundingMode.HALF_UP));
        }
        product.setSellingPrice(newSellingPrice);
        product.setUpdatedAt(java.time.LocalDateTime.now());
        productRepository.save(product);

        // Update the InboundGood
        inboundGood.setPurchasedPrice(newPurchasedPrice);
        inboundGood.setSellingPrice(newSellingPrice);
        inboundGood.setQuantity(newQuantity);
        
        if (request.unit() != null) {
            inboundGood.setUnit(request.unit());
        }
        if (request.name() != null) {
            inboundGood.setName(request.name());
        }
        if (request.category() != null) {
            inboundGood.setCategory(categoryService.createCategoryIfNotExists(request.category()));
        }
        
        inboundGood.setUpdatedAt(java.time.LocalDateTime.now());

        InboundGood savedInboundGood = inboundGoodRepository.save(inboundGood);
        log.info("Updated InboundGood with externalId: {}", request.externalId());

        return inboundDocumentMapper.toGoodDto(savedInboundGood);
    }
}

package com.backbone.phalanx.inbound_document.service.implementation;

import com.backbone.phalanx.inbound_document.model.InboundDocument;
import com.backbone.phalanx.inbound_document.model.InboundGood;
import com.backbone.phalanx.inbound_document.repository.InboundGoodRepository;
import com.backbone.phalanx.inbound_document.service.InboundGoodService;
import com.backbone.phalanx.product.model.Product;
import com.backbone.phalanx.product.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InboundGoodServiceImpl implements InboundGoodService {

    private final InboundGoodRepository inboundGoodRepository;
    private final CategoryService categoryService;

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
}

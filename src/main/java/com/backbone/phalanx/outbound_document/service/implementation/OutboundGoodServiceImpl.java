package com.backbone.phalanx.outbound_document.service.implementation;

import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import com.backbone.phalanx.outbound_document.model.OutboundGood;
import com.backbone.phalanx.outbound_document.repository.OutboundGoodRepository;
import com.backbone.phalanx.outbound_document.service.OutboundGoodService;
import com.backbone.phalanx.product.dto.ProductSellDto;
import com.backbone.phalanx.product.model.Product;
import com.backbone.phalanx.product.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboundGoodServiceImpl implements OutboundGoodService {

    private final OutboundGoodRepository outboundGoodRepository;
    private final CategoryService categoryService;

    @Override
    @Transactional
    public OutboundGood createOutboundGood(
            OutboundDocument outboundDocument, ProductSellDto productToSell, Product productInStock
    ) {
        OutboundGood outboundGood = outboundGoodRepository.save(
                OutboundGood.builder()
                        .externalId(java.util.UUID.randomUUID().toString())
                        .name(productInStock.getName())
                        .sku(productInStock.getSku())
                        .barcode(productInStock.getBarcode())
                        .unit(productInStock.getUnit())
                        .category(categoryService.createCategoryIfNotExists(productInStock.getCategory().getName()))
                        .purchasedPrice(productInStock.getPurchasedPrice())
                        .sellingPrice(productInStock.getSellingPrice())
                        .quantity(productToSell.quantity())
                        .photoUrl(productInStock.getPhotoUrl())
                        .outboundDocument(outboundDocument)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                .build()
        );

        log.info(
                "Outbound good created with name: {} and externalId: {}",
                productInStock.getName(),
                productInStock.getExternalId()
        );

        return outboundGood;
    }
}


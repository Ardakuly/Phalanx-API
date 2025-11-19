package com.backbone.phalanx.inbound_document.service.implementation;

import com.backbone.phalanx.inbound_document.model.InboundGood;
import com.backbone.phalanx.inbound_document.repository.InboundGoodRepository;
import com.backbone.phalanx.inbound_document.service.InboundGoodService;
import com.backbone.phalanx.product.model.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InboundGoodServiceImpl implements InboundGoodService {

    private final InboundGoodRepository inboundGoodRepository;

    @Override
    @Transactional
    public InboundGood createInboundGood(Product productInStock) {
        InboundGood inboundGood = inboundGoodRepository.save(
                InboundGood.builder()
                        .externalId(java.util.UUID.randomUUID().toString())
                        .name(productInStock.getName())
                        .sku(productInStock.getSku())
                        .barcode(productInStock.getBarcode())
                        .unit(productInStock.getUnit())
                        .purchasedPrice(productInStock.getPurchasedPrice())
                        .sellingPrice(productInStock.getSellingPrice())
                        .quantity(productInStock.getStockBalance())
                        .photoUrl(productInStock.getPhotoUrl())
                        .createdAt(java.time.LocalDateTime.now())
                        .updatedAt(java.time.LocalDateTime.now())
                        .build()
        );

        return inboundGood;
    }
}

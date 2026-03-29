package com.backbone.phalanx.good_return_document.service.implementation;

import com.backbone.phalanx.exception.ProductNotFoundException;
import com.backbone.phalanx.exception.ReturnQuantityExceededException;
import com.backbone.phalanx.good_return_document.dto.GoodReturnDocumentRequestDto;
import com.backbone.phalanx.good_return_document.dto.GoodReturnDocumentResponseDto;
import com.backbone.phalanx.good_return_document.dto.ReturnedGoodRequestDto;
import com.backbone.phalanx.good_return_document.mapper.GoodReturnDocumentMapper;
import com.backbone.phalanx.good_return_document.model.GoodReturnDocument;
import com.backbone.phalanx.good_return_document.model.ReturnedGood;
import com.backbone.phalanx.good_return_document.repository.GoodReturnDocumentRepository;
import com.backbone.phalanx.good_return_document.service.GoodReturnDocumentService;
import com.backbone.phalanx.good_return_document.service.ReturnedGoodService;
import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import com.backbone.phalanx.outbound_document.model.OutboundGood;
import com.backbone.phalanx.outbound_document.service.OutboundDocumentService;
import com.backbone.phalanx.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoodReturnDocumentServiceImpl implements GoodReturnDocumentService {

        private final GoodReturnDocumentRepository goodReturnDocumentRepository;
        private final ReturnedGoodService returnedGoodService;
        private final OutboundDocumentService outboundDocumentService;
        private final ProductService productService;
        private final GoodReturnDocumentMapper goodReturnDocumentMapper;

        @Override
        @Transactional
        public GoodReturnDocumentResponseDto createGoodReturnDocument(GoodReturnDocumentRequestDto request) {

                log.info("Creating Good Return Document for Outbound Document: {}",
                                request.outboundDocumentNumber());

                OutboundDocument outboundDocument = outboundDocumentService
                                .findByDocumentNumber(request.outboundDocumentNumber());

                GoodReturnDocument goodReturnDocument = GoodReturnDocument.builder()
                                .externalId(UUID.randomUUID().toString())
                                .documentNumber(UUID.randomUUID().toString())
                                .outboundDocument(outboundDocument)
                                .comment(request.comment())
                                .refundAmount(BigDecimal.ZERO)
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build();

                List<ReturnedGood> returnedGoods = new ArrayList<>();
                BigDecimal totalRefund = BigDecimal.ZERO;

                for (ReturnedGoodRequestDto goodRequest : request.goods()) {
                        OutboundGood outboundGood = outboundDocument.getOutboundGoods().stream()
                                        .filter(og -> og.getBarcode().equals(goodRequest.barcode()))
                                        .findFirst()
                                        .orElseThrow(() -> new ProductNotFoundException(goodRequest.barcode()));

                        // Calculate already returned quantity
                        BigDecimal alreadyReturnedQuantity = returnedGoodService
                                        .getAlreadyReturnedQuantity(outboundGood.getId());

                        if (alreadyReturnedQuantity.add(goodRequest.quantity())
                                        .compareTo(outboundGood.getQuantity()) > 0) {
                                throw new ReturnQuantityExceededException(outboundGood.getName());
                        }

                        // Create ReturnedGood record
                        ReturnedGood returnedGood = ReturnedGood.builder()
                                        .externalId(UUID.randomUUID().toString())
                                        .name(outboundGood.getName())
                                        .barcode(outboundGood.getBarcode())
                                        .unit(outboundGood.getUnit())
                                        .category(outboundGood.getCategory())
                                        .purchasedPrice(outboundGood.getPurchasedPrice())
                                        .sellingPrice(outboundGood.getSellingPrice())
                                        .quantity(goodRequest.quantity())
                                        .goodReturnDocument(goodReturnDocument)
                                        .outboundGood(outboundGood)
                                        .createdAt(LocalDateTime.now())
                                        .updatedAt(LocalDateTime.now())
                                        .build();

                        returnedGoods.add(returnedGood);

                        // Update product stock
                        productService.increaseStock(outboundGood.getBarcode(), goodRequest.quantity());

                        // Calculate refund for this item
                        BigDecimal itemRefund = outboundGood.getSellingPrice().multiply(goodRequest.quantity());
                        totalRefund = totalRefund.add(itemRefund);
                }

                goodReturnDocument.setReturnedGoods(returnedGoods);
                goodReturnDocument.setRefundAmount(totalRefund);

                GoodReturnDocument savedDoc = goodReturnDocumentRepository.save(goodReturnDocument);
                log.info("Good Return Document created with ID: {}", savedDoc.getExternalId());

                return goodReturnDocumentMapper.toDto(savedDoc);
        }
}
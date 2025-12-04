package com.backbone.phalanx.outbound_document.service.implementation;

import com.backbone.phalanx.outbound_document.dto.OutboundDocumentDto;
import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import com.backbone.phalanx.outbound_document.model.OutboundGood;
import com.backbone.phalanx.outbound_document.repository.OutboundDocumentRepository;
import com.backbone.phalanx.outbound_document.service.OutboundDocumentService;
import com.backbone.phalanx.outbound_document.service.OutboundGoodService;
import com.backbone.phalanx.product.dto.ProductSellDto;
import com.backbone.phalanx.product.model.Product;
import com.backbone.phalanx.product.service.ProductService;
import com.backbone.phalanx.user.service.implementation.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboundDocumentServiceImpl implements OutboundDocumentService {

    private final OutboundDocumentRepository outboundDocumentRepository;
    private final ProductService productService;
    private final OutboundGoodService outboundGoodService;
    private final UserServiceImpl userService;

    @Override
    @Transactional(readOnly = true)
    public List<OutboundDocument> getAllOutboundDocumentByInterval(LocalDateTime from, LocalDateTime to) {
        return outboundDocumentRepository.findAllByCreatedAtBetween(from, to);
    }

    @Override
    @Transactional(propagation = REQUIRES_NEW)
    public OutboundDocument createOutboundDocument(
            OutboundDocumentDto outboundDocumentDto, String sellerEmail
    ) {

        Map<ProductSellDto, Product> productSellDtoToProducts = outboundDocumentDto.products().stream().map(
                productService::sell
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        OutboundDocument outboundDocument = outboundDocumentRepository.save(
                OutboundDocument.builder()
                        .externalId(UUID.randomUUID().toString())
                        .documentNumber(UUID.randomUUID().toString())
                        .paymentType(outboundDocumentDto.paymentType())
                        .seller(userService.loadUserByUsername(sellerEmail))
                        .createdAt(java.time.LocalDateTime.now())
                        .updatedAt(java.time.LocalDateTime.now())
                        .build()
        );

        List<OutboundGood> outboundGoods = productSellDtoToProducts.entrySet().stream().map(
                (productSellDtoToProduct) -> {
                    ProductSellDto productSellDto = productSellDtoToProduct.getKey();
                    Product product = productSellDtoToProduct.getValue();

                    return outboundGoodService.createOutboundGood(outboundDocument, productSellDto, product);
                }
        ).toList();

        outboundDocument.setOutboundGoods(outboundGoods);

        log.info(
                "Outbound document created with document number: {} and externalId: {}",
                outboundDocument.getDocumentNumber(),
                outboundDocument.getExternalId()
        );

        return outboundDocument;
    }
}

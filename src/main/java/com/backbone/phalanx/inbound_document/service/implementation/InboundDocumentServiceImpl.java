package com.backbone.phalanx.inbound_document.service.implementation;

import com.backbone.phalanx.inbound_document.model.InboundDocument;
import com.backbone.phalanx.inbound_document.model.InboundGood;
import com.backbone.phalanx.inbound_document.repository.InboundDocumentRepository;
import com.backbone.phalanx.inbound_document.service.InboundDocumentService;
import com.backbone.phalanx.inbound_document.service.InboundGoodService;
import com.backbone.phalanx.product.dto.ProductRequestDto;
import com.backbone.phalanx.product.model.Product;
import com.backbone.phalanx.product.service.ProductService;
import com.backbone.phalanx.inbound_document.dto.InboundDocumentFilterRequestDto;
import com.backbone.phalanx.inbound_document.dto.InboundDocumentFilterResponseDto;
import com.backbone.phalanx.inbound_document.dto.InboundDocumentResponseDto;
import com.backbone.phalanx.inbound_document.mapper.InboundDocumentMapper;
import com.backbone.phalanx.specification.InboundDocumentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
@RequiredArgsConstructor
@Slf4j
public class InboundDocumentServiceImpl implements InboundDocumentService {

    private final ProductService productService;
    private final InboundGoodService inboundGoodService;
    private final InboundDocumentRepository inboundDocumentRepository;
    private final InboundDocumentMapper inboundDocumentMapper;

    @Override
    @Transactional(propagation = REQUIRES_NEW)
    public InboundDocument creatInboundDocument(List<ProductRequestDto> products) {

        List<Product> productsInStock = products.stream()
                .map(productService::createProduct)
                .toList();

        InboundDocument inboundDocument = InboundDocument.builder()
                .externalId(UUID.randomUUID().toString())
                .documentNumber(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        InboundDocument savedInboundDocument = inboundDocumentRepository.save(inboundDocument);

        List<InboundGood> inboundGoods = productsInStock.stream()
                .map((product) -> inboundGoodService.createInboundGood(inboundDocument, product))
                .toList();

        savedInboundDocument.setInboundGoods(inboundGoods);

        log.info(
                "Inbound document created with document number: {}",
                inboundDocument.getDocumentNumber()
        );

        return inboundDocument;
    }

    @Override
    public InboundDocumentFilterResponseDto getAllInboundDocumentsByFiltering(InboundDocumentFilterRequestDto filter) {
        int pageNumber = filter.page() != null ? filter.page() : 0;
        int pageSize = filter.pageSize() != null ? filter.pageSize() : 10;

        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                InboundDocumentSpecification.getSort(filter.sortBy(), filter.sortDirection())
        );

        Page<InboundDocument> inboundDocumentsPage = inboundDocumentRepository.findAll(
                InboundDocumentSpecification.filterBy(filter),
                pageable
        );

        List<InboundDocumentResponseDto> content = inboundDocumentsPage.getContent().stream()
                .map(inboundDocumentMapper::toDto)
                .toList();

        return new InboundDocumentFilterResponseDto(
                inboundDocumentsPage.getTotalPages(),
                inboundDocumentsPage.getTotalElements(),
                inboundDocumentsPage.getNumber(),
                content
        );
    }
}

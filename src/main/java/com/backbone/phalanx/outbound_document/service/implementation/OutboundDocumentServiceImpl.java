package com.backbone.phalanx.outbound_document.service.implementation;

import com.backbone.phalanx.outbound_document.dto.OutboundDocumentDto;
import com.backbone.phalanx.outbound_document.dto.OutboundDocumentFilterRequestDto;
import com.backbone.phalanx.outbound_document.dto.OutboundDocumentFilterResponseDto;
import com.backbone.phalanx.outbound_document.mapper.OutboundDocumentMapper;
import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import com.backbone.phalanx.outbound_document.model.OutboundGood;
import com.backbone.phalanx.outbound_document.repository.OutboundDocumentRepository;
import com.backbone.phalanx.outbound_document.service.OutboundDocumentService;
import com.backbone.phalanx.outbound_document.service.OutboundGoodService;
import com.backbone.phalanx.product.dto.ProductSellDto;
import com.backbone.phalanx.product.model.Product;
import com.backbone.phalanx.product.service.ProductService;
import com.backbone.phalanx.specification.OutboundDocumentSpecification;
import com.backbone.phalanx.user.service.implementation.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
        private final OutboundDocumentMapper outboundDocumentMapper;

        @Override
        @Transactional(readOnly = true)
        public OutboundDocumentFilterResponseDto getAllOutboundDocumentsByFiltering(
                        OutboundDocumentFilterRequestDto filter) {
                Sort sort = OutboundDocumentSpecification.getSort(filter.sortBy(), filter.sortDirection());
                Pageable pageable = PageRequest.of(filter.page(), filter.pageSize(), sort);
                Specification<OutboundDocument> spec = OutboundDocumentSpecification.filterBy(filter);

                Page<OutboundDocument> pageResult = outboundDocumentRepository.findAll(spec, pageable);

                OutboundDocumentFilterResponseDto response = new OutboundDocumentFilterResponseDto(
                                pageResult.getTotalPages(),
                                (int) pageResult.getTotalElements(),
                                filter.page(),
                                pageResult.getContent().stream().map(outboundDocumentMapper::toDto).toList());

                log.info("Filtered outbound documents response has {} found records.", response.totalElements());
                return response;
        }

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
                                                .documentNumber(UUID.randomUUID().toString()) // TODO: generate barcode
                                                .paymentType(outboundDocumentDto.paymentType())
                                                .seller(userService.loadUserByUsername(sellerEmail))
                                                .createdAt(java.time.LocalDateTime.now())
                                                .updatedAt(java.time.LocalDateTime.now())
                                                .build());

                List<OutboundGood> outboundGoods = productSellDtoToProducts.entrySet().stream().map(
                                (productSellDtoToProduct) -> {
                                        ProductSellDto productSellDto = productSellDtoToProduct.getKey();
                                        Product product = productSellDtoToProduct.getValue();

                                        return outboundGoodService.createOutboundGood(outboundDocument, productSellDto,
                                                        product);
                                }).toList();

                outboundDocument.setOutboundGoods(outboundGoods);

                log.info(
                                "Outbound document created with document number: {} and externalId: {}",
                                outboundDocument.getDocumentNumber(),
                                outboundDocument.getExternalId());

                return outboundDocument;
        }

        @Override
        @Transactional(readOnly = true)
        public OutboundDocument findByDocumentNumber(String documentNumber) {
                return outboundDocumentRepository.findByDocumentNumber(documentNumber)
                                .orElseThrow(() -> new RuntimeException("Outbound Document not found: " + documentNumber));
        }
}

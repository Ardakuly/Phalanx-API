package com.backbone.phalanx.inventarization.service;

import com.backbone.phalanx.inventarization.dto.CountRequestDto;

import com.backbone.phalanx.inventarization.dto.InventarizationResponseDto;
import com.backbone.phalanx.inventarization.model.Inventarization;
import com.backbone.phalanx.inventarization.model.InventarizationItem;
import com.backbone.phalanx.inventarization.model.InventarizationStatus;
import com.backbone.phalanx.inventarization.repository.InventarizationRepository;
import com.backbone.phalanx.inventarization.mapper.InventarizationMapper;
import com.backbone.phalanx.product.model.Product;
import com.backbone.phalanx.product.service.ProductService;
import com.backbone.phalanx.authentication.util.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventarizationServiceImpl implements InventarizationService {

    private final InventarizationRepository inventarizationRepository;
    private final InventarizationItemService inventarizationItemService;
    private final ProductService productService;
    private final InventarizationMapper inventarizationMapper;

    @Override
    @Transactional
    public InventarizationResponseDto start() {

        if (inventarizationRepository.existsByStatus(InventarizationStatus.IN_PROGRESS)) {
            throw new IllegalStateException("An inventarization session is already in progress.");
        }

        String currentUser = SecurityUtils.getCurrentUser();

        Inventarization inventarization = Inventarization.builder()
                .status(InventarizationStatus.IN_PROGRESS)
                .startedAt(LocalDateTime.now())
                .conductedBy(currentUser)
                .createdBy(currentUser)
                .build();

        final Inventarization savedInventarization = inventarizationRepository.save(inventarization);

        List<Product> allProducts = productService.getAllProductEntities();

        List<InventarizationItem> itemsToSave = allProducts.stream()
                .map(product -> InventarizationItem.builder()
                        .inventarization(savedInventarization)
                        .product(product)
                        .expectedQuantity(product.getStockBalance())
                        .countedQuantity(BigDecimal.ZERO)
                        .build())
                .toList();

        inventarizationItemService.saveAll(itemsToSave);

        return getById(savedInventarization.getId());
    }

    @Override
    @Transactional
    public InventarizationResponseDto count(Long id, CountRequestDto requestDto) {

        Inventarization inventarization = inventarizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventarization session not found."));

        if (!inventarization.getStatus().equals(InventarizationStatus.IN_PROGRESS)) {
            throw new IllegalStateException("Cannot count items for a session that is not in progress.");
        }

        InventarizationItem item = inventarizationItemService
                .findByInventarizationIdAndProductId(id, requestDto.productId())
                .orElseThrow(() -> new EntityNotFoundException("Item not found in this inventarization session."));

        item.setCountedQuantity(requestDto.countedQuantity());
        item.setUpdatedAt(LocalDateTime.now());
        inventarizationItemService.save(item);

        return getById(id);
    }

    @Override
    @Transactional
    public InventarizationResponseDto complete(Long id) {

        Inventarization inventarization = inventarizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventarization session not found."));

        if (!inventarization.getStatus().equals(InventarizationStatus.IN_PROGRESS)) {
            throw new IllegalStateException("Cannot complete a session that is not in progress.");
        }

        List<InventarizationItem> items = inventarizationItemService.findByInventarizationId(id);

        items.forEach(item -> {
            BigDecimal expected = item.getExpectedQuantity();
            BigDecimal counted = item.getCountedQuantity() != null ? item.getCountedQuantity() : BigDecimal.ZERO;
            BigDecimal diff = counted.subtract(expected);

            item.setCountedQuantity(counted);
            item.setFinalQuantity(counted);
            item.setDifference(diff);
            item.setUpdatedAt(LocalDateTime.now());

            productService.updateProductStock(item.getProduct().getId(), counted);
        });

        inventarizationItemService.saveAll(items);

        inventarization.setStatus(InventarizationStatus.COMPLETED);
        inventarization.setCompletedAt(LocalDateTime.now());
        inventarization.setClosedBy(SecurityUtils.getCurrentUser());
        inventarizationRepository.save(inventarization);

        return getById(id);
    }

    @Override
    public InventarizationResponseDto getById(Long id) {
        Inventarization inventarization = inventarizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inventarization session not found."));

        List<InventarizationItem> items = inventarizationItemService.findByInventarizationId(id);

        return inventarizationMapper.toDto(inventarization, items);
    }

    @Override
    public InventarizationResponseDto getActive() {
        Inventarization inventarization = inventarizationRepository.findByStatus(InventarizationStatus.IN_PROGRESS)
                .orElseThrow(() -> new EntityNotFoundException("Inventarization session not found."));

        List<InventarizationItem> items = inventarizationItemService.findByInventarizationId(inventarization.getId());

        return inventarizationMapper.toDto(inventarization, items);
    }
}
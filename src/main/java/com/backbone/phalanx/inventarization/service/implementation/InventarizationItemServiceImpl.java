package com.backbone.phalanx.inventarization.service.implementation;

import com.backbone.phalanx.inventarization.model.InventarizationItem;
import com.backbone.phalanx.inventarization.repository.InventarizationItemRepository;
import com.backbone.phalanx.inventarization.service.InventarizationItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventarizationItemServiceImpl implements InventarizationItemService {

    private final InventarizationItemRepository repository;

    @Override
    public List<InventarizationItem> saveAll(List<InventarizationItem> items) {
        return repository.saveAll(items);
    }

    @Override
    public InventarizationItem save(InventarizationItem item) {
        return repository.save(item);
    }

    @Override
    public Optional<InventarizationItem> findByInventarizationIdAndProductId(Long inventarizationId, Long productId) {
        return repository.findByInventarizationIdAndProductId(inventarizationId, productId);
    }

    @Override
    public List<InventarizationItem> findByInventarizationId(Long inventarizationId) {
        return repository.findByInventarizationId(inventarizationId);
    }
}
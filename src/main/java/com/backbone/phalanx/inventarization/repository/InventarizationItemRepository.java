package com.backbone.phalanx.inventarization.repository;

import com.backbone.phalanx.inventarization.model.InventarizationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarizationItemRepository extends JpaRepository<InventarizationItem, Long> {

    List<InventarizationItem> findByInventarizationId(Long inventarizationId);

    Optional<InventarizationItem> findByInventarizationIdAndProductId(Long inventarizationId, Long productId);

    List<InventarizationItem> findByInventarizationIdAndDifferenceNot(Long inventarizationId,
            java.math.BigDecimal difference);
}
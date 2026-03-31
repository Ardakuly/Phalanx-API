package com.backbone.phalanx.inventarization.repository;

import com.backbone.phalanx.inventarization.model.Inventarization;
import com.backbone.phalanx.inventarization.model.InventarizationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventarizationRepository extends JpaRepository<Inventarization, Long>, JpaSpecificationExecutor<Inventarization> {

    boolean existsByStatus(InventarizationStatus status);

    Optional<Inventarization> findByStatus(InventarizationStatus status);
}
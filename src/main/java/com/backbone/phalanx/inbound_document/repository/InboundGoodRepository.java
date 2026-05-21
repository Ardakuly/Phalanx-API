package com.backbone.phalanx.inbound_document.repository;

import com.backbone.phalanx.inbound_document.model.InboundGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InboundGoodRepository extends JpaRepository<InboundGood, Long> {

    Optional<InboundGood> findByExternalId(String externalId);
}

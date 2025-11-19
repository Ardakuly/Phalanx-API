package com.backbone.phalanx.outbound_document.repository;

import com.backbone.phalanx.outbound_document.model.OutboundGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboundGoodRepository extends JpaRepository<OutboundGood, Long> {
}

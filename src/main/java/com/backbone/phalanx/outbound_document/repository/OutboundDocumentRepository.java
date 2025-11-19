package com.backbone.phalanx.outbound_document.repository;

import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboundDocumentRepository extends JpaRepository<OutboundDocument, Long> {
}

package com.backbone.phalanx.inbound_document.repository;

import com.backbone.phalanx.inbound_document.model.InboundDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InboundDocumentRepository extends JpaRepository<InboundDocument, Long> {
}

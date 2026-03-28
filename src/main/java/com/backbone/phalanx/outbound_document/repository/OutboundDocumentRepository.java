package com.backbone.phalanx.outbound_document.repository;

import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface OutboundDocumentRepository
        extends JpaRepository<OutboundDocument, Long>, JpaSpecificationExecutor<OutboundDocument> {

    List<OutboundDocument> findAllByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}
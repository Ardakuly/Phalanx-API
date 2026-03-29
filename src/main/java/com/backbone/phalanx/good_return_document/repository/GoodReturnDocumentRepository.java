package com.backbone.phalanx.good_return_document.repository;

import com.backbone.phalanx.good_return_document.model.GoodReturnDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoodReturnDocumentRepository extends JpaRepository<GoodReturnDocument, Long>, JpaSpecificationExecutor<GoodReturnDocument> {
    Optional<GoodReturnDocument> findByExternalId(String externalId);
}

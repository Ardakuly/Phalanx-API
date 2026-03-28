package com.backbone.phalanx.good_return_document.repository;

import com.backbone.phalanx.good_return_document.model.ReturnedGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnedGoodRepository extends JpaRepository<ReturnedGood, Long> {
    List<ReturnedGood> findAllByOutboundGoodId(Long outboundGoodId);
}

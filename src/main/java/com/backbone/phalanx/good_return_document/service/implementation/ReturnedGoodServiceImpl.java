package com.backbone.phalanx.good_return_document.service.implementation;

import com.backbone.phalanx.good_return_document.model.ReturnedGood;
import com.backbone.phalanx.good_return_document.repository.ReturnedGoodRepository;
import com.backbone.phalanx.good_return_document.service.ReturnedGoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReturnedGoodServiceImpl implements ReturnedGoodService {

    private final ReturnedGoodRepository returnedGoodRepository;

    @Override
    public List<ReturnedGood> findAllByOutboundGoodId(Long outboundGoodId) {
        return returnedGoodRepository.findAllByOutboundGoodId(outboundGoodId);
    }

    @Override
    public BigDecimal getAlreadyReturnedQuantity(Long outboundGoodId) {
        return findAllByOutboundGoodId(outboundGoodId).stream()
                .map(ReturnedGood::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

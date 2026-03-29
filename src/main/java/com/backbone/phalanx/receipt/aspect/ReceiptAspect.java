package com.backbone.phalanx.receipt.aspect;

import com.backbone.phalanx.good_return_document.dto.GoodReturnDocumentResponseDto;
import com.backbone.phalanx.good_return_document.repository.GoodReturnDocumentRepository;
import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import com.backbone.phalanx.receipt.model.ReturnCompletedEvent;
import com.backbone.phalanx.receipt.model.SaleCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ReceiptAspect {

    private final ApplicationEventPublisher eventPublisher;
    private final GoodReturnDocumentRepository goodReturnDocumentRepository;

    @AfterReturning(pointcut = "execution(* com.backbone.phalanx.outbound_document.service.OutboundDocumentService.createOutboundDocument(..))", returning = "outboundDocument")
    public void afterCreateOutboundDocument(OutboundDocument outboundDocument) {

        log.info(
                "Sale successfully completed. Publishing SaleCompletedEvent for document: {}",
                outboundDocument.getDocumentNumber());
        eventPublisher.publishEvent(new SaleCompletedEvent(this, outboundDocument));
    }

    @AfterReturning(pointcut = "execution(* com.backbone.phalanx.good_return_document.service.GoodReturnDocumentService.createGoodReturnDocument(..))", returning = "responseDto")
    public void afterCreateGoodReturnDocument(GoodReturnDocumentResponseDto responseDto) {

        log.info("Good return successfully completed. Publishing ReturnCompletedEvent for document: {}",
                responseDto.documentNumber());

        goodReturnDocumentRepository.findByExternalId(responseDto.externalId()).ifPresent(doc -> {
            eventPublisher.publishEvent(new ReturnCompletedEvent(this, doc));
        });
    }
}
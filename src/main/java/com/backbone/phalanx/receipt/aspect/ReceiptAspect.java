package com.backbone.phalanx.receipt.aspect;

import com.backbone.phalanx.outbound_document.model.OutboundDocument;
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

    @AfterReturning(pointcut = "execution(* com.backbone.phalanx.outbound_document.service.OutboundDocumentService.createOutboundDocument(..))", returning = "outboundDocument")
    public void afterCreateOutboundDocument(OutboundDocument outboundDocument) {

        log.info("Sale successfully completed. Publishing SaleCompletedEvent for document: {}",
                outboundDocument.getDocumentNumber());
        eventPublisher.publishEvent(new SaleCompletedEvent(this, outboundDocument));
    }
}
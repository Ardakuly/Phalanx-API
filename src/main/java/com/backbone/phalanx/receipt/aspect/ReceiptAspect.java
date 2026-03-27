package com.backbone.phalanx.receipt.aspect;

import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import com.backbone.phalanx.receipt.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ReceiptAspect {

    private final ReceiptService receiptService;

    @AfterReturning(
            pointcut = "execution(* com.backbone.phalanx.outbound_document.service.OutboundDocumentService.createOutboundDocument(..))",
            returning = "outboundDocument"
    )
    public void afterCreateOutboundDocument(OutboundDocument outboundDocument) {
        log.info("Sale successfully completed. Triggering receipt printing for document: {}", outboundDocument.getDocumentNumber());
        try {
            receiptService.printReceipt(outboundDocument);
        } catch (Exception e) {
            log.error("Failed to initiate receipt printing for document: {}", outboundDocument.getDocumentNumber(), e);
        }
    }
}

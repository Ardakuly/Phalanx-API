package com.backbone.phalanx.receipt.listener;

import com.backbone.phalanx.receipt.model.SaleCompletedEvent;
import com.backbone.phalanx.receipt.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReceiptEventListener {

    private final ReceiptService receiptService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleSaleCompletedEvent(SaleCompletedEvent event) {

        log.info(
                "Transaction committed. Initiating receipt printing for document: {}",
                event.getOutboundDocument().getDocumentNumber());
        try {
            receiptService.printReceipt(event.getOutboundDocument());
        } catch (Exception exception) {
            log.error(
                    "Failed to trigger receipt printing for document: {}",
                    event.getOutboundDocument().getDocumentNumber(),
                    exception);
        }
    }
}
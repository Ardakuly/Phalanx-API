package com.backbone.phalanx.receipt.model;

import com.backbone.phalanx.outbound_document.model.OutboundDocument;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SaleCompletedEvent extends ApplicationEvent {

    private final OutboundDocument outboundDocument;

    public SaleCompletedEvent(Object source, OutboundDocument outboundDocument) {
        super(source);
        this.outboundDocument = outboundDocument;
    }
}
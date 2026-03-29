package com.backbone.phalanx.receipt.model;

import com.backbone.phalanx.good_return_document.model.GoodReturnDocument;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ReturnCompletedEvent extends ApplicationEvent {
    private final GoodReturnDocument goodReturnDocument;

    public ReturnCompletedEvent(Object source, GoodReturnDocument goodReturnDocument) {
        super(source);
        this.goodReturnDocument = goodReturnDocument;
    }
}
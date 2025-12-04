package com.backbone.phalanx.outbound_document.model;

import lombok.Getter;

@Getter
public enum PaymentType {

    CASH(1, "Наличные"),
    CARD(2, "Карта"),
    QR(3, "Каспи QR");

    private final int value;

    private final String name;

    PaymentType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static PaymentType fromCode(int value) {
        for (PaymentType type : values()) {
            if (type.value == value) return type;
        }

        throw new IllegalArgumentException("Invalid PaymentType value: " + value);
    }
}
package com.backbone.phalanx.product.model;

import lombok.Getter;

@Getter
public enum Unit {

    PIECE(1, "шт"),

    KILOGRAM(2, "кг"),

    GRAM(3, "г"),

    LITRE(4, "л"),

    METER(5, "м");

    private final int value;

    private final String name;

    Unit(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Unit fromCode(int value) {
        for (Unit status : values()) {
            if (status.value == value) return status;
        }
        throw new IllegalArgumentException("Invalid Unit value: " + value);
    }

}
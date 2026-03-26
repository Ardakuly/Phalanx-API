package com.backbone.phalanx.inventarization.model;

import lombok.Getter;

@Getter
public enum InventarizationStatus {

    CREATED(1, "Создан"),
    IN_PROGRESS(2, "В процессе"),
    COMPLETED(3, "Завершен");

    private final int value;
    private final String name;

    InventarizationStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static InventarizationStatus fromCode(int value) {
        for (InventarizationStatus status : values()) {
            if (status.value == value)
                return status;
        }
        throw new IllegalArgumentException("Invalid InventarizationStatus value: " + value);
    }
}
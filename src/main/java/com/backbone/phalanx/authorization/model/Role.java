package com.backbone.phalanx.authorization.model;

import lombok.Getter;

@Getter
public enum Role {

    EMPLOYER(1,"EMPLOYER"),
    EMPLOYEE(2, "EMPLOYEE");

    private final int value;

    private final String name;

    Role(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Role fromCode(int value) {
        for (Role status : values()) {
            if (status.value == value) return status;
        }
        throw new IllegalArgumentException("Invalid Role value: " + value);
    }
}

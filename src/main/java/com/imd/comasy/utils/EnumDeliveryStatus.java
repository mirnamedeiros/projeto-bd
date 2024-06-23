package com.imd.comasy.utils;

import lombok.Getter;

@Getter
public enum EnumDeliveryStatus {

    PENDING("pending"),
    DELIVERED("delivered");

    private final String role;

    EnumDeliveryStatus(String role) {
        this.role = role;
    }

    public String toUpperCase() {
        return this.role.toUpperCase();
    }
}

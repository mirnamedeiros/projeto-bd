package com.imd.comasy.utils;

import lombok.Getter;

@Getter
public enum EnumStatusVisitor {

    PENDING("pending"),
    APPROVED("approved"),
    DENIED("denied"),
    CHECKOUT("checkout");

    private final String role;

    EnumStatusVisitor(String role) {
        this.role = role;
    }

    public String toUpperCase() {
        return this.role.toUpperCase();
    }
}

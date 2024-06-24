package com.imd.comasy.utils;

import lombok.Getter;

@Getter
public enum EnumVisitor {

    VISITOR("visitor"),
    DELIVERY("delivery");

    private final String role;

    EnumVisitor(String role) {
        this.role = role;
    }

    public String toUpperCase() {
        return this.role.toUpperCase();
    }
}

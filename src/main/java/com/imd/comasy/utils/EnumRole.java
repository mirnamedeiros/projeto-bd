package com.imd.comasy.utils;

import lombok.Getter;

@Getter
public enum EnumRole {

    ADMIN("admin"),
    MANAGER("manager"),
    RESIDENT("resident"),
    DOORMAN("doorman");

    private final String role;

    EnumRole(String role) {
        this.role = role;
    }

    public String toUpperCase() {
        return this.role.toUpperCase();
    }
}
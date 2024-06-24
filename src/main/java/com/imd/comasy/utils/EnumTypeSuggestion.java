package com.imd.comasy.utils;

import lombok.Getter;

@Getter
public enum EnumTypeSuggestion {

    MAINTENANCE("maintenance"),
    EVENT("event"),
    SECURITY("security"),
    OTHER("other");

    private final String role;

    EnumTypeSuggestion(String role) {
        this.role = role;
    }

    public String toUpperCase() {
        return this.role.toUpperCase();
    }
}

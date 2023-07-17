package com.example.qpc.entity;

public enum RoleEntity {
    MEMBER("일반 회원"),
    ADMIN("관리자"),
    GUEST("손님");

    private final String description;

    RoleEntity(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

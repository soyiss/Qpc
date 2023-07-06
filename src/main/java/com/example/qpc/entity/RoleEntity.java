package com.example.qpc.entity;

public enum RoleEntity {
    ROLE_MEMBER("일반 회원"),
    ROLE_ADMIN("관리자"),
    ROLE_GUEST("손님");

    private final String description;

    RoleEntity(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

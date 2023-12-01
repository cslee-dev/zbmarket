package com.example.zbmarket.type.member;

public enum MemberRoleEnum {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    MemberRoleEnum(String roleAdmin) {
        this.value = roleAdmin;
    }

    private final String value;
}

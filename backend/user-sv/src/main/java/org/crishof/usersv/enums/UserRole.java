package org.crishof.usersv.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    CUSTOMER("Customer"), ADMIN("Admin"), USER("User");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }
}

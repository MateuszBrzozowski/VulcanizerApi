package pl.mbrzozowski.vulcanizer.enums;

import static pl.mbrzozowski.vulcanizer.constant.Authority.*;

public enum AppRole {
    ROLE_USER(USER_AUTHORITIES),
    ROLE_BUSINESS_OWNER(USER_BUSINESS_OWNER_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES),
    ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES);

    private final String[] authorities;

    AppRole(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }
}

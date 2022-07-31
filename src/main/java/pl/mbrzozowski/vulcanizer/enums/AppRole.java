package pl.mbrzozowski.vulcanizer.enums;

import java.util.List;
import java.util.Set;

import static pl.mbrzozowski.vulcanizer.constant.Authority.*;

public enum AppRole {
    ROLE_USER(USER_AUTHORITIES),
    ROLE_BUSINESS_OWNER(BUSINESS_OWNER_AUTHORITIES),
    ROLE_BUSINESS_EMPLOYEE(BUSINESS_EMPLOYEE_AUTHORITIES),
    ROLE_BUSINESS_MANAGER(BUSINESS_MANAGER_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES),
    ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES);

    private final Set<String> authorities;

    AppRole(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }
}

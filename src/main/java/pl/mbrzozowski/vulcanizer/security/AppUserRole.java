package pl.mbrzozowski.vulcanizer.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static pl.mbrzozowski.vulcanizer.security.AppUserPermission.USERS_GET;

public enum AppUserRole {
    ADMIN(Sets.newHashSet(USERS_GET)),
    USER(Sets.newHashSet()),
    BUSINESS_OWNER(Sets.newHashSet(USERS_GET)),
    BUSINESS_EMPLOYEE(Sets.newHashSet(USERS_GET));

    private final Set<AppUserPermission> permissions;

    AppUserRole(Set<AppUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<AppUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorityList() {
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = getPermissions()
                .stream()
                .map(permissions -> new SimpleGrantedAuthority(permissions.name()))
                .collect(Collectors.toSet());
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return simpleGrantedAuthorities;
    }
}

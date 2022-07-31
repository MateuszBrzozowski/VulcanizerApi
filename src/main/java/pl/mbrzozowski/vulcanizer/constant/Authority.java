package pl.mbrzozowski.vulcanizer.constant;

import java.util.*;

import static pl.mbrzozowski.vulcanizer.enums.Authorities.USER;

public class Authority {
    public static final Set<String> USER_AUTHORITIES = new HashSet<>(Collections.singletonList(USER.getAuthority())) {
    };

    public static final Set<String> BUSINESS_OWNER_AUTHORITIES = new HashSet<>();
    public static final Set<String> BUSINESS_MANAGER_AUTHORITIES = new HashSet<>();
    public static final Set<String> BUSINESS_EMPLOYEE_AUTHORITIES = new HashSet<>();
    public static final Set<String> ADMIN_AUTHORITIES = new HashSet<>();
    public static final Set<String> SUPER_ADMIN_AUTHORITIES = new HashSet<>();

}

package pl.mbrzozowski.vulcanizer.constant;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static pl.mbrzozowski.vulcanizer.enums.Authorities.USER;

public class Authority {
    public static final Set<String> USER_AUTHORITIES = new
            HashSet<>(Collections.singletonList(USER.getAuthority()));
    public static final Set<String> ADMIN_AUTHORITIES = new HashSet<>();
    public static final Set<String> SUPER_ADMIN_AUTHORITIES = new HashSet<>();

}

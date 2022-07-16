package pl.mbrzozowski.vulcanizer.entity.converter;

import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;

public class UserStatusAccountConverter {

    public static UserStatusAccount convert(String status) {
        if (status.equalsIgnoreCase(UserStatusAccount.NOT_ACTIVATED.name())) {
            return UserStatusAccount.NOT_ACTIVATED;
        } else if (status.equalsIgnoreCase(UserStatusAccount.ACTIVATED.name())) {
            return UserStatusAccount.ACTIVATED;
        } else if (status.equalsIgnoreCase(UserStatusAccount.BLOCKED.name())) {
            return UserStatusAccount.BLOCKED;
        } else if (status.equalsIgnoreCase(UserStatusAccount.SUSPENDED.name())) {
            return UserStatusAccount.SUSPENDED;
        } else {
            throw new IllegalArgumentException("Status is not correct.");
        }
    }

    public static String convert(UserStatusAccount status) {
        switch (status) {
            case NOT_ACTIVATED -> {
                return UserStatusAccount.NOT_ACTIVATED.name();
            }
            case ACTIVATED -> {
                return UserStatusAccount.ACTIVATED.name();
            }
            case BLOCKED -> {
                return UserStatusAccount.BLOCKED.name();
            }
            case SUSPENDED -> {
                return UserStatusAccount.SUSPENDED.name();
            }
            default -> throw new IllegalArgumentException("Status is not correct.");
        }
    }
}

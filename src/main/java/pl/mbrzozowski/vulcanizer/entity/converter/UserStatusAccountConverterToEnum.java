package pl.mbrzozowski.vulcanizer.entity.converter;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;

class UserStatusAccountConverterToEnum implements Converter<String, UserStatusAccount> {
    @Override
    public UserStatusAccount convert(String status) {
        if (status == null) {
            return null;
        }
        if (status.equalsIgnoreCase(UserStatusAccount.NOT_ACTIVATED.name())) {
            return UserStatusAccount.NOT_ACTIVATED;
        } else if (status.equalsIgnoreCase(UserStatusAccount.ACTIVATED.name())) {
            return UserStatusAccount.ACTIVATED;
        } else if (status.equalsIgnoreCase(UserStatusAccount.BLOCKED.name())) {
            return UserStatusAccount.BLOCKED;
        } else if (status.equalsIgnoreCase(UserStatusAccount.SUSPENDED.name())) {
            return UserStatusAccount.SUSPENDED;
        } else {
            throw new IllegalArgumentException("Status is not correct.[]");
        }
    }
}

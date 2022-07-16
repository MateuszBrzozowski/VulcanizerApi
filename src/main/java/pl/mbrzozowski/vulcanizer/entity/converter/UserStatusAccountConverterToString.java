package pl.mbrzozowski.vulcanizer.entity.converter;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;

class UserStatusAccountConverterToString implements Converter<UserStatusAccount,String> {
    @Override
    public String convert(UserStatusAccount status) {
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

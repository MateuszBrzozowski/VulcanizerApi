package pl.mbrzozowski.vulcanizer.enums.converter;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;

class UserStatusAccountConverterToStringFromInt implements Converter<Integer, String> {
    @Override
    public String convert(Integer source) {
        switch (source) {
            case 0 -> {
                return UserStatusAccount.NOT_ACTIVATED.name();
            }
            case 1 -> {
                return UserStatusAccount.ACTIVATED.name();
            }
            case 2 -> {
                return UserStatusAccount.BLOCKED.name();
            }
            case 3 -> {
                return UserStatusAccount.SUSPENDED.name();
            }
            default -> throw new IllegalArgumentException(
                    "Status is not correct. Use int for [0 = NOT_ACTIVATED, 1 = ACTIVATED, 2 = BLOCKED, 3 = SUSPENDED]");
        }
    }
}

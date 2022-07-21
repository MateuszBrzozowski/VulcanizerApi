package pl.mbrzozowski.vulcanizer.enums.converter;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;

class UserStatusAccountIntToEnum implements Converter<Integer, UserStatusAccount> {
    @Override
    public UserStatusAccount convert(Integer source) {
        switch (source) {
            case 0 -> {
                return UserStatusAccount.NOT_ACTIVATED;
            }
            case 1 -> {
                return UserStatusAccount.ACTIVATED;
            }
            case 2 -> {
                return UserStatusAccount.BLOCKED;
            }
            case 3 -> {
                return UserStatusAccount.SUSPENDED;
            }
            default -> throw new IllegalArgumentException(
                    "Status is not correct. Use int for [0 = NOT_ACTIVATED, 1 = ACTIVATED, 2 = BLOCKED, 3 = SUSPENDED]");
        }
    }
}

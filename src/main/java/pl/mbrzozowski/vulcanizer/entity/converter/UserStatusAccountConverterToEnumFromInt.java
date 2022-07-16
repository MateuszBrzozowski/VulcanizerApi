package pl.mbrzozowski.vulcanizer.entity.converter;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;

public class UserStatusAccountConverterToEnumFromInt implements Converter<Integer, UserStatusAccount> {
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
                    "Status is not correct. Use by name [NOT_ACTIVATED,ACTIVATED,BLOCKED,SUSPENDED] " +
                            "or iteration [0..3]");
        }
    }
}

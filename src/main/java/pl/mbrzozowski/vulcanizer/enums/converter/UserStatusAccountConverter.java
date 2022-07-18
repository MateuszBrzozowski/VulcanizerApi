package pl.mbrzozowski.vulcanizer.enums.converter;

import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;

public class UserStatusAccountConverter {

    public static String convert(Integer status) {
        return new UserStatusAccountConverterToStringFromInt().convert(status);
    }
}

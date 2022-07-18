package pl.mbrzozowski.vulcanizer.enums.converter;

import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;

public class UserStatusAccountConverter {

    public static UserStatusAccount convert(String status) {
        return new UserStatusAccountConverterToEnum().convert(status);
    }

    public static String convert(UserStatusAccount status) {
        return new UserStatusAccountConverterToString().convert(status);
    }

    public static String convert(Integer status) {
        return new UserStatusAccountConverterToStringFromInt().convert(status);
    }
}

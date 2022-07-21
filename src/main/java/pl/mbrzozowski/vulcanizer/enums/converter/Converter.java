package pl.mbrzozowski.vulcanizer.enums.converter;

import pl.mbrzozowski.vulcanizer.enums.Gender;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;

public class Converter {

    /**
     * @param number of Enum
     * @return UserStatusAccount
     * @see UserStatusAccount
     */
    public static UserStatusAccount convertIntToUserStatusAccount(Integer number) {
        return new UserStatusAccountIntToEnum().convert(number);
    }

    /**
     * @param number of Enum
     * @return Gender
     * @see Gender
     */
    public static Gender convertIntToGender(int number) {
        return new GenderConverterToStringFromInt().convert(number);
    }
}

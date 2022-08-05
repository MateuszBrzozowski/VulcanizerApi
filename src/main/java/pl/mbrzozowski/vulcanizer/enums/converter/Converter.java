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
     * @param gender as string
     * @return gender as enum {@link Gender}
     */
    public static Gender convertStringToGender(String gender) {
        return new GenderConverterFromStringToEnum().convert(gender);
    }
}

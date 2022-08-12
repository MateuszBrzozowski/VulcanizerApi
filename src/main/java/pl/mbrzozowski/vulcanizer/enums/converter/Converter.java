package pl.mbrzozowski.vulcanizer.enums.converter;

import pl.mbrzozowski.vulcanizer.enums.Gender;

public class Converter {

    /**
     * @param gender as string
     * @return gender as enum {@link Gender}
     */
    public static Gender convertStringToGender(String gender) {
        return new GenderConverterFromStringToEnum().convert(gender);
    }

    public static String getCompanyBranchStatus(boolean isActive, boolean isLocked, boolean isClosed) {
        return new CompanyStatusString().convert(isActive, isLocked, isClosed);
    }
}

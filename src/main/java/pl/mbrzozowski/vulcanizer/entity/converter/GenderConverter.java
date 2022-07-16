package pl.mbrzozowski.vulcanizer.entity.converter;

import pl.mbrzozowski.vulcanizer.enums.Gender;

public class GenderConverter {

    public static Gender convert(String gender) {
        return new GenderConverterToEnum().convert(gender);
    }

    public static String convert(Gender gender) {
        return new GenderConverterToString().convert(gender);
    }

    public static Gender convert(int gender) {
        return new GenderConverterToEnumFromInt().convert(gender);
    }
}

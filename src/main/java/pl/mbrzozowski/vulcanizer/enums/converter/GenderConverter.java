package pl.mbrzozowski.vulcanizer.enums.converter;

public class GenderConverter {

    public static String convert(int gender) {
        return new GenderConverterToStringFromInt().convert(gender);
    }
}

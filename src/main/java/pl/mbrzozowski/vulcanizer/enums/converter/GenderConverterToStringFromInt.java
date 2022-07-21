package pl.mbrzozowski.vulcanizer.enums.converter;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.enums.Gender;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;

class GenderConverterToStringFromInt implements Converter<Integer, Gender> {
    @Override
    public Gender convert(Integer source) {
        switch (source) {
            case 0 -> {
                return Gender.UNDEFINED;
            }
            case 1 -> {
                return Gender.MALE;
            }
            case 2 -> {
                return Gender.FEMALE;
            }
            default -> throw new IllegalArgumentException(
                    "Gender is not correct. Use int for [0 = UNDEFINED, 1 = MALE, 2 = FEMALE]");
        }
    }
}

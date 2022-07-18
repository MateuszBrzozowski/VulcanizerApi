package pl.mbrzozowski.vulcanizer.enums.converter;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.enums.Gender;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;

public class GenderConverterToStringFromInt implements Converter<Integer, String> {
    @Override
    public String convert(Integer source) {
        switch (source) {
            case 0 -> {
                return Gender.UNDEFINED.name();
            }
            case 1 -> {
                return Gender.MALE.name();
            }
            case 2 -> {
                return Gender.FEMALE.name();
            }
            default -> throw new IllegalArgumentException(
                    "Gender is not correct. Use int for [0 = UNDEFINED, 1 = MALE, 2 = FEMALE]");
        }
    }
}

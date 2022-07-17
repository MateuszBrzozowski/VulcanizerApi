package pl.mbrzozowski.vulcanizer.enums.converter;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.enums.Gender;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;

public class GenderConverterToEnumFromInt implements Converter<Integer, Gender> {
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
                    "Gender is not correct. Use by name [UNDEFINED,MALE,FEMALE] or iteration [0..2]");
        }
    }
}

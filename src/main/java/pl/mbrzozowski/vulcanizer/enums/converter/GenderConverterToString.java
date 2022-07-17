package pl.mbrzozowski.vulcanizer.enums.converter;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.enums.Gender;

class GenderConverterToString implements Converter<Gender, String> {
    @Override
    public String convert(Gender source) {
        switch (source) {
            case UNDEFINED -> {
                return Gender.UNDEFINED.name();
            }
            case MALE -> {
                return Gender.MALE.name();
            }
            case FEMALE -> {
                return Gender.FEMALE.name();
            }
            default -> throw new IllegalArgumentException("Gender is not correct");
        }
    }
}

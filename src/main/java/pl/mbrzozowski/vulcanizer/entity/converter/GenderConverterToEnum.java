package pl.mbrzozowski.vulcanizer.entity.converter;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.enums.Gender;

class GenderConverterToEnum implements Converter<String, Gender> {
    @Override
    public Gender convert(String source) {
        if (source == null) {
            return Gender.UNDEFINED;
        }
        if (source.equalsIgnoreCase(Gender.FEMALE.name())) {
            return Gender.FEMALE;
        } else if (source.equalsIgnoreCase(Gender.MALE.name())) {
            return Gender.MALE;
        } else {
            return Gender.UNDEFINED;
        }
    }
}

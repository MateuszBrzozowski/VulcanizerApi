package pl.mbrzozowski.vulcanizer.enums.converter;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.enums.Gender;

class GenderConverterFromStringToEnum implements Converter<String, Gender> {
    @Override
    public Gender convert(String source) {
        if (source.equalsIgnoreCase(Gender.MALE.name())) {
            return Gender.MALE;
        }
        if(source.equalsIgnoreCase(Gender.FEMALE.name())){
            return Gender.FEMALE;
        }
        if(source.equalsIgnoreCase(Gender.UNDEFINED.name())){
            return Gender.UNDEFINED;
        }
        throw new IllegalArgumentException(String.format("Gender like %s is not correct",source));
    }
}

package pl.mbrzozowski.vulcanizer.dto.mapper;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.dto.PhoneResponse;
import pl.mbrzozowski.vulcanizer.entity.Phone;

public class PhoneToPhoneResponse implements Converter<Phone, PhoneResponse> {
    @Override
    public PhoneResponse convert(Phone source) {
        return new PhoneResponse(source.getNumber());
    }
}

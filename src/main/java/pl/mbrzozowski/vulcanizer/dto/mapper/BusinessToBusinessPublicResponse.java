package pl.mbrzozowski.vulcanizer.dto.mapper;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.dto.BusinessPublicResponse;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.Phone;

import java.util.stream.Collectors;

public class BusinessToBusinessPublicResponse implements Converter<Business, BusinessPublicResponse> {

    @Override
    public BusinessPublicResponse convert(Business source) {
        BusinessPublicResponse result = BusinessPublicResponse.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .phones(source.getPhones()
                        .stream()
                        .map(Phone::getNumber)
                        .collect(Collectors.toSet()))
                .address(new AddressToAddressResponse().convert(source.getAddress()))
                .build();
        if (source.getPhoto() != null) {
            result.setPhoto(source.getPhoto().getUrl());
        }
        return result;
    }
}

package pl.mbrzozowski.vulcanizer.dto.mapper;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.dto.AddressResponse;
import pl.mbrzozowski.vulcanizer.entity.Address;

import java.util.function.Function;

public class AddressToAddressResponse implements Converter<Address, AddressResponse> {

    @Override
    public AddressResponse convert(Address address) {
        if (address == null) {
            return null;
        }
        AddressResponse addressResponse = AddressResponse.builder()
                .id(address.getId())
                .addressLine(address.getAddressLine())
                .code(address.getCode())
                .city(address.getCity())
                .country(address.getCountry())
                .build();
        if (address.getState() != null) {
            addressResponse.setState(address.getState().getName());
        }
        return addressResponse;
    }
}

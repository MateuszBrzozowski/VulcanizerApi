package pl.mbrzozowski.vulcanizer.dto.mapper;

import pl.mbrzozowski.vulcanizer.dto.AddressResponse;
import pl.mbrzozowski.vulcanizer.entity.Address;

import java.util.function.Function;

public class AddressToAddressResponse implements Function<Address, AddressResponse> {

    @Override
    public AddressResponse apply(Address address) {
        if (address == null) {
            return null;
        }
        AddressResponse addressResponse = AddressResponse.builder()
                .id(address.getId())
                .addressLineOne(address.getAddressLine())
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

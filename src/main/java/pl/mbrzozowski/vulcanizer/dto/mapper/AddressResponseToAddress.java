package pl.mbrzozowski.vulcanizer.dto.mapper;

import lombok.RequiredArgsConstructor;
import pl.mbrzozowski.vulcanizer.dto.AddressResponse;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.service.StateService;

import java.util.function.Function;

@RequiredArgsConstructor
public class AddressResponseToAddress implements Function<AddressResponse, Address> {
    private final StateService stateService;

    @Override
    public Address apply(AddressResponse addressResponse) {
        State state = null;
        try {
            state = stateService.findByName(addressResponse.getState());
        } catch (IllegalArgumentException ignored) {
        }
        return Address.builder()
                .id(addressResponse.getId())
                .addressLineOne(addressResponse.getAddressLineOne())
                .addressLineTwo(addressResponse.getAddressLineTwo())
                .city(addressResponse.getCity())
                .code(addressResponse.getCode())
                .state(state)
                .build();
    }
}

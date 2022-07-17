package pl.mbrzozowski.vulcanizer.dto.mapper;

import lombok.RequiredArgsConstructor;
import pl.mbrzozowski.vulcanizer.dto.AddressRequest;
import pl.mbrzozowski.vulcanizer.dto.StateResponse;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.service.StateService;

import java.util.function.Function;

@RequiredArgsConstructor
public class AddressRequestToAddress implements Function<AddressRequest, Address> {
    private final StateService stateService;

    @Override
    public Address apply(AddressRequest addressRequest) {
        String stateName = addressRequest.getStateName();
        State state = null;
        if (stateName != null) {
            StateResponse stateResponse = stateService.findByName(stateName);
            state = new StateResponseToState().apply(stateResponse);
        }
        return Address.builder()
                .id(addressRequest.getId())
                .addressLineOne(addressRequest.getAddressLineOne())
                .addressLineTwo(addressRequest.getAddressLineTwo())
                .city(addressRequest.getCity())
                .code(addressRequest.getCode())
                .state(state)
                .build();
    }
}

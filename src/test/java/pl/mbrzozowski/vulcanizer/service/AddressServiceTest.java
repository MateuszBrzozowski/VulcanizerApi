package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.AddressRequest;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.exceptions.NullPointerException;
import pl.mbrzozowski.vulcanizer.repository.AddressRepository;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

class AddressServiceTest {
    private AddressService addressService;
    private AddressRepository addressRepository;
    private StateRepository stateRepository;
    private StateService stateService;
    private final String CITY = "Warszawa";
    private final String STATE_NAME = "Mazowieckie";
    private final State state = new State(1L, STATE_NAME, null);

    @BeforeEach
    public void beforeEach() {
        addressRepository = mock(AddressRepository.class);
        stateRepository = mock(StateRepository.class);
        stateService = new StateService(stateRepository);
        addressService = new AddressService(addressRepository, stateService);
    }

    @Test
    public void save_SaveAddressCorectly() {
        AddressRequest addressRequest = AddressRequest.builder()
                .city(CITY)
                .build();
        Address address = Address.builder()
                .city(CITY)
                .build();
        when(stateService.findByName(null)).thenReturn(null);
        addressService.save(addressRequest);
        verify(addressRepository).save(address);
    }

    @Test
    public void save_AllVariableNull_ThrowNullPointerException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .build();
        Assertions.assertThrows(NullPointerException.class, () -> addressService.save(addressRequest));
    }

    @Test
    void save_OnlyState_Correct() {
        AddressRequest addressRequest = AddressRequest.builder()
                .stateName(state.getName())
                .build();
        Address address = Address.builder()
                .state(state)
                .build();
        when(stateRepository.findByName(STATE_NAME)).thenReturn(Optional.of(state));
        addressService.save(addressRequest);
        verify(addressRepository).save(address);
    }

    @Test
    void save_OnlyAddressLineOne_Correct() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLineOne("Line One")
                .build();
        Address address = Address.builder()
                .addressLineOne("Line One")
                .build();
        addressService.save(addressRequest);
        verify(addressRepository).save(address);
    }

    @Test
    void save_OnlyAddressLineTwo_Correct() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLineTwo("Line Two")
                .build();
        Address address = Address.builder()
                .addressLineTwo("Line Two")
                .build();
        addressService.save(addressRequest);
        verify(addressRepository).save(address);
    }

}
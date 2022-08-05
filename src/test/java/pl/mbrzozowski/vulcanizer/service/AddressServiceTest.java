package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.AddressRequest;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.repository.AddressRepository;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;
import pl.mbrzozowski.vulcanizer.util.StringGenerator;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class AddressServiceTest {
    private AddressService addressService;
    private AddressRepository addressRepository;
    private StateRepository stateRepository;
    private final String CITY = "Warszawa";
    private final String STATE_NAME = "Mazowieckie";
    private final State state = new State(1L, STATE_NAME, null);

    @BeforeEach
    public void beforeEach() {
        addressRepository = mock(AddressRepository.class);
        stateRepository = mock(StateRepository.class);
        StateService stateService = new StateService(stateRepository);
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
        when(stateRepository.findByName(null)).thenReturn(Optional.empty());
        addressService.save(addressRequest);
        verify(addressRepository).save(address);
    }
    @Test
    void save_OnlyState_Correct() {
        AddressRequest addressRequest = AddressRequest.builder()
                .state(state.getName())
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
                .addressLine("Line One")
                .build();
        Address address = Address.builder()
                .addressLine("Line One")
                .build();
        addressService.save(addressRequest);
        verify(addressRepository).save(address);
    }

    @Test
    void save_OnlyCity_Correct() {
        AddressRequest addressRequest = AddressRequest.builder()
                .city(CITY)
                .build();
        Address address = Address.builder()
                .city(CITY)
                .build();
        addressService.save(addressRequest);
        verify(addressRepository).save(address);
    }

    @Test
    void save_OnlyPostalCode_Correct() {
        AddressRequest addressRequest = AddressRequest.builder()
                .code("00-000")
                .build();
        Address address = Address.builder()
                .code("00-000")
                .build();
        addressService.save(addressRequest);
        verify(addressRepository).save(address);
    }

    @Test
    void save_NoSuchStateNameAndRestNull_ThrowNoSuchElementException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .state("NotCorrect")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> addressService.save(addressRequest));
    }

    @Test
    void save_StateIsCorrecteAndRestNull_DoesNotThrow() {
        String stateName = "Mazowieckie";
        AddressRequest addressRequest = AddressRequest.builder()
                .state(stateName)
                .build();
        State state = State.builder()
                .id(1L)
                .name(stateName)
                .build();
        when(stateRepository.findByName(stateName)).thenReturn(Optional.of(state));
        Assertions.assertDoesNotThrow(() -> addressService.save(addressRequest));
    }


    @Test
    void save_MaxLengthLineTwo_DoesNotThrow() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLine(new StringGenerator().apply(100))
                .build();
        Assertions.assertDoesNotThrow(() -> addressService.save(addressRequest));
    }

    @Test
    void save_MaxLengthCityName_DoesNotThrow() {
        AddressRequest addressRequest = AddressRequest.builder()
                .city(new StringGenerator().apply(40))
                .build();
        Assertions.assertDoesNotThrow(() -> addressService.save(addressRequest));
    }
    @Test
    void save_MaxLengthCode_DoesNotThrow() {
        AddressRequest addressRequest = AddressRequest.builder()
                .code(new StringGenerator().apply(6))
                .build();
        Assertions.assertDoesNotThrow(() -> addressService.save(addressRequest));
    }

    @Test
    void findById_FoundAddress_DoesNotThrow() {
        long id = 1L;
        Address address = Address.builder()
                .id(id)
                .city(CITY)
                .state(new State(1L, STATE_NAME))
                .build();
        when(addressRepository.findById(id)).thenReturn(Optional.of(address));
        Assertions.assertDoesNotThrow(() -> addressService.findById(id));
    }

    @Test
    void findById_DoesNotFoundAddress_ThrowNoSuchElementException() {
        long id = 1L;
        when(addressRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> addressService.findById(id));
    }

    @Test
    void findById_IdIsNull_ThrowNoSuchElementException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> addressService.findById(null));
    }

    @Test
    void findAll_FoundAddress_DoesNotThrow() {
        Address address = Address.builder()
                .id(1L)
                .city(CITY)
                .state(new State(1L, STATE_NAME))
                .build();
        Address addressTwo = Address.builder()
                .id(2L)
                .city(CITY)
                .state(new State(1L, STATE_NAME))
                .build();
        when(addressRepository.findAll()).thenReturn(List.of(address, addressTwo));
        Assertions.assertDoesNotThrow(() -> addressService.findAll());
    }

    @Test
    void findAll_DoesNotFoundAddresses_DoesNotThrow() {
        when(addressRepository.findAll()).thenReturn(List.of());
        Assertions.assertDoesNotThrow(() -> addressService.findAll());
    }

    @Test
    void update_AllParametersEmpty_ThrowNoSuchElementException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .id(1L)
                .addressLine("")
                .city("")
                .code("")
                .state("")
                .build();
        when(stateRepository.findByName("")).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> addressService.update(addressRequest));
    }

    @Test
    void update_IdIsNull_ThrowNoSuchElementException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .city(CITY)
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> addressService.update(addressRequest));
    }

    @Test
    void update_executed() {
        AddressRequest addressRequest = AddressRequest.builder()
                .id(1L)
                .city(CITY)
                .build();
        Address address = Address.builder()
                .id(null)
                .city(CITY)
                .build();
        addressService.save(addressRequest);
        verify(addressRepository).save(address);
    }

    @Test
    void delete_IdIsNull_ThrowNoSuchElementException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> addressService.deleteById(null));
    }

    @Test
    void delete_DoesNotFoundAddress_ThrowNoSuchElementException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> addressService.deleteById(1L));
    }

    @Test
    void update_NullStateName_DoesNotThrowException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .id(1L)
                .city(CITY)
                .state(null)
                .build();
        Address address = Address.builder()
                .id(1L)
                .state(new State(1L, STATE_NAME))
                .build();
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        Assertions.assertDoesNotThrow(() -> addressService.update(addressRequest));
    }

    @Test
    void update_EmptyStateName_DoesNotThrowException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .id(1L)
                .city(CITY)
                .state("")
                .build();
        Address address = Address.builder()
                .id(1L)
                .state(new State(1L, STATE_NAME))
                .build();
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        Assertions.assertDoesNotThrow(() -> addressService.update(addressRequest));
    }
}
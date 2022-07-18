package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.AddressRequest;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.exceptions.NoSuchElementException;
import pl.mbrzozowski.vulcanizer.exceptions.NullParameterException;
import pl.mbrzozowski.vulcanizer.repository.AddressRepository;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;

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
    public void save_AllVariableNull_ThrowNullPointerException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .build();
        Assertions.assertThrows(NullParameterException.class, () -> addressService.save(addressRequest));
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
    void save_EmptyAddressLineOneAndRestNull_ThrowIllegalArgumentException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLineOne("")
                .build();
        Assertions.assertThrows(NullParameterException.class, () -> addressService.save(addressRequest));
    }

    @Test
    void save_EmptyAddressLineTwoAndRestNull_ThrowIllegalArgumentException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLineTwo("")
                .build();
        Assertions.assertThrows(NullParameterException.class, () -> addressService.save(addressRequest));
    }

    @Test
    void save_EmptyCityAndRestNull_ThrowIllegalArgumentException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .city("")
                .build();
        Assertions.assertThrows(NullParameterException.class, () -> addressService.save(addressRequest));
    }

    @Test
    void save_EmptyCodeAndRestNull_ThrowIllegalArgumentException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .code("")
                .build();
        Assertions.assertThrows(NullParameterException.class, () -> addressService.save(addressRequest));
    }

    @Test
    void save_EmptyStateNameAndRestNull_ThrowNoSuchElementException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .state("")
                .build();
        Assertions.assertThrows(NullParameterException.class, () -> addressService.save(addressRequest));
    }

    @Test
    void save_NoSuchStateNameAndRestNull_ThrowNoSuchElementException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .state("NotCorrect")
                .build();
        Assertions.assertThrows(NoSuchElementException.class, () -> addressService.save(addressRequest));
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
    void save_EmptyAllAddress_ThrowNoSuchElementException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLineOne("")
                .addressLineTwo("")
                .city("")
                .code("")
                .state("")
                .build();
        Assertions.assertThrows(NullParameterException.class, () -> addressService.save(addressRequest));
    }

    @Test
    void save_EmptyAllAddressAndNullState_ThrowIllegalArgumentException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLineOne("")
                .addressLineTwo("")
                .city("")
                .code("")
                .build();
        Assertions.assertThrows(NullParameterException.class, () -> addressService.save(addressRequest));
    }

    @Test
    void save_AllEmptyOrNullArguments_ThrowNullParameterException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLineOne("")
                .addressLineTwo(null)
                .city(null)
                .code("")
                .build();
        Assertions.assertThrows(NullParameterException.class, () -> addressService.save(addressRequest));
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
        Assertions.assertThrows(NoSuchElementException.class, () -> addressService.findById(id));
    }

    @Test
    void findById_IdIsNull_ThrowNoSuchElementException() {
        Assertions.assertThrows(NoSuchElementException.class, () -> addressService.findById(null));
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

    @SuppressWarnings("ConstantConditions")
    @Test
    void update_AllParametersNull_ThrowNullParameterException() {
        AddressRequest addressRequest = AddressRequest.builder().build();
        Address address = Address.builder().build();
        when(addressRepository.findById(null)).thenReturn(Optional.of(address));
        Assertions.assertThrows(NullParameterException.class, () -> addressService.update(addressRequest));
    }

    @Test
    void update_AllParametersEmpty_ThrowNoSuchElementException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .id(1L)
                .addressLineOne("")
                .addressLineTwo("")
                .city("")
                .code("")
                .state("")
                .build();
        when(stateRepository.findByName("")).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> addressService.update(addressRequest));
    }

    @Test
    void update_AllParametersWithoutStateEmpty_ThrowIllegalArgumentException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .id(1L)
                .addressLineOne("")
                .addressLineTwo("")
                .city("")
                .code("")
                .build();
        Address address = Address.builder()
                .id(1L)
                .build();
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        Assertions.assertThrows(NullParameterException.class, () -> addressService.update(addressRequest));
    }

    @Test
    void update_IdIsNull_ThrowNoSuchElementException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .city(CITY)
                .build();
        Assertions.assertThrows(NoSuchElementException.class, () -> addressService.update(addressRequest));
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
        Assertions.assertThrows(NoSuchElementException.class, () -> addressService.deleteById(null));
    }

    @Test
    void delete_DoesNotFoundAddress_ThrowNoSuchElementException() {
        Assertions.assertThrows(NoSuchElementException.class, () -> addressService.deleteById(1L));
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
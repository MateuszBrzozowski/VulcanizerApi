package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.AddressRequest;
import pl.mbrzozowski.vulcanizer.dto.BusinessCreateRequest;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.exceptions.NoSuchElementException;
import pl.mbrzozowski.vulcanizer.repository.BusinessRepository;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;
import pl.mbrzozowski.vulcanizer.repository.UserRepository;
import pl.mbrzozowski.vulcanizer.util.StringGenerator;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BusinessServiceTest {
    private BusinessService businessService;
    private StateRepository stateRepository;
    private StateService stateService;
    private UserRepository userRepository;
    private UserService userService;


//    Business business = Business.builder()
//            .id(10L)
//            .name("Name")
//            .nip("0123456789")
//            .description("description")
//            .address(address)
//            .photo(photo)
//            .build();

//    Photo photo = new Photo(10L, "url");

    @BeforeEach
    public void beforeEach() {
        userRepository = mock(UserRepository.class);
        BusinessRepository businessRepository = mock(BusinessRepository.class);
        stateRepository = mock(StateRepository.class);
        stateService = mock(StateService.class);
        userService = mock(UserService.class);
        PhotoService photoService = mock(PhotoService.class);
        AddressService addressService = mock(AddressService.class);
        EmployeeRoleService employeeRoleService = mock(EmployeeRoleService.class);
        EmployeeService employeeService = mock(EmployeeService.class);
        businessService = new BusinessService(businessRepository,
                photoService,
                stateRepository,
                stateService,
                addressService,
                userService,
                employeeRoleService,
                employeeService);
    }

    @Test
    void create_AllParamReq_Success() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertDoesNotThrow(() -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_NoIdUser_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .name("Name")
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_NoName_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_NoNip_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_NoDescription_DoesNotThrow() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertDoesNotThrow(() -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_NoAddress_ThrowIllegalArgumentException() {
        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("description")
                .photo("urlurl")
                .build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_NoPhoto_DoesNotThrow() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("description")
                .address(address)
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertDoesNotThrow(() -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_NoAddressLineOne_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_NoAddressCity_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_NoAddressCode_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_NoAddressState_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_NoAddressCountry_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_NoAddressNoState_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_IllegalNip_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("012345678")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_IllegalNipToLong_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("01234567899")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_NameToLong_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name(new StringGenerator().apply(256))
                .nip("01234567899")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_NameMaxLength_ThrowDoesNotThrow() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name(new StringGenerator().apply(255))
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertDoesNotThrow(() -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_DescriptionToLong_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        String description = new StringGenerator().apply(1001);
        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description(description)
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);
        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_MaxLengthDescription_DoesNotThrow() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description(new StringGenerator().apply(1000))
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertDoesNotThrow(() -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_MaxLCityNameLength_DoesNotThrow() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city(new StringGenerator().apply(40))
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description(new StringGenerator().apply(1000))
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertDoesNotThrow(() -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_CityNameToLong_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city(new StringGenerator().apply(41))
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description(new StringGenerator().apply(1000))
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_NotValidPostalCodeLong_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-9999")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description(new StringGenerator().apply(1000))
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_NotValidPostalCodeShort_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-99")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description(new StringGenerator().apply(1000))
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_CountryToLong_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-99")
                .state("state")
                .country(new StringGenerator().apply(51))
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description(new StringGenerator().apply(1000))
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.create(businessCreateRequest));
    }

    @Test
    void create_UserIsNotExist_ThrowNoSuchElementException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessCreateRequest businessCreateRequest = BusinessCreateRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("Description")
                .address(address)
                .photo("urlurl")
                .build();
        when(stateService.findByName("state")).thenReturn(state);
        when(userService.findById(20L)).thenThrow(NoSuchElementException.class);
        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));

        Assertions.assertThrows(NoSuchElementException.class, () -> businessService.create(businessCreateRequest));
    }

}
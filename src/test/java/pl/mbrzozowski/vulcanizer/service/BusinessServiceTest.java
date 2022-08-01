package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.AddressRequest;
import pl.mbrzozowski.vulcanizer.dto.BusinessRequest;
import pl.mbrzozowski.vulcanizer.entity.Phone;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.repository.BusinessRepository;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;
import pl.mbrzozowski.vulcanizer.repository.UserRepository;
import pl.mbrzozowski.vulcanizer.util.StringGenerator;

import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BusinessServiceTest {
    private BusinessService businessService;
    private StateRepository stateRepository;
    private StateService stateService;
    private UserServiceImpl userService;


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
        UserRepository userRepository = mock(UserRepository.class);
        BusinessRepository businessRepository = mock(BusinessRepository.class);
        stateRepository = mock(StateRepository.class);
        stateService = mock(StateService.class);
        userService = mock(UserServiceImpl.class);
        PhotoService photoService = mock(PhotoService.class);
        PhoneService phoneService = mock(PhoneService.class);
        AddressService addressService = mock(AddressService.class);
        EmployeeService employeeService = mock(EmployeeService.class);
        businessService = new BusinessService(businessRepository,
                photoService,
                phoneService,
                stateRepository,
                stateService,
                addressService,
                userService,
                employeeService);
    }

    @Test
    void save_AllParamReq_Success() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        HashSet<Phone> phones = new HashSet<>();
        phones.add(new Phone("1111"));
        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .phones(phones)
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertDoesNotThrow(() -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_NoIdUser_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .name("Name")
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_NoName_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_NoNip_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_NoDescription_DoesNotThrow() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        HashSet<Phone> phones = new HashSet<>();
        phones.add(new Phone("1111"));
        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .address(address)
                .photo("urlurl")
                .phones(phones)
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertDoesNotThrow(() -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_NoAddress_ThrowIllegalArgumentException() {
        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("description")
                .photo("urlurl")
                .build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_NoPhoto_DoesNotThrow() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        HashSet<Phone> set = new HashSet<>();
        set.add(new Phone("1111"));
        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("description")
                .address(address)
                .phones(set)
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertDoesNotThrow(() -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_NoAddressLineOne_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_NoAddressCity_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_NoAddressCode_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .state("state")
                .country("Country")
                .build();

        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_NoAddressState_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .country("Country")
                .build();

        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_NoAddressCountry_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .build();

        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_NoAddressNoState_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .country("Country")
                .build();

        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_IllegalNip_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("012345678")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_IllegalNipToLong_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("01234567899")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_NameToLong_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name(new StringGenerator().apply(256))
                .nip("01234567899")
                .description("description")
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_NameMaxLength_ThrowDoesNotThrow() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        HashSet<Phone> phones = new HashSet<>();
        phones.add(new Phone("1111"));
        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name(new StringGenerator().apply(255))
                .nip("0123456789")
                .description("description")
                .address(address)
                .photo("urlurl")
                .phones(phones)
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertDoesNotThrow(() -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_DescriptionToLong_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        String description = new StringGenerator().apply(1001);
        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description(description)
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);
        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_MaxLengthDescription_DoesNotThrow() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        HashSet<Phone> phones = new HashSet<>();
        phones.add(new Phone("1111"));
        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description(new StringGenerator().apply(1000))
                .address(address)
                .photo("urlurl")
                .phones(phones)
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertDoesNotThrow(() -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_MaxLCityNameLength_DoesNotThrow() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city(new StringGenerator().apply(40))
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        HashSet<Phone> phones = new HashSet<>();
        phones.add(new Phone("1111"));
        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description(new StringGenerator().apply(1000))
                .address(address)
                .photo("urlurl")
                .phones(phones)
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertDoesNotThrow(() -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_CityNameToLong_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city(new StringGenerator().apply(41))
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description(new StringGenerator().apply(1000))
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_NotValidPostalCodeLong_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-9999")
                .state("state")
                .country("Country")
                .build();

        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description(new StringGenerator().apply(1000))
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_NotValidPostalCodeShort_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-99")
                .state("state")
                .country("Country")
                .build();

        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description(new StringGenerator().apply(1000))
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_CountryToLong_ThrowIllegalArgumentException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-99")
                .state("state")
                .country(new StringGenerator().apply(51))
                .build();

        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description(new StringGenerator().apply(1000))
                .address(address)
                .photo("urlurl")
                .build();

        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));
        when(stateService.findByName("state")).thenReturn(state);

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

    @Test
    void save_UserIsNotExist_ThrowNoSuchElementException() {
        State state = new State(10L, "state");
        AddressRequest address = AddressRequest.builder()
                .addressLineOne("Line One")
                .city("City")
                .code("99-999")
                .state("state")
                .country("Country")
                .build();

        BusinessRequest businessCreateRequest = BusinessRequest.builder()
                .userId(20L)
                .name("Name")
                .nip("0123456789")
                .description("Description")
                .address(address)
                .photo("urlurl")
                .build();
        when(stateService.findByName("state")).thenReturn(state);
        when(userService.findById(20L)).thenThrow(IllegalArgumentException.class);
        when(stateRepository.findByName("state")).thenReturn(Optional.of(state));

        Assertions.assertThrows(IllegalArgumentException.class, () -> businessService.save(businessCreateRequest));
    }

}
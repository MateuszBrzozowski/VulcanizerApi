package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;
import pl.mbrzozowski.vulcanizer.exceptions.EmailExistException;
import pl.mbrzozowski.vulcanizer.exceptions.NullParameterException;
import pl.mbrzozowski.vulcanizer.exceptions.UserWasNotFoundException;
import pl.mbrzozowski.vulcanizer.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private final String email = "username@domain.com";
    private final String password = "password";
    private final String firstName = "firstName";
    private final String lastName = "LastName";
    private final LocalDateTime createAccountTime = LocalDateTime.now();
    private UserService userService;
    private UserRepository userRepository;
    private final int USER_AGE = 6;

    @BeforeEach
    public void beforeEach() {
        userRepository = mock(UserRepository.class);
        userRepository = mock(UserRepository.class);
        StateService stateService = mock(StateService.class);
        AddressService addressService = mock(AddressService.class);
        userService = new UserService(userRepository, stateService, addressService);
    }

    @Test
    public void saveUser_ReqFieldByConstructor_addUser() {
        UserRequest user = new UserRequest(email, password, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> userService.save(user));
    }

    @Test
    public void saveUser_ReqFieldByBuilder_addUser() {
        UserRequest user = UserRequest.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        Assertions.assertDoesNotThrow(() -> userService.save(user));
    }

    @Test
    public void saveUser_ReqFieldByConstructorNoEmail_ThrowNullParameterException() {
        UserRequest user = new UserRequest(null, password, firstName, lastName);
        Assertions.assertThrows(NullParameterException.class, () -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByConstructorEmptyEmail_ThrowIllegalArgumentException() {
        UserRequest user = new UserRequest("", password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByBuilderNoEmail_ThrowNullParameterException() {
        UserRequest user = UserRequest.builder()
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        Assertions.assertThrows(NullParameterException.class, () -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByConstructorNoPass_ThrowNullParameterException() {
        UserRequest user = new UserRequest(email, null, firstName, lastName);
        Assertions.assertThrows(NullParameterException.class, () -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByConstructorEmptyPass_ThrowIllegalArgumentException() {
        UserRequest user = new UserRequest(email, "", firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByBuilderNoPass_ThrowNullParameterException() {
        UserRequest user = UserRequest.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        Assertions.assertThrows(NullParameterException.class, () -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByConstructorNoFirstName_ThrowNullParameterException() {
        UserRequest user = new UserRequest(email, password, null, lastName);
        Assertions.assertThrows(NullParameterException.class, () -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByConstructorEmptyFirstName_ThrowIllegalArgumentException() {
        UserRequest user = new UserRequest(email, password, "", lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByBuilderNoFirstName_ThrowNullParameterException() {
        UserRequest user = UserRequest.builder()
                .email(email)
                .password(password)
                .lastName(lastName)
                .build();
        Assertions.assertThrows(NullParameterException.class, () -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByConstructorNoLastName_ThrowNullParameterException() {
        UserRequest user = new UserRequest(email, password, firstName, null);
        Assertions.assertThrows(NullParameterException.class, () -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByConstructorEmptyLastName_ThrowIllegalArgumentException() {
        UserRequest user = new UserRequest(email, password, firstName, "");
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByBuilderNoLastName_ThrowNullParameterException() {
        UserRequest user = UserRequest.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .build();
        Assertions.assertThrows(NullParameterException.class, () -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByConstructorNoCreateTime_createUser() {
        UserRequest user = new UserRequest(email, password, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByBuilderNoCreateTime_createUser() {
        UserRequest user = UserRequest.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        Assertions.assertDoesNotThrow(() -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByConstructorAllNull_ThrowNullParameterException() {
        UserRequest user = new UserRequest(null, null, null, null);
        Assertions.assertThrows(NullParameterException.class, () -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByBuilderAllNull_ThrowNullParameterException() {
        UserRequest user = UserRequest.builder()
                .build();
        Assertions.assertThrows(NullParameterException.class, () -> userService.save(user));
    }

    @Test
    public void save_ValidateEmailNoMonkey_ThrowIllegalArgumentException() {
        String emailAddress = "usernamedomain.com";
        UserRequest user = new UserRequest(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    public void save_ValidateEmailUnicode_ThrowIllegalArgumentException() {
        String emailAddress = "用户名@领域.电脑";
        UserRequest user = new UserRequest(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    public void save_ValidateGmail_Valid() {
        String emailAddress = "username+something@domain.com";
        UserRequest user = new UserRequest(emailAddress, password, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> userService.save(user));
    }

    @Test
    public void save_ValidateToLongEmailUserNameMax64_ThrowIllegalArgumentException() {
        String emailAddress = "vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv" +
                "vvvvvvvvvvvvvvv@domain.com";
        UserRequest user = new UserRequest(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    public void save_ValidateEmptyUsernameEmail_ThrowIllegalArgumentException() {
        String emailAddress = "@domain.com";
        UserRequest user = new UserRequest(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    public void save_ValidateDomainEndingByPointer_ThrowIllegalArgumentException() {
        String emailAddress = "username@domain.";
        UserRequest user = new UserRequest(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    public void save_ValidateDomainStartingByPointer_ThrowIllegalArgumentException() {
        String emailAddress = "username@.com";
        UserRequest user = new UserRequest(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    public void save_ValidateEmptyDomainEmail_ThrowIllegalArgumentException() {
        String emailAddress = "username@";
        UserRequest user = new UserRequest(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    public void save_ValidateDomainStartingByPointerr_ThrowIllegalArgumentException() {
        String emailAddress = "mateusz_brzozowski93@wp.pl";
        UserRequest user = new UserRequest(emailAddress, password, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> userService.save(user));
    }

    @Test
    void save_ToLongPass_ThrowIllegalArgumentException() {
        String password_fake = "ZbytDlugieHasloMaksymalnie30znakowMozeBycTylko";
        UserRequest user = new UserRequest(email, password_fake, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    void save_ToLongFirstName_ThrowIllegalArgumentException() {
        String firstName_fake = "ZbytDlugieHasloMaksymalnie100znakowMozeBycTylkoTylkoTylkoTylko";
        UserRequest user = new UserRequest(email, password, firstName_fake, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    void save_ToLongLastName_ThrowIllegalArgumentException() {
        String lastName_fake = "ZbytDlugieHasloMaksymalnie100znakowMozeBycTylkoTylkoTylkoTylko";
        UserRequest user = new UserRequest(email, password, firstName, lastName_fake);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    void save_emailMustBeUnique_ThrowEmailExistException() {
        User user = new User(email, password, firstName, lastName);
        UserRequest userSecond = new UserRequest(email, password, firstName, lastName);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Assertions.assertThrows(EmailExistException.class, () -> userService.save(userSecond));
    }

    @Test
    void save_emailUnique_addUser() {
        User user = new User(email, password, firstName, lastName);
        UserRequest user_actual = new UserRequest("email@wp.pl", password, firstName, lastName);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Assertions.assertDoesNotThrow(() -> userService.save(user_actual));
    }

    @Test
    void update_editParameters_editCorrect() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserRequest userRequest = UserRequest.builder()
                .id(id)
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName("newLastName")
                .build();
        Assertions.assertDoesNotThrow(() -> userService.update(userRequest));
    }

    @Test
    void update_editParametersNoUserInDatabase_ThrowUserWasNotFoundException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserRequest userRequest = UserRequest.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName("newLastName")
                .build();
        Assertions.assertThrows(UserWasNotFoundException.class, () -> userService.update(userRequest));
    }

    @Test
    void update_editParametersNullEmail_ThrowNullParameterException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserRequest userRequest = UserRequest.builder()
                .id(id)
                .email(null)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        Assertions.assertThrows(NullParameterException.class, () -> userService.update(userRequest));
    }

    @Test
    void update_editParametersEmptyEmail_ThrowIllegalArgumentException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserRequest userRequest = UserRequest.builder()
                .id(id)
                .email("")
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.update(userRequest));
    }

    @Test
    void update_editParametersEmptyPass_ThrowNullParameterException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserRequest userRequest = UserRequest.builder()
                .id(id)
                .email(email)
                .password("")
                .firstName(firstName)
                .lastName(lastName)
                .build();
        Assertions.assertThrows(NullParameterException.class, () -> userService.update(userRequest));
    }

    @Test
    void update_editParametersNullFirstName_ThrowNullParameterException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserRequest userRequest = UserRequest.builder()
                .id(id)
                .email(email)
                .password(password)
                .firstName(null)
                .lastName(lastName)
                .build();
        Assertions.assertThrows(NullParameterException.class, () -> userService.update(userRequest));
    }

    @Test
    void update_editParametersEmptyFirstName_ThrowIllegalArgumentException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserRequest userRequest = UserRequest.builder()
                .id(id)
                .email(email)
                .password(password)
                .firstName("")
                .lastName(lastName)
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.update(userRequest));
    }

    @Test
    void update_editParametersNullLastName_ThrowNullParameterException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserRequest userRequest = UserRequest.builder()
                .id(id)
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(null)
                .build();
        Assertions.assertThrows(NullParameterException.class, () -> userService.update(userRequest));
    }

    @Test
    void update_editParametersEmptyLastName_ThrowIllegalArgumentException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserRequest userRequest = UserRequest.builder()
                .id(id)
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName("")
                .statusAccount(UserStatusAccount.NOT_ACTIVATED.name())
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.update(userRequest));
    }

    @Test
    void update_editParameterStatus_editCorrect() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        long idSecond = 2L;
        User userSecond = new User("email@p.pl", password, firstName, lastName);
        userSecond.setId(idSecond);
        userSecond.setStatusAccount(UserStatusAccount.NOT_ACTIVATED.name());
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("email@p.pl")).thenReturn(Optional.of(userSecond));
        UserRequest userRequestbuilder = UserRequest.builder()
                .id(id)
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .statusAccount(UserStatusAccount.ACTIVATED.name())
                .build();
        Assertions.assertDoesNotThrow(() -> userService.update(userRequestbuilder));
    }

    @Test
    void update_editParameterStatusNoCorrect_changeStatusToNoActivated() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        long idSecond = 2L;
        User userSecond = new User("email@p.pl", password, firstName, lastName);
        userSecond.setId(idSecond);
        userSecond.setStatusAccount(UserStatusAccount.NOT_ACTIVATED.name());
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("email@p.pl")).thenReturn(Optional.of(userSecond));
        UserRequest userRequestBuilder = UserRequest.builder()
                .id(id)
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .statusAccount(null)
                .build();
        Assertions.assertDoesNotThrow(() -> userService.update(userRequestBuilder));
    }

    @Test
    void save_UserWithBirthDateAfterToday_ThrowsIllegalArgumentException() {
        UserRequest user = UserRequest.builder()
                .email(email)
                .password(password)
                .lastName(lastName)
                .firstName(firstName)
                .birthDate(LocalDate.now().plusDays(1))
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    void update_UserWithBirthDateAfterToday_ThrowsIllegalArgumentException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserRequest userRequest = UserRequest.builder()
                .id(id)
                .email(email)
                .password(password)
                .lastName(lastName)
                .firstName(firstName)
                .birthDate(LocalDate.now().plusDays(1))
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.update(userRequest));
    }

    @Test
    void update_UserWithBirthDateBeforeMinUserAge_DoesNotThrow() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserRequest userRequest = UserRequest.builder()
                .id(id)
                .email(email)
                .password(password)
                .lastName(lastName)
                .firstName(firstName)
                .birthDate(LocalDate.now().minusYears(USER_AGE))
                .build();
        Assertions.assertDoesNotThrow(() -> userService.update(userRequest));
    }

    @Test
    void update_UserWithBirthDateBeforeMinUserAgeAndOneDay_DoesNotThrow() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserRequest userRequest = UserRequest.builder()
                .id(id)
                .email(email)
                .password(password)
                .lastName(lastName)
                .firstName(firstName)
                .birthDate(LocalDate.now().minusYears(USER_AGE).minusDays(1))
                .build();
        Assertions.assertDoesNotThrow(() -> userService.update(userRequest));
    }

    @Test
    void update_UserWithBirthDateBeforeMinUserAgeAndPlusOneDay_ThrowsIllegalArgumentException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserRequest userRequest = UserRequest.builder()
                .id(id)
                .email(email)
                .password(password)
                .lastName(lastName)
                .firstName(firstName)
                .birthDate(LocalDate.now().minusYears(USER_AGE).plusDays(1))
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.update(userRequest));
    }

    @Test
    void update_UserWithNormalBirthDate_DoesNotThrow() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserRequest userRequest = UserRequest.builder()
                .id(id)
                .email(email)
                .password(password)
                .lastName(lastName)
                .firstName(firstName)
                .birthDate(LocalDate.now().minusYears(25))
                .build();
        Assertions.assertDoesNotThrow(() -> userService.update(userRequest));
    }

    @Test
    void update_NullPasswordOldInDB_DoesNotThrow() {
        final String NOT_NULL = "Not Null";
        UserRequest userRequest = UserRequest.builder()
                .id(17L)
                .firstName(NOT_NULL)
                .lastName(NOT_NULL)
                .email("mail@domain.pl")
                .build();
        User user = User.builder()
                .id(17L)
                .firstName(NOT_NULL)
                .lastName(NOT_NULL)
                .email("mail@domain.pl")
                .password(NOT_NULL)
                .build();
        when(userRepository.findById(17L)).thenReturn(Optional.of(user));
        Assertions.assertDoesNotThrow(() -> userService.update(userRequest));
    }

    @Test
    void update_EmptyPassword_ThrowNullParamterException() {
        final String NOT_NULL = "Not Null";
        UserRequest userRequest = UserRequest.builder()
                .id(17L)
                .firstName(NOT_NULL)
                .lastName(NOT_NULL)
                .password("")
                .email("mail@domain.pl")
                .build();
        User user = User.builder()
                .id(17L)
                .firstName(NOT_NULL)
                .lastName(NOT_NULL)
                .email("mail@domain.pl")
                .password(NOT_NULL)
                .build();
        when(userRepository.findById(17L)).thenReturn(Optional.of(user));
        Assertions.assertThrows(NullParameterException.class, () -> userService.update(userRequest));
    }
}
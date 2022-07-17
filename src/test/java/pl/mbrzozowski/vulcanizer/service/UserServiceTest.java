package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;
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
        userService = new UserService(userRepository);
    }

    @Test
    public void saveUser_ReqFieldByConstructor_addUser() {
        User user = new User(email, password, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ReqFieldByBuilder_addUser() {
        User user = User.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .createAccountTime(createAccountTime)
                .build();
        Assertions.assertDoesNotThrow(() -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ReqFieldByConstructorNoEmail_ThrowNullPointerException() {
        User user = new User(null, password, firstName, lastName);
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ReqFieldByConstructorEmptyEmail_ThrowIllegalArgumentException() {
        User user = new User("", password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ReqFieldByBuilderNoEmail_ThrowNullPointerException() {
        User user = User.builder()
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .createAccountTime(createAccountTime)
                .build();
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ReqFieldByConstructorNoPass_ThrowNullPointerException() {
        User user = new User(email, null, firstName, lastName);
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ReqFieldByConstructorEmptyPass_ThrowIllegalArgumentException() {
        User user = new User(email, "", firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ReqFieldByBuilderNoPass_ThrowNullPointerException() {
        User user = User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .createAccountTime(createAccountTime)
                .build();
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ReqFieldByConstructorNoFirstName_ThrowNullPointerException() {
        User user = new User(email, password, null, lastName);
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ReqFieldByConstructorEmptyFirstName_ThrowIllegalArgumentException() {
        User user = new User(email, password, "", lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ReqFieldByBuilderNoFirstName_ThrowNullPointerException() {
        User user = User.builder()
                .email(email)
                .password(password)
                .lastName(lastName)
                .createAccountTime(createAccountTime)
                .build();
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ReqFieldByConstructorNoLastName_ThrowNullPointerException() {
        User user = new User(email, password, firstName, null);
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ReqFieldByConstructorEmptyLastName_ThrowIllegalArgumentException() {
        User user = new User(email, password, firstName, "");
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ReqFieldByBuilderNoLastName_ThrowNullPointerException() {
        User user = User.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .createAccountTime(createAccountTime)
                .build();
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ReqFieldByConstructorNoCreateTime_createUser() {
        User user = new User(email, password, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ReqFieldByBuilderNoCreateTime_createUser() {
        User user = User.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        Assertions.assertDoesNotThrow(() -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ReqFieldByConstructorAllNull_ThrowNullPointerException() {
        User user = new User(null, null, null, null);
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ReqFieldByBuilderAllNull_ThrowNullPointerException() {
        User user = User.builder()
                .build();
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ValidateEmailNoMonkey_ThrowIllegalArgumentException() {
        String emailAddress = "usernamedomain.com";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ValidateEmailUnicode_ThrowIllegalArgumentException() {
        String emailAddress = "用户名@领域.电脑";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ValidateGmail_Valid() {
        String emailAddress = "username+something@domain.com";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ValidateToLongEmailUserNameMax64_ThrowIllegalArgumentException() {
        String emailAddress = "vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv" +
                "vvvvvvvvvvvvvvv@domain.com";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ValidateEmptyUsernameEmail_ThrowIllegalArgumentException() {
        String emailAddress = "@domain.com";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ValidateDomainEndingByPointer_ThrowIllegalArgumentException() {
        String emailAddress = "username@domain.";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ValidateDomainStartingByPointer_ThrowIllegalArgumentException() {
        String emailAddress = "username@.com";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ValidateEmptyDomainEmail_ThrowIllegalArgumentException() {
        String emailAddress = "username@";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_ValidateDomainStartingByPointerr_ThrowIllegalArgumentException() {
        String emailAddress = "mateusz_brzozowski93@wp.pl";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> userService.saveUser(user));
    }

    @Test
    void saveUser_ToLongPass_ThrowIllegalArgumentException() {
        String password_fake = "ZbytDlugieHasloMaksymalnie30znakowMozeBycTylko";
        User user = new User(email, password_fake, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    void saveUser_ToLongFirstName_ThrowIllegalArgumentException() {
        String firstName_fake = "ZbytDlugieHasloMaksymalnie100znakowMozeBycTylkoTylkoTylkoTylko";
        User user = new User(email, password, firstName_fake, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    void saveUser_ToLongLastName_ThrowIllegalArgumentException() {
        String lastName_fake = "ZbytDlugieHasloMaksymalnie100znakowMozeBycTylkoTylkoTylkoTylko";
        User user = new User(email, password, firstName, lastName_fake);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    void saveUser_emailMustBeUnique_ThrowIllegalArgumentException() {
        User user = new User(email, password, firstName, lastName);
        User userSecond = new User(email, password, firstName, lastName);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(userSecond));
    }

    @Test
    void saveUser_emailUnique_addUser() {
        User user = new User(email, password, firstName, lastName);
        User user_actual = new User("email@wp.pl", password, firstName, lastName);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Assertions.assertDoesNotThrow(() -> userService.saveUser(user_actual));
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
    void update_editParametersNullEmail_ThrowNullPointerException() {
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
        Assertions.assertThrows(NullPointerException.class, () -> userService.update(userRequest));
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
    void update_editParametersNullPass_ThrowNullPointerException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserRequest userRequest = UserRequest.builder()
                .id(id)
                .email(email)
                .password(null)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        Assertions.assertThrows(NullPointerException.class, () -> userService.update(userRequest));
    }

    @Test
    void update_editParametersEmptyPass_ThrowIllegalArgumentException() {
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
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.update(userRequest));
    }

    @Test
    void update_editParametersNullFirstName_ThrowNullPointerException() {
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
        Assertions.assertThrows(NullPointerException.class, () -> userService.update(userRequest));
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
    void update_editParametersNullLastName_ThrowNullPointerException() {
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
        Assertions.assertThrows(NullPointerException.class, () -> userService.update(userRequest));
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
                .statusAccount(UserStatusAccount.NOT_ACTIVATED)
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
        userSecond.setStatusAccount(UserStatusAccount.NOT_ACTIVATED);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail("email@p.pl")).thenReturn(Optional.of(userSecond));
        UserRequest userRequestbuilder = UserRequest.builder()
                .id(id)
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .statusAccount(UserStatusAccount.ACTIVATED)
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
        userSecond.setStatusAccount(UserStatusAccount.NOT_ACTIVATED);
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
    void saveUser_UserWithBirthDateAfterToday_ThrowsIllegalArgumentException() {
        User user = User.builder()
                .email(email)
                .password(password)
                .lastName(lastName)
                .firstName(firstName)
                .birthDate(LocalDate.now().plusDays(1))
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
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
}
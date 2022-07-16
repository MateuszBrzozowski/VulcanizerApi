package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;
import pl.mbrzozowski.vulcanizer.exceptions.UserWasNotFoundException;
import pl.mbrzozowski.vulcanizer.repository.UserRepository;
import pl.mbrzozowski.vulcanizer.service.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

class UserServiceTest {

    private final String email = "username@domain.com";
    private final String password = "password";
    private final String firstName = "firstName";
    private final String lastName = "LastName";
    private final LocalDateTime createAccountTime = LocalDateTime.now();
    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userRepository = mock(UserRepository.class);
        UserMapper mapper = mock(UserMapper.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void newUser_ReqFieldByConstructor_addUser() {
        User user = new User(email, password, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> userService.saveUser(user));
    }

    @Test
    public void newUser_ReqFieldByBuilder_addUser() {
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
    public void newUser_ReqFieldByConstructorNoEmail_ThrowNullPointerException() {
        User user = new User(null, password, firstName, lastName);
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorEmptyEmail_ThrowIllegalArgumentException() {
        User user = new User("", password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ReqFieldByBuilderNoEmail_ThrowNullPointerException() {
        User user = User.builder()
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .createAccountTime(createAccountTime)
                .build();
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorNoPass_ThrowNullPointerException() {
        User user = new User(email, null, firstName, lastName);
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorEmptyPass_ThrowIllegalArgumentException() {
        User user = new User(email, "", firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ReqFieldByBuilderNoPass_ThrowNullPointerException() {
        User user = User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .createAccountTime(createAccountTime)
                .build();
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorNoFirstName_ThrowNullPointerException() {
        User user = new User(email, password, null, lastName);
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorEmptyFirstName_ThrowIllegalArgumentException() {
        User user = new User(email, password, "", lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ReqFieldByBuilderNoFirstName_ThrowNullPointerException() {
        User user = User.builder()
                .email(email)
                .password(password)
                .lastName(lastName)
                .createAccountTime(createAccountTime)
                .build();
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorNoLastName_ThrowNullPointerException() {
        User user = new User(email, password, firstName, null);
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorEmptyLastName_ThrowIllegalArgumentException() {
        User user = new User(email, password, firstName, "");
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ReqFieldByBuilderNoLastName_ThrowNullPointerException() {
        User user = User.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .createAccountTime(createAccountTime)
                .build();
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorNoCreateTime_createUser() {
        User user = new User(email, password, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> userService.saveUser(user));
    }

    @Test
    public void newUser_ReqFieldByBuilderNoCreateTime_createUser() {
        User user = User.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        Assertions.assertDoesNotThrow(() -> userService.saveUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorAllNull_ThrowNullPointerException() {
        User user = new User(null, null, null, null);
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ReqFieldByBuilderAllNull_ThrowNullPointerException() {
        User user = User.builder()
                .build();
        Assertions.assertThrows(NullPointerException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ValidateEmailNoMonkey_ThrowIllegalArgumentException() {
        String emailAddress = "usernamedomain.com";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ValidateEmailUnicode_ThrowIllegalArgumentException() {
        String emailAddress = "用户名@领域.电脑";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ValidateGmail_Valid() {
        String emailAddress = "username+something@domain.com";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> userService.saveUser(user));
    }

    @Test
    public void newUser_ValidateToLongEmailUserNameMax64_ThrowIllegalArgumentException() {
        String emailAddress = "vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv" +
                "vvvvvvvvvvvvvvv@domain.com";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ValidateEmptyUsernameEmail_ThrowIllegalArgumentException() {
        String emailAddress = "@domain.com";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ValidateDomainEndingByPointer_ThrowIllegalArgumentException() {
        String emailAddress = "username@domain.";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ValidateDomainStartingByPointer_ThrowIllegalArgumentException() {
        String emailAddress = "username@.com";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ValidateEmptyDomainEmail_ThrowIllegalArgumentException() {
        String emailAddress = "username@";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    public void newUser_ValidateDomainStartingByPointerr_ThrowIllegalArgumentException() {
        String emailAddress = "mateusz_brzozowski93@wp.pl";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> userService.saveUser(user));
    }

    @Test
    void newUser_ToLongPass_ThrowIllegalArgumentException() {
        String password_fake = "ZbytDlugieHasloMaksymalnie30znakowMozeBycTylko";
        User user = new User(email, password_fake, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    void newUser_ToLongFirstName_ThrowIllegalArgumentException() {
        String firstName_fake = "ZbytDlugieHasloMaksymalnie100znakowMozeBycTylkoTylkoTylkoTylko";
        User user = new User(email, password, firstName_fake, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    void newUser_ToLongLastName_ThrowIllegalArgumentException() {
        String lastName_fake = "ZbytDlugieHasloMaksymalnie100znakowMozeBycTylkoTylkoTylkoTylko";
        User user = new User(email, password, firstName, lastName_fake);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    void newUser_emailMustBeUnique_ThrowIllegalArgumentException() {
        User user = new User(email, password, firstName, lastName);
        User userSecond = new User(email, password, firstName, lastName);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveUser(userSecond));
    }

    @Test
    void newUser_emailUnique_addUser() {
        User user = new User(email, password, firstName, lastName);
        User user_actual = new User("email@wp.pl", password, firstName, lastName);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Assertions.assertDoesNotThrow(() -> userService.saveUser(user_actual));
    }

    @Test
    void saveUser_editParameters_editCorrect() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.getReferenceById(user.getId())).thenReturn(user);
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
    void saveUser_editParametersNoUserInDatabase_ThrowUserWasNotFoundException() {
        User user = new User(email, password, firstName, lastName);
        user.setId(1L);
        when(userRepository.getReferenceById(user.getId())).thenReturn(null);
        UserRequest userRequest = UserRequest.builder()
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName("newLastName")
                .build();
        Assertions.assertThrows(UserWasNotFoundException.class, () -> userService.update(userRequest));
    }

    @Test
    void saveUser_editParametersNullEmail_ThrowNullPointerException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.getReferenceById(user.getId())).thenReturn(user);
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
    void saveUser_editParametersEmptyEmail_ThrowIllegalArgumentException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.getReferenceById(user.getId())).thenReturn(user);
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
    void saveUser_editParametersNullPass_ThrowNullPointerException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.getReferenceById(user.getId())).thenReturn(user);
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
    void saveUser_editParametersEmptyPass_ThrowIllegalArgumentException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.getReferenceById(user.getId())).thenReturn(user);
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
    void saveUser_editParametersNullFirstName_ThrowNullPointerException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.getReferenceById(user.getId())).thenReturn(user);
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
    void saveUser_editParametersEmptyFirstName_ThrowIllegalArgumentException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.getReferenceById(user.getId())).thenReturn(user);
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
    void saveUser_editParametersNullLastName_ThrowNullPointerException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.getReferenceById(user.getId())).thenReturn(user);
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
    void saveUser_editParametersEmptyLastName_ThrowIllegalArgumentException() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        when(userRepository.getReferenceById(user.getId())).thenReturn(user);
        UserRequest userRequest = UserRequest.builder()
                .id(id)
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName("")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.update(userRequest));
    }

    @Test
    void saveUser_editParameterStatus_editCorrect() {
        long id = 1L;
        User user = new User(email, password, firstName, lastName);
        user.setId(id);
        user.setStatusAccount(UserStatusAccount.NOT_ACTIVATED);
        when(userRepository.getReferenceById(user.getId())).thenReturn(user);
        UserRequest userRequestbuilder = UserRequest.builder()
                .id(id)
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .statusAccount(UserStatusAccount.ACTIVATED)
                .build();
        userService.update(userRequestbuilder);
        verify(userRepository).save(user);
    }


}
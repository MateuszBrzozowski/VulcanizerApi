package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.repository.UserRepository;


import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;

class UserServiceTest {

    private final String email = "username@domain.com";
    private final String password = "password";
    private final String firstName = "firstName";
    private final String lastName = "LastName";
    private final LocalDateTime createAccountTime = LocalDateTime.now();
    private pl.mbrzozowski.vulcanizer.service.UserService userService;

    @BeforeEach
    public void beforeEach() {
        UserRepository userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void newUser_ReqFieldByConstructor_addUser() {
        User user = new User(email, password, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> userService.addUser(user));
    }

    @Test
    public void newUser_ReqFieldByBuilder_addUser() {
        User user = User.builder()
                .email(email)
                .password(password)
                .firsName(firstName)
                .lastName(lastName)
                .createAccountTime(createAccountTime)
                .build();
        Assertions.assertDoesNotThrow(() -> userService.addUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorNoEmail_ThrowNullPointerException() {
        User user = new User(null, password, firstName, lastName);
        Assertions.assertThrows(NullPointerException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorEmptyEmail_ThrowIllegalArgumentException() {
        User user = new User("", password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ReqFieldByBuilderNoEmail_ThrowNullPointerException() {
        User user = User.builder()
                .password(password)
                .firsName(firstName)
                .lastName(lastName)
                .createAccountTime(createAccountTime)
                .build();
        Assertions.assertThrows(NullPointerException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorNoPass_ThrowNullPointerException() {
        User user = new User(email, null, firstName, lastName);
        Assertions.assertThrows(NullPointerException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorEmptyPass_ThrowIllegalArgumentException() {
        User user = new User(email, "", firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ReqFieldByBuilderNoPass_ThrowNullPointerException() {
        User user = User.builder()
                .email(email)
                .firsName(firstName)
                .lastName(lastName)
                .createAccountTime(createAccountTime)
                .build();
        Assertions.assertThrows(NullPointerException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorNoFirstName_ThrowNullPointerException() {
        User user = new User(email, password, null, lastName);
        Assertions.assertThrows(NullPointerException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorEmptyFirstName_ThrowIllegalArgumentException() {
        User user = new User(email, password, "", lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ReqFieldByBuilderNoFirstName_ThrowNullPointerException() {
        User user = User.builder()
                .email(email)
                .password(password)
                .lastName(lastName)
                .createAccountTime(createAccountTime)
                .build();
        Assertions.assertThrows(NullPointerException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorNoLastName_ThrowNullPointerException() {
        User user = new User(email, password, firstName, null);
        Assertions.assertThrows(NullPointerException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorEmptyLastName_ThrowIllegalArgumentException() {
        User user = new User(email, password, firstName, "");
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ReqFieldByBuilderNoLastName_ThrowNullPointerException() {
        User user = User.builder()
                .email(email)
                .password(password)
                .firsName(firstName)
                .createAccountTime(createAccountTime)
                .build();
        Assertions.assertThrows(NullPointerException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ReqFieldNullCreationTime_createUser() {
        User user = new User(email, password, firstName, lastName, null, null);
        Assertions.assertDoesNotThrow(() -> userService.addUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorNoCreateTime_createUser() {
        User user = new User(email, password, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> userService.addUser(user));
    }

    @Test
    public void newUser_ReqFieldByBuilderNoCreateTime_createUser() {
        User user = User.builder()
                .email(email)
                .password(password)
                .firsName(firstName)
                .lastName(lastName)
                .build();
        Assertions.assertDoesNotThrow(() -> userService.addUser(user));
    }

    @Test
    public void newUser_ReqFieldByConstructorAllNull_ThrowNullPointerException() {
        User user = new User(null, null, null, null);
        Assertions.assertThrows(NullPointerException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ReqFieldByBuilderAllNull_ThrowNullPointerException() {
        User user = User.builder()
                .build();
        Assertions.assertThrows(NullPointerException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ValidateEmailNoMonkey_ThrowIllegalArgumentException() {
        String emailAddress = "usernamedomain.com";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ValidateEmailUnicode_ThrowIllegalArgumentException() {
        String emailAddress = "用户名@领域.电脑";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ValidateGmail_Valid() {
        String emailAddress = "username+something@domain.com";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> userService.addUser(user));
    }

    @Test
    public void newUser_ValidateToLongEmailUserNameMax64_ThrowIllegalArgumentException() {
        String emailAddress = "vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv" +
                "vvvvvvvvvvvvvvv@domain.com";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ValidateEmptyUsernameEmail_ThrowIllegalArgumentException() {
        String emailAddress = "@domain.com";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ValidateDomainEndingByPointer_ThrowIllegalArgumentException() {
        String emailAddress = "username@domain.";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ValidateDomainStartingByPointer_ThrowIllegalArgumentException() {
        String emailAddress = "username@.com";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ValidateEmptyDomainEmail_ThrowIllegalArgumentException() {
        String emailAddress = "username@";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.addUser(user));
    }

    @Test
    public void newUser_ValidateDomainStartingByPointerr_ThrowIllegalArgumentException() {
        String emailAddress = "mateusz_brzozowski93@wp.pl";
        User user = new User(emailAddress, password, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> userService.addUser(user));
    }

    @Test
    public void newUser_statusAccountCanNotBeNull_addUser() {
        User user = new User(email, password, firstName, lastName, null, null);
        Assertions.assertDoesNotThrow(() -> userService.addUser(user));
    }


}
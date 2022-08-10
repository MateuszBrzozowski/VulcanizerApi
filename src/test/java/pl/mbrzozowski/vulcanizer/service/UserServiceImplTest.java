package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.mbrzozowski.vulcanizer.dto.FavoritesRequest;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.entity.Company;
import pl.mbrzozowski.vulcanizer.entity.Favorites;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.exceptions.EmailExistException;
import pl.mbrzozowski.vulcanizer.repository.CompanyRepository;
import pl.mbrzozowski.vulcanizer.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private final String email = "username@domain.com";
    private final String password = "password";
    private final String firstName = "firstName";
    private final String lastName = "LastName";
    private final LocalDateTime createAccountTime = LocalDateTime.now();
    private UserServiceImpl userService;
    private UserRepository userRepository;
    private CompanyService businessService;
    private CompanyRepository businessRepository;
    private final int USER_AGE = 6;

    @BeforeEach
    public void beforeEach() {
        EmailService emailService = mock(EmailService.class);
        LoginAttemptService loginAttemptService = mock(LoginAttemptService.class);
        BCryptPasswordEncoder passwordEncoder = mock(BCryptPasswordEncoder.class);
        userRepository = mock(UserRepository.class);
        userRepository = mock(UserRepository.class);
        PhoneService phoneService = mock(PhoneService.class);
        PhotoService photoService = mock(PhotoService.class);
        AddressService addressService = mock(AddressService.class);
        FavoriteService favoriteService = mock(FavoriteService.class);
        ConfirmationTokenService confirmationTokenService = mock(ConfirmationTokenService.class);
        ResetPasswordTokenService resetPasswordTokenService = mock(ResetPasswordTokenService.class);
        businessService = mock(CompanyService.class);
        businessRepository = mock(CompanyRepository.class);
        SentMailAccountBlockedService sentMailAccountBlockedService = mock(SentMailAccountBlockedService.class);
        TokenCheckSumService tokenCheckSumService = mock(TokenCheckSumService.class);
        userService = new UserServiceImpl(userRepository,
                addressService,
                phoneService,
                photoService,
                favoriteService,
                businessService,
                passwordEncoder,
                loginAttemptService,
                confirmationTokenService,
                emailService,
                resetPasswordTokenService,
                sentMailAccountBlockedService,
                tokenCheckSumService);
    }

//    @Test
//    public void saveUser_ReqFieldByConstructor_addUser() {
//        UserRequest user = new UserRequest(email, password, firstName, lastName);
//        Assertions.assertDoesNotThrow(() -> userService.save(user));
//    }
//
//    @Test
//    public void saveUser_ReqFieldByBuilder_addUser() {
//        UserRequest user = UserRequest.builder()
//                .email(email)
//                .password(password)
//                .firstName(firstName)
//                .lastName(lastName)
//                .build();
//        Assertions.assertDoesNotThrow(() -> userService.save(user));
//    }

    @Test
    public void saveUser_ReqFieldByConstructorNoEmail_ThrowIllegalArgumentException() {
        UserRequest user = new UserRequest(null, password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByConstructorEmptyEmail_ThrowIllegalArgumentException() {
        UserRequest user = new UserRequest("", password, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByBuilderNoEmail_ThrowIllegalArgumentException() {
        UserRequest user = UserRequest.builder()
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByConstructorNoPass_ThrowNullParameterException() {
        UserRequest user = new UserRequest(email, null, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
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
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByConstructorNoFirstName_ThrowNullParameterException() {
        UserRequest user = new UserRequest(email, password, null, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
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
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

    @Test
    public void save_ReqFieldByConstructorNoLastName_ThrowNullParameterException() {
        UserRequest user = new UserRequest(email, password, firstName, null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
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
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
    }

//    @Test
//    public void save_ReqFieldByConstructorNoCreateTime_createUser() {
//        UserRequest user = new UserRequest(email, password, firstName, lastName);
//        Assertions.assertDoesNotThrow(() -> userService.save(user));
//    }
//
//    @Test
//    public void save_ReqFieldByBuilderNoCreateTime_createUser() {
//        UserRequest user = UserRequest.builder()
//                .email(email)
//                .password(password)
//                .firstName(firstName)
//                .lastName(lastName)
//                .build();
//        Assertions.assertDoesNotThrow(() -> userService.save(user));
//    }

    @Test
    public void save_ReqFieldAllNull_ThrowIllegalArgumentException() {
        UserRequest user = UserRequest.builder()
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(user));
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

//    @Test
//    public void save_ValidateGmail_Valid() {
//        String emailAddress = "username+something@domain.com";
//        UserRequest user = new UserRequest(emailAddress, password, firstName, lastName);
//        Assertions.assertDoesNotThrow(() -> userService.save(user));
//    }

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

//    @Test
//    public void save_ValidateDomainStartingByPointerr_ThrowIllegalArgumentException() {
//        String emailAddress = "mateusz_brzozowski93@wp.pl";
//        UserRequest user = new UserRequest(emailAddress, password, firstName, lastName);
//        Assertions.assertDoesNotThrow(() -> userService.save(user));
//    }

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
    void saveFavorites_Success() {
        FavoritesRequest favoritesRequest = new FavoritesRequest(1L, 1L);
        User user = new User();
        Company business = new Company();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(businessRepository.findById(1L)).thenReturn(Optional.of(business));
        boolean b = userService.saveFavorite(favoritesRequest);
        Assertions.assertTrue(b);
    }

    @Test
    void saveFavorites_BusinessIDNotNull_ThrowIllegalArgumentException() {
        FavoritesRequest favoritesRequest = new FavoritesRequest(1L, null);
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(businessService.findById(null)).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveFavorite(favoritesRequest));
    }

    @Test
    void saveFavorites_UserIDNotNull_ThrowIllegalArgumentException() {
        FavoritesRequest favoritesRequest = new FavoritesRequest(null, 1L);
        Company business = new Company();
        when(userRepository.findById(1L)).thenThrow(IllegalArgumentException.class);
        when(businessService.findById(null)).thenReturn(business);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveFavorite(favoritesRequest));
    }

    @Test
    void saveFavorites_BusinessNotFound_ThrowIllegalArgumentException() {
        FavoritesRequest favoritesRequest = new FavoritesRequest(1L, 1L);
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(businessService.findById(1L)).thenThrow(IllegalArgumentException.class);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveFavorite(favoritesRequest));
    }

    @Test
    void saveFavorites_UserIDNoTfOUND_ThrowIllegalArgumentException() {
        FavoritesRequest favoritesRequest = new FavoritesRequest(1L, 1L);
        Company business = new Company();
        when(userRepository.findById(1L)).thenThrow(IllegalArgumentException.class);
        when(businessService.findById(1L)).thenReturn(business);
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.saveFavorite(favoritesRequest));
    }

    @Test
    void isBusinessFavoriteForUser_True() {
        User user = new User();
        user.setId(1L);
        Company business = new Company();
        business.setId(1L);
        List<Favorites> favorites = new ArrayList<>();
        favorites.add(new Favorites(user, business));
        user.setFavorites(favorites);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        boolean isTrue = userService.isBusinessFavoriteForUser(1L, 1L);
        Assertions.assertTrue(isTrue);
    }

    @Test
    void isBusinessFavoriteForUser_False() {
        User user = new User();
        user.setId(1L);
        Company business = new Company();
        business.setId(1L);
        List<Favorites> favorites = new ArrayList<>();
        favorites.add(new Favorites(user, business));
        user.setFavorites(favorites);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        boolean isFalse = userService.isBusinessFavoriteForUser(1L, 2L);
        Assertions.assertFalse(isFalse);
    }

    @Test
    void deleteFavorite_True() {
        User user = new User();
        user.setId(1L);
        Company business = new Company();
        business.setId(1L);
        List<Favorites> favorites = new ArrayList<>();
        favorites.add(new Favorites(user, business));
        user.setFavorites(favorites);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        boolean isTrue = userService.deleteFavorite(1L, 1L);
        Assertions.assertTrue(isTrue);
    }

    @Test
    void deleteFavorite_False() {
        User user = new User();
        user.setId(1L);
        Company business = new Company();
        business.setId(1L);
        List<Favorites> favorites = new ArrayList<>();
        favorites.add(new Favorites(user, business));
        user.setFavorites(favorites);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        boolean isFalse = userService.deleteFavorite(1L, 2L);
        Assertions.assertFalse(isFalse);
    }
}
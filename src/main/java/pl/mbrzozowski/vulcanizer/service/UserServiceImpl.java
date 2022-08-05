package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mbrzozowski.vulcanizer.constant.SecurityConstant;
import pl.mbrzozowski.vulcanizer.domain.UserPrincipal;
import pl.mbrzozowski.vulcanizer.dto.*;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserRegisterBodyToUserRequest;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserRequestToUser;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserToUserResponse;
import pl.mbrzozowski.vulcanizer.entity.*;
import pl.mbrzozowski.vulcanizer.exceptions.*;
import pl.mbrzozowski.vulcanizer.repository.UserRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final AddressService addressService;
    private final PhoneService phoneService;
    private final PhotoService photoService;
    private final FavoriteService favoriteService;
    private final BusinessService businessService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final LoginAttemptService loginAttemptService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final ResetPasswordTokenService resetPasswordTokenService;
    private final SentMailAccountBlockedService sentMailAccountBlockedService;
    private final TokenCheckSumService tokenCheckSumService;
    protected static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public User save(UserRequest userRequest) {
        if (findByEmail(userRequest.getEmail()).isPresent()) {
            throw new EmailExistException("Email is ready exist.");
        }
        ValidationUser.validBeforeRegister(userRequest);
        String encodedPassword = encodePassword(userRequest.getPassword());
        User newUser =
                new User(userRequest.getEmail().toLowerCase(),
                        encodedPassword,
                        userRequest.getFirstName(),
                        userRequest.getLastName());

        User user = userRepository.save(newUser);
        String token = confirmationTokenService.createNewToken(user);
        emailService.confirmYourEmail(user.getEmail(), token);
        return newUser;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


        User user = findById(userRequest.getId());
        if (!user.getEmail().equalsIgnoreCase(userRequest.getEmail())) {
            if (findByEmail(userRequest.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email is ready exist.");
            }
        }

        updatePhone(userRequest, user);
        updateAvatar(userRequest, user);
        updateAddress(userRequest, user);

        User userNewData = new UserRequestToUser().apply(userRequest);
        businessToBusinessNewData(user, userNewData);
        ValidationUser.validBeforeEditing(user);
        userRepository.save(user);
        return new UserToUserResponse().convert(user);
    }

    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserToUserResponse().convert(user))
                .collect(Collectors.toList());
    }

    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("User by id [" + id + "] was not found");
                });
    }

    public void deleteById(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository
                .findByEmail(email);
    }

    @Override
    public boolean saveFavorite(FavoritesRequest favoritesRequest) {
        User user = findById(favoritesRequest.getUserId());
        Business business = businessService.findById(favoritesRequest.getBusinessId());
        Favorites favorites = new Favorites(user, business);
        favoriteService.save(favorites);
        return true;
    }

    @Override
    public boolean deleteFavorite(Long userId, Long businessId) {
        User user = findById(userId);
        List<Favorites> favoritesList = user.getFavorites();
        for (int i = 0; i < favoritesList.size(); i++) {
            if (favoritesList.get(i).getBusiness().getId().equals(businessId)) {
                Favorites favoritesToDelete = favoritesList.get(i);
                favoritesList.remove(i);
                favoriteService.deleteById(favoritesToDelete.getId());
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isBusinessFavoriteForUser(Long userId, Long businessId) {
        User user = findById(userId);
        List<Favorites> favorites = user.getFavorites();
        return favorites.stream().anyMatch(favorites1 -> favorites1.getBusiness().getId().equals(businessId));
    }

    @Override
    public ArrayList<Business> findAllFavoriteForUser(Long userId) {
        return null;
    }

    @Override
    public UserResponse register(UserRegisterBody userRegisterBody) {
        UserRequest userRequest = new UserRegisterBodyToUserRequest().convert(userRegisterBody);
        if (userRequest == null) {
            return null;
        }
        User userSaved = save(userRequest);
        return new UserToUserResponse().convert(userSaved);
    }

    @Override
    public User login(UserLoginBody userLoginBody) {
        User user = new User();
        if (findByEmail(userLoginBody.getEmail()).isPresent()) {
            user = findByEmail(userLoginBody.getEmail()).get();
        }
        return user;
    }

    @Override
    public void resetPasswordStart(UserResetPasswordBody userResetPasswordBody) {
        ValidationUser.validResetPassBody(userResetPasswordBody);
        Optional<User> userOptional = findByEmail(userResetPasswordBody.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String firstName = userResetPasswordBody.getFirstName();
            String lastName = userResetPasswordBody.getLastName();
            if (user.getFirstName().equalsIgnoreCase(firstName) && user.getLastName().equalsIgnoreCase(lastName)) {
                String token = resetPasswordTokenService.createNewToken(user);
                emailService.resetPassword(user.getEmail(), token);
            } else {
                throw new IllegalArgumentException("First and last name not valid");
            }
        } else {
            throw new UsernameNotFoundException(String.format("User not found by email: %s", userResetPasswordBody.getEmail()));
        }
    }

    @Override
    public void resetPasswordSave(UserResetPasswordBody userResetPasswordBody) {
        ValidationUser.validResetNewPassword(userResetPasswordBody);
        User userByToken = resetPasswordTokenService.confirmToken(userResetPasswordBody.getToken());
        Optional<User> userOptional = findByEmail(userByToken.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String encodePassword = encodePassword(userResetPasswordBody.getPassword());
            user.setPassword(encodePassword);
            user.setNotLocked(true);
            List<TokenCheckSum> tokenCheckSums = user.getTokenCheckSums();
            tokenCheckSumService.deleteAllSumsForUser(user);
            tokenCheckSums.clear();
            sentMailAccountBlockedService.deleteByUser(user);
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException(String.format("User not found by email: %s", userResetPasswordBody.getEmail()));
        }
    }

    @Override
    public void setNewPassword(User user, String newPassword) {
        ValidationUser.validPassword(newPassword);
        String encodedNewPassword = encodePassword(newPassword);
        user.setPassword(encodedNewPassword);
        List<TokenCheckSum> tokenCheckSums = user.getTokenCheckSums();
        tokenCheckSumService.deleteAllSumsForUser(user);
        tokenCheckSums.clear();
        userRepository.save(user);
        emailService.yourPasswordChangedCorrectly(user.getEmail());
    }

    @Override
    public TokenCheckSumResponse generateCheckSum(User user, String jwtToken) {
        String sum = RandomStringUtils.randomNumeric(20);
        String md5Hex = DigestUtils.md5Hex(sum);
        TokenCheckSum checkSum = tokenCheckSumService.getNewSum(user, jwtToken, md5Hex);
        user.getTokenCheckSums().add(checkSum);
        userRepository.save(user);
        return new TokenCheckSumResponse(checkSum.getId(), sum);
    }

    @Override
    public boolean isValidToken(User user, String token, String checkSumId, String checkSumProperties) {
        long id;
        try {
            id = Long.parseLong(checkSumId);
        } catch (NumberFormatException e) {
            throw new BadCredentialsException(e.getMessage());
        }
        token = token.substring(SecurityConstant.TOKEN_PREFIX.length());
        String md5Hex = DigestUtils.md5Hex(checkSumProperties);
        int tokenHashCode = token.hashCode();
        return tokenCheckSumService.checkSum(id, md5Hex, tokenHashCode);
    }

    public void accountBlocked(String email) {
        Optional<User> optionalUser = findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            sentMailAccountBlockedService.checkAndSendEmail(user);
        }
    }

    public UserResponse login(UserRegisterBody userRequest) {
        String email = userRequest.getEmail();
        String password = userRequest.getPassword();
        ValidationUser.validLogin(email, password);
        Optional<User> byEmail = findByEmail(email);
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (user.getPassword().equals(password)) {
                return new UserToUserResponse().convert(user);
            } else {
                throw new LoginException("Email or password is not correct.");
            }
        } else {
            throw new LoginException("Email is not correct.");
        }

    }

    private void updateAddress(UserRequest userRequest, User user) {
        if (user.getAddress() == null && userRequest.getAddress() != null) {
            Address address = addressService.save(userRequest.getAddress());
            user.setAddress(address);
        } else if (user.getAddress() != null && userRequest.getAddress() != null) {
            userRequest.getAddress().setId(user.getAddress().getId());
            addressService.update(userRequest.getAddress());
        } else if (user.getAddress() != null && userRequest.getAddress() == null) {
            Long id = user.getAddress().getId();
            user.setAddress(null);
            addressService.deleteById(id);
        }
    }

    private void updateAvatar(UserRequest userRequest, User user) {
        if (user.getAvatar() == null && userRequest.getAvatar() != null) {
            Photo photo = new Photo(userRequest.getAvatar());
            Photo photoSaved = photoService.save(photo);
            user.setAvatar(photoSaved);
        } else if (user.getAvatar() != null && userRequest.getAvatar() != null) {
            user.getAvatar().setUrl(userRequest.getAvatar());
        } else if (user.getAvatar() != null && userRequest.getAvatar() == null) {
            Long id = user.getAvatar().getId();
            user.setAvatar(null);
            photoService.deleteById(id);
        }
    }

    private void updatePhone(UserRequest userRequest, User user) {
        if (user.getPhone() == null && userRequest.getPhone() != null) {
            Phone phone = new Phone(userRequest.getPhone());
            Phone phoneSaved = phoneService.save(phone);
            user.setPhone(phoneSaved);
        } else if (user.getPhone() != null && userRequest.getPhone() != null) {
            user.getPhone().setNumber(userRequest.getPhone());
        } else if (user.getPhone() != null && userRequest.getPhone() == null) {
            Long id = user.getPhone().getId();
            user.setPhone(null);
            phoneService.deleteById(id);
        }
    }

    private void businessToBusinessNewData(User user, User userNewData) {
        user.setEmail(userNewData.getEmail());
        user.setPassword(userNewData.getPassword());
        user.setFirstName(userNewData.getFirstName());
        user.setLastName(userNewData.getLastName());
        user.setGender(userNewData.getGender());
        user.setBirthDate(userNewData.getBirthDate());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new UsernameNotFoundException(String.format("User not found by email - %s", email));
        });
        validateLoginAttempt(user);
        user.setLastLoginDateDisplay(user.getLastLoginDate());
        user.setLastLoginDate(new Date());
        userRepository.save(user);
        return new UserPrincipal(user);
    }

    private void validateLoginAttempt(User user) {
        if (user.isNotLocked()) {
            user.setNotLocked(!loginAttemptService.hasExceededMaxAttempts(user.getEmail()));
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getEmail());
        }
    }


    public void confirmMail(String token) {
        User user = confirmationTokenService.confirmToken(token);
        if (user == null) {
            throw new LinkHasExpiredException("Link has expired");
        } else {
            user.setActive(true);
        }
    }


    public void checkBans(User user) {
        List<Bans> bans = user.getBans();
        LocalDateTime now = LocalDateTime.now();
        for (Bans ban : bans) {
            if (ban.getExpiredTime().isAfter(now)) {
                throw new UserHasBanException(
                        String.format("User %s has ban for %s to %s, Description: %s",
                                user.getEmail(),
                                ban.getCreatedTime(),
                                ban.getExpiredTime(),
                                ban.getDescription()));
            }
        }
    }
}

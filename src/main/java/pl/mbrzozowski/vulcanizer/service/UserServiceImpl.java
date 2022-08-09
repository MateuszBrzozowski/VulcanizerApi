package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
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
import pl.mbrzozowski.vulcanizer.dto.mapper.UserToUserResponse;
import pl.mbrzozowski.vulcanizer.entity.*;
import pl.mbrzozowski.vulcanizer.enums.converter.Converter;
import pl.mbrzozowski.vulcanizer.exceptions.EmailExistException;
import pl.mbrzozowski.vulcanizer.exceptions.LinkHasExpiredException;
import pl.mbrzozowski.vulcanizer.exceptions.UserHasBanException;
import pl.mbrzozowski.vulcanizer.repository.UserRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationUser;

import java.time.LocalDate;
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


    public UserResponse update(User user, UserRequest userRequest) {
        updateEmail(user, userRequest);
        updateFirstName(user, userRequest);
        updateLastName(user, userRequest);
        updatePhone(user, userRequest);
        user.setGender(userRequest.getGender());
        updateBirthDate(user, userRequest);
        resetTokenCheckSum(user);
        return new UserToUserResponse().convert(userRepository.save(user));
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
            resetTokenCheckSum(user);
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
        resetTokenCheckSum(user);
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

    @Override
    public UserResponse saveAddress(User user, AddressRequest addressRequest) {
        if (user.getAddress() == null && addressRequest != null) { // dodawanie nowego adresu
            Address address = addressService.saveForUser(addressRequest);
            user.setAddress(address);
        } else if (user.getAddress() != null && addressRequest != null) { // update
            Address address = addressService.updateForUser(user.getAddress(), addressRequest);
            user.setAddress(address);
        } else if (user.getAddress() != null && addressRequest == null) { //remove
            Long id = user.getAddress().getId();
            user.setAddress(null);
            addressService.deleteById(id);
        }
        return new UserToUserResponse().convert(userRepository.save(user));
    }

    @Override
    public List<UserBusinessesResponse> findAllBusiness(User user) {
        log.info(String.valueOf(user.getEmployees().size()));
        if (user.getEmployees().size() == 0) {
            return null;
        } else {
            List<UserBusinessesResponse> userBusinessesResponseList = new ArrayList<>();
            user.getEmployees().forEach(employee -> {
                Business business = employee.getBusinessId();
                String status = Converter.getBusinessStatus(business.isActive(), business.isLocked(), business.isClosed());
                UserBusinessesResponse userBusinessesResponse = new UserBusinessesResponse(
                        employee.getRole().name(),
                        business.getId().toString(),
                        business.getDisplayName(),
                        status);
                userBusinessesResponseList.add(userBusinessesResponse);
            });
            return userBusinessesResponseList;
        }
    }

    public void accountBlocked(String email) {
        Optional<User> optionalUser = findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            sentMailAccountBlockedService.checkAndSendEmail(user);
        }
    }

    private void updateBirthDate(User user, UserRequest userRequest) {
        if (StringUtils.isNotEmpty(userRequest.getBirthDate())) {
            try {
                LocalDate date = LocalDate.parse(userRequest.getBirthDate());
                user.setBirthDate(date);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid date or format (YYYY-MM-DD)");
            }
        } else {
            user.setBirthDate(null);
        }
    }

    private void updateLastName(User user, UserRequest userRequest) {
        if (StringUtils.isNotBlank(userRequest.getLastName())) {
            if (!user.getLastName().equalsIgnoreCase(userRequest.getLastName())) {
                user.setLastName(userRequest.getLastName());
            }
        }
    }

    private void updateFirstName(User user, UserRequest userRequest) {
        if (StringUtils.isNotBlank(userRequest.getFirstName())) {
            if (!user.getFirstName().equalsIgnoreCase(userRequest.getFirstName())) {
                user.setFirstName(userRequest.getFirstName());
            }
        }
    }

    private void updateEmail(User user, UserRequest userRequest) {
        if (StringUtils.isNotBlank(userRequest.getEmail())) {
            userRequest.setEmail(userRequest.getEmail().toLowerCase());
            if (!user.getEmail().equalsIgnoreCase(userRequest.getEmail())) {
                Optional<User> userOptional = findByEmail(userRequest.getEmail());
                if (userOptional.isPresent()) {
                    throw new EmailExistException("Email is exist.");
                }
                user.setEmail(userRequest.getEmail());
            }
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

    private void updatePhone(User user, UserRequest userRequest) {
        if (user.getPhone() == null && StringUtils.isNotBlank(userRequest.getPhone())) {
            Phone phone = new Phone(userRequest.getPhone());
            Phone phoneSaved = phoneService.save(phone);
            user.setPhone(phoneSaved);
        } else if (user.getPhone() != null && StringUtils.isNotBlank(userRequest.getPhone())) {
            phoneService.update(user.getPhone(), userRequest.getPhone());
        } else if (user.getPhone() != null && StringUtils.isBlank(userRequest.getPhone())) {
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

    private void resetTokenCheckSum(User user) {
        List<TokenCheckSum> tokenCheckSums = user.getTokenCheckSums();
        tokenCheckSumService.deleteAllSumsForUser(user);
        tokenCheckSums.clear();
    }
}

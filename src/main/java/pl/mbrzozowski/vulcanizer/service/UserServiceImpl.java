package pl.mbrzozowski.vulcanizer.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mbrzozowski.vulcanizer.domain.UserPrincipal;
import pl.mbrzozowski.vulcanizer.dto.FavoritesRequest;
import pl.mbrzozowski.vulcanizer.dto.UserRegisterBody;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.dto.UserResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserRequestToUser;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserToUserResponse;
import pl.mbrzozowski.vulcanizer.entity.*;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.exceptions.LoginException;
import pl.mbrzozowski.vulcanizer.repository.UserRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
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
    protected static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User save(UserRequest userRequest) {
        if (findByEmail(userRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is ready exist.");
        }
        ValidationUser.validBeforeCreated(userRequest);

        User newUser =
                new User(userRequest.getEmail().toLowerCase(),
                        userRequest.getPassword(),
                        userRequest.getFirstName(),
                        userRequest.getLastName());


        return userRepository.save(newUser);
    }

    @Override
    public UserResponse update(UserRequest userRequest) {
        if (userRequest.getId() == null) {
            throw new IllegalArgumentException("[id] can not be null.");
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
        return new UserToUserResponse().apply(user);
    }

    @Override
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserToUserResponse().apply(user))
                .collect(Collectors.toList());
    }

    @Override
    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("User by id [" + id + "] was not found");
                });
    }

    @Override
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

    public UserResponse login(UserRegisterBody userRequest) {
        logger.info(String.valueOf(userRequest));
        String email = userRequest.getEmail();
        String password = userRequest.getPassword();
        ValidationUser.validLogin(email, password);
        Optional<User> byEmail = findByEmail(email);
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (user.getPassword().equals(password)) {
                return new UserToUserResponse().apply(user);
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
            log.error("User not found by email {}", email);
            throw new UsernameNotFoundException(String.format("User not found by email - %s", email));
        });
        user.setLastLoginDateDisplay(user.getLastLoginDate());
        user.setLastLoginDate(new Date());
        userRepository.save(user);
        UserPrincipal userPrincipal = new UserPrincipal(user);
        log.info("Returning found user by emial {}", email);
        return userPrincipal;
    }
}

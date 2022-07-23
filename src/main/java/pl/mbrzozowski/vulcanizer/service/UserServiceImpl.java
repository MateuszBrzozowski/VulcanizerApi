package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.dto.UserResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserRequestToUser;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserToUserResponse;
import pl.mbrzozowski.vulcanizer.entity.*;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.exceptions.UserWasNotFoundException;
import pl.mbrzozowski.vulcanizer.repository.UserRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AddressService addressService;
    private final PhoneService phoneService;
    private final PhotoService photoService;
    protected static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User save(UserRequest userRequest) {
        if (findByEmail(userRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is ready exist.");
        }

        User newUser =
                new User(userRequest.getEmail().toLowerCase(),
                        userRequest.getPassword(),
                        userRequest.getFirstName(),
                        userRequest.getLastName());

        ValidationUser.validBeforeCreated(newUser);

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
                    throw new UserWasNotFoundException("User by id [" + id + "] was not found");
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
    public boolean saveFavorite(Long userId, Long businessId) {
        return false;
    }

    @Override
    public boolean deleteFavorite(Long userId, Long businessId) {
        return false;
    }

    @Override
    public boolean isBusinessFavoriteForUser(Long userId, Long businessId) {
        return false;
    }

    @Override
    public ArrayList<Business> findAllFavoriteForUser(Long userId) {
        return null;
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

}

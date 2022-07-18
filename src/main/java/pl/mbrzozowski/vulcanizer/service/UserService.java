package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.AddressRequest;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.dto.UserResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserRequestToUser;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserToUserResponse;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;
import pl.mbrzozowski.vulcanizer.exceptions.NullParameterException;
import pl.mbrzozowski.vulcanizer.exceptions.UserWasNotFoundException;
import pl.mbrzozowski.vulcanizer.repository.UserRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationUser;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements ServiceLayer<UserRequest, UserResponse, User> {
    private final UserRepository userRepository;
    private final StateService stateService;
    private final AddressService addressService;

    @Override
    public User save(UserRequest userRequest) {
        User user = new UserRequestToUser(stateService).apply(userRequest);
        user.setId(null);
        user.setStatusAccount(UserStatusAccount.NOT_ACTIVATED.name());
        ValidationUser validationUser = new ValidationUser(userRepository);
        validationUser.accept(user);
        try {
            addressService.save(userRequest.getAddress());
        } catch (NullParameterException exception) {
            user.setAddress(null);
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public UserResponse update(UserRequest userRequest) {
        User isUser = findById(userRequest.getId());
        User userEdit = new UserRequestToUser(stateService).apply(userRequest);
        ValidationUser validationUser = new ValidationUser(userRepository);
        validationUser.accept(userEdit);
        if (isUser.getAddress() == null) {
            Address address = addressService.save(userRequest.getAddress());
            userEdit.setAddress(address);
        } else {
            AddressRequest addressRequest = userRequest.getAddress();
            if (addressRequest != null) {
                addressRequest.setId(isUser.getAddress().getId());
                userRequest.setAddress(addressRequest);
                userEdit = new UserRequestToUser(stateService).apply(userRequest);
            }
        }
        userEdit.setId(isUser.getId());
        userEdit.setPassword(isUser.getPassword());
        userEdit.setCreateAccountTime(isUser.getCreateAccountTime());
        userRepository.save(userEdit);
        return new UserToUserResponse().apply(userEdit);
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
//        return new UserToUserResponse().apply(user);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    public UserResponse findByEmail(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> {
                    throw new UserWasNotFoundException("User by email [" + email + "] was not found");
                });
        return new UserToUserResponse().apply(user);
    }

}

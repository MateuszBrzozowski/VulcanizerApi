package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.dto.UserResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserRequestToUser;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserToUserResponse;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;
import pl.mbrzozowski.vulcanizer.exceptions.UserWasNotFoundException;
import pl.mbrzozowski.vulcanizer.repository.UserRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationUser;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements ServiceLayer<UserRequest, UserResponse> {
    private final UserRepository userRepository;

    @Override
    public void save(UserRequest userRequest) {
        User user = new UserRequestToUser().apply(userRequest);
        user.setStatusAccount(UserStatusAccount.NOT_ACTIVATED);
        ValidationUser validationUser = new ValidationUser(userRepository);
        validationUser.accept(user);
        userRepository.save(user);
    }

    @Override
    public UserResponse update(UserRequest userRequest) {
        UserResponse isUser = findById(userRequest.getId());
        User userEdit = new UserRequestToUser().apply(userRequest);
        userEdit.setId(isUser.getId());
        userEdit.setCreateAccountTime(isUser.getCreateAccountTime());
        ValidationUser validationUser = new ValidationUser(userRepository);
        validationUser.accept(userEdit);
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
    public UserResponse findById(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new UserWasNotFoundException("User by id [" + id + "] was not found");
                });
        return new UserToUserResponse().apply(user);
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

package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.dto.UserResponse;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.exceptions.UserWasNotFoundException;
import pl.mbrzozowski.vulcanizer.repository.UserRepository;
import pl.mbrzozowski.vulcanizer.service.mapper.UserMapper;
import pl.mbrzozowski.vulcanizer.validation.ValidationUser;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void saveUser(User user) {
        ValidationUser validationUser = new ValidationUser(userRepository);
        validationUser.validUser(user);
        userRepository.save(user);
    }

    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::getUser)
                .collect(Collectors.toList());
    }

    public UserResponse findByEmail(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> {
                    throw new UserWasNotFoundException("User by email [" + email + "] was not found");
                });
        return UserMapper.getUser(user);
    }

    public UserResponse findById(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new UserWasNotFoundException("User by id [" + id + "] was not found");
                });
        return UserMapper.getUser(user);
    }

    public UserResponse update(UserRequest userRequest) {
        UserResponse isUser = findById(userRequest.getId());
        User userEdit = UserMapper.getUser(userRequest);
        userEdit.setId(isUser.getId());
        userEdit.setCreateAccountTime(isUser.getCreateAccountTime());
        ValidationUser validationUser = new ValidationUser(userRepository);
        validationUser.validUser(userEdit);
        userRepository.save(userEdit);
        return UserMapper.getUser(userEdit);
    }
}

package pl.mbrzozowski.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.entity.User;
import pl.mbrzozowski.repository.UserRepository;
import pl.mbrzozowski.validation.ValidationUser;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void addUser(User user) {
        ValidationUser.validUser(user);
        userRepository.save(user);
    }
}

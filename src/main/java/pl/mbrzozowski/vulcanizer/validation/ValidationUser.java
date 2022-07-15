package pl.mbrzozowski.vulcanizer.validation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.repository.UserRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ValidationUser {
    private final UserRepository userRepository;

    public void validUser(User user) {
        validEmail(user.getEmail());
        validPassword(user.getPassword());
        validFirstName(user.getFirstName());
        validLastName(user.getLastName());
        validAccountCreateTime(user.getCreateAccountTime());
    }

    public void validEmail(String email) {
        if (email == null) {
            throw new NullPointerException("Email can not be null");
        }
        EmailValidator emailValidator = EmailValidator.getInstance();
        boolean valid = emailValidator.isValid(email);
        if (!valid) {
            throw new IllegalArgumentException("Email is not valid!");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            User user = userRepository.findByEmail(email).get();
            if (user.getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("Email is ready exist.");
            }
        }
    }

    private void validPassword(String password) {
        if (password == null) {
            throw new NullPointerException("Password can not be null");
        }
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Password can not be empty");
        }
        if (password.length() > 30) {
            throw new IllegalArgumentException("Password to long. Max length 30 chars.");
        }
    }

    private void validFirstName(String firsName) {
        if (firsName == null) {
            throw new NullPointerException("First name can not be null");
        }
        if (firsName.isEmpty()) {
            throw new IllegalArgumentException("First name can not be empty");
        }
        if (firsName.length() > 50) {
            throw new IllegalArgumentException("First name to long. Max length 50 chars.");
        }
    }

    private void validLastName(String lastName) {
        if (lastName == null) {
            throw new NullPointerException("Last name can not be null");
        }
        if (lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name can not be empty");
        }
        if (lastName.length() > 50) {
            throw new IllegalArgumentException("Last name to long. Max length 50 chars.");
        }
    }

    private void validAccountCreateTime(LocalDateTime createAccountTime) {
        if (createAccountTime == null) {
            throw new NullPointerException("Create Account time can not be null");
        }
    }
}

package pl.mbrzozowski.vulcanizer.validation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.exceptions.NullParameterException;
import pl.mbrzozowski.vulcanizer.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class ValidationUser implements Consumer<User> {
    private static final long MIN_USER_AGE = 6;
    private final UserRepository userRepository;

    @Override
    public void accept(User user) {
        validEmail(user.getEmail(), user.getId());
        validPassword(user.getPassword());
        validFirstName(user.getFirstName());
        validLastName(user.getLastName());
        validAccountCreateTime(user.getCreateAccountTime());
        validBirthDay(user.getBirthDate());
    }

    private void validBirthDay(LocalDate birthDate) {
        if (birthDate != null) {
            if (birthDate.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Birth day is after today!");
            }
            if (birthDate.isAfter(LocalDate.now().minusYears(MIN_USER_AGE))) {
                throw new IllegalArgumentException("No minimum user age");
            }
        }
    }

    public void validEmail(String email, Long id) {
        if (email == null) {
            throw new NullParameterException("Email can not be null");
        }
        EmailValidator emailValidator = EmailValidator.getInstance();
        boolean valid = emailValidator.isValid(email);
        if (!valid) {
            throw new IllegalArgumentException("Email is not valid!");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            User user = userRepository.findByEmail(email).get();
            if (id != null) { //is edit
                if (userRepository.findById(id).isPresent()) { // is edit
                    User userTwo = userRepository.findById(id).get();
                    if (!userTwo.getId().equals(user.getId())) {
                        if (user.getEmail().equalsIgnoreCase(email)) {
                            throw new IllegalArgumentException("Email is ready exist.");
                        }
                    }
                } else if (user.getEmail().equalsIgnoreCase(email)) {
                    throw new IllegalArgumentException("Email is ready exist.");
                }
            } else if (user.getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("Email is ready exist.");
            }

        }
    }

    private void validPassword(String password) {
        if (password == null) {
            throw new NullParameterException("Password can not be null");
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
            throw new NullParameterException("First name can not be null");
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
            throw new NullParameterException("Last name can not be null");
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
            throw new NullParameterException("Create Account time can not be null");
        }
    }
}

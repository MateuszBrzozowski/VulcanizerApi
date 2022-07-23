package pl.mbrzozowski.vulcanizer.validation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.exceptions.NullParameterException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ValidationUser {
    private static final long MIN_USER_AGE = 6;

    public static void validBeforeCreated(UserRequest userRequest) {
        validEmail(userRequest.getEmail());
        validPassword(userRequest.getPassword(), userRequest.getId());
        validFirstName(userRequest.getFirstName());
        validLastName(userRequest.getLastName());
    }

    public static void validBeforeEditing(User user) {
        validEmail(user.getEmail());
        validPassword(user.getPassword(), user.getId());
        validFirstName(user.getFirstName());
        validLastName(user.getLastName());
        validBirthDay(user.getBirthDate());
    }

    private static void validBirthDay(LocalDate birthDate) {
        if (birthDate != null) {
            if (birthDate.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Birth day is after today!");
            }
            if (birthDate.isAfter(LocalDate.now().minusYears(MIN_USER_AGE))) {
                throw new IllegalArgumentException("No minimum user age");
            }
        }
    }

    public static void validEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email can not be null");
        }
        EmailValidator emailValidator = EmailValidator.getInstance();
        boolean valid = emailValidator.isValid(email);
        if (!valid) {
            throw new IllegalArgumentException("Email is not valid!");
        }
    }

    private static void validPassword(String password, Long id) {
        if (id != null) {
            //edit
            if (password != null) {
                if (password.isEmpty()) {
                    throw new NullParameterException("Password can not be empty");
                }
                if (password.length() > 30) {
                    throw new IllegalArgumentException("Password to long. Max length 30 chars.");
                }
            }
        } else {
            //add
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
    }

    private static void validFirstName(String firsName) {
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

    private static void validLastName(String lastName) {
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

    private static void validAccountCreateTime(LocalDateTime createAccountTime) {
        if (createAccountTime == null) {
            throw new NullParameterException("Create Account time can not be null");
        }
    }
}

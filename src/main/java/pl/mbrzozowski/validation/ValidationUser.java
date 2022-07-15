package pl.mbrzozowski.validation;

import org.apache.commons.validator.routines.EmailValidator;
import pl.mbrzozowski.entity.User;

import java.time.LocalDateTime;

public class ValidationUser {

    public static void validUser(User user) {
        validEmail(user.getEmail());
        validPassword(user.getPassword());
        validFirstName(user.getFirsName());
        validLastName(user.getLastName());
        validAccountCreateTime(user.getCreateAccountTime());
    }

    public static void validEmail(String email) {
        if (email == null) {
            throw new NullPointerException("Email can not be null");
        }

        EmailValidator emailValidator = EmailValidator.getInstance();
        boolean valid = emailValidator.isValid(email);
        if (!valid) {
            throw new IllegalArgumentException("Email is not valid!");
        }
    }

    private static void validPassword(String password) {
        if (password == null) {
            throw new NullPointerException("Password can not be null");
        }
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Password can not be empty");
        }
    }

    private static void validFirstName(String firsName) {
        if (firsName == null) {
            throw new NullPointerException("First name can not be null");
        }
        if (firsName.isEmpty()) {
            throw new IllegalArgumentException("First name can not be empty");
        }
    }

    private static void validLastName(String lastName) {
        if (lastName == null) {
            throw new NullPointerException("Last name can not be null");
        }
        if (lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name can not be empty");
        }
    }

    private static void validAccountCreateTime(LocalDateTime createAccountTime) {
        if (createAccountTime == null) {
            throw new NullPointerException("Create Account time can not be null");
        }
    }
}

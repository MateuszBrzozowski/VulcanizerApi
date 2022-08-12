package pl.mbrzozowski.vulcanizer.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;

class ValidationUserTest {


    @Test
    void isAnyFieldBlankForPersonalUpdate_FillAllReq_False() {
        UserRequest userRequest = UserRequest.builder()
                .firstName("Name")
                .lastName("lastName")
                .email("email")
                .phone("789456123")
                .birthDate("1999-01-01")
                .build();
        boolean result = ValidationUser.isAnyFieldBlankForPersonalUpdate(userRequest);
        Assertions.assertFalse(result);
    }

    @Test
    void isAnyFieldBlankForPersonalUpdate_NameBlank_True() {
        UserRequest userRequest = UserRequest.builder()
                .lastName("lastName")
                .email("email")
                .phone("789456123")
                .birthDate("1999-01-01")
                .build();
        boolean result = ValidationUser.isAnyFieldBlankForPersonalUpdate(userRequest);
        Assertions.assertTrue(result);
    }

    @Test
    void isAnyFieldBlankForPersonalUpdate_LastNameBlank_True() {
        UserRequest userRequest = UserRequest.builder()
                .firstName("Name")
                .email("email")
                .phone("789456123")
                .birthDate("1999-01-01")
                .build();
        boolean result = ValidationUser.isAnyFieldBlankForPersonalUpdate(userRequest);
        Assertions.assertTrue(result);
    }

    @Test
    void isAnyFieldBlankForPersonalUpdate_emailBlank_True() {
        UserRequest userRequest = UserRequest.builder()
                .firstName("Name")
                .lastName("lastName")
                .phone("789456123")
                .birthDate("1999-01-01")
                .build();
        boolean result = ValidationUser.isAnyFieldBlankForPersonalUpdate(userRequest);
        Assertions.assertTrue(result);
    }

    @Test
    void isAnyFieldBlankForPersonalUpdate_PhoneBlank_True() {
        UserRequest userRequest = UserRequest.builder()
                .firstName("Name")
                .lastName("lastName")
                .email("email")
                .birthDate("1999-01-01")
                .build();
        boolean result = ValidationUser.isAnyFieldBlankForPersonalUpdate(userRequest);
        Assertions.assertTrue(result);
    }

    @Test
    void isAnyFieldBlankForPersonalUpdate_BirthDateBlank_True() {
        UserRequest userRequest = UserRequest.builder()
                .firstName("Name")
                .lastName("lastName")
                .email("email")
                .phone("789456123")
                .build();
        boolean result = ValidationUser.isAnyFieldBlankForPersonalUpdate(userRequest);
        Assertions.assertTrue(result);
    }

    @Test
    void isAnyFieldBlankForPersonalUpdate_AllBlank_True() {
        UserRequest userRequest = UserRequest.builder()
                .build();
        boolean result = ValidationUser.isAnyFieldBlankForPersonalUpdate(userRequest);
        Assertions.assertTrue(result);
    }

}
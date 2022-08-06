package pl.mbrzozowski.vulcanizer.validation;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.AddressRequest;

class ValidationAddressTest {

    @Test
    void validForUser_AllFieldsEmpty_DoesNotThrow() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLine("")
                .city("")
                .code("")
                .state("")
                .country("")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_AlleFieldsEmpty_DoesNotThrow() {
        AddressRequest addressRequest = AddressRequest.builder()
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_AddressLineCorrect_DoesNotThrow() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLine(RandomStringUtils.randomAlphabetic(255))
                .city("")
                .code("")
                .state("")
                .country("")
                .build();
        Assertions.assertDoesNotThrow(() -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_AddressLineToLong_ThrowIllegalException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLine(RandomStringUtils.randomAlphabetic(256))
                .city("")
                .code("")
                .state("")
                .country("")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_CityCorrect_DoesNotThrow() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLine("")
                .city(RandomStringUtils.randomAlphabetic(50))
                .code("")
                .state("")
                .country("")
                .build();
        Assertions.assertDoesNotThrow(() -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_CityToLong_ThrowIllegalException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLine("")
                .city(RandomStringUtils.randomAlphabetic(51))
                .code("")
                .state("")
                .country("")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_CodeCorrect_DoesNotThrow() {
        AddressRequest addressRequest = AddressRequest.builder()
                .code("99-999")
                .build();
        Assertions.assertDoesNotThrow(() -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_CodeNotValid_ThrowIllegalArgumentException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .code("99-9999")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_CodeNotValid2_ThrowIllegalArgumentException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .code("999-999")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_CodeNotValid3_ThrowIllegalArgumentException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .code("99999")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_CodeNotValid4_ThrowIllegalArgumentException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .code("9999")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_CodeNotValid5_ThrowIllegalArgumentException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .code("99999999-")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_StateCorrect_DoesNotThrowException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .state("Dolnośląskie")
                .build();
        Assertions.assertDoesNotThrow(() -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_StateCorrect2_DoesNotThrowException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .state("Łódzkie")
                .build();
        Assertions.assertDoesNotThrow(() -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_StateCorrect3_DoesNotThrowException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .state("Małopolskie")
                .build();
        Assertions.assertDoesNotThrow(() -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_StateCorrect4_DoesNotThrowException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .state("Warmińsko-mazurskie")
                .build();
        Assertions.assertDoesNotThrow(() -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_StateNotCorrect_ThrowIllegalException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .state("Łódzkie2")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_StateNotCorrect2_ThrowIllegalException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .state("test")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_CountryCorrect_DoesNotThrow() {
        AddressRequest addressRequest = AddressRequest.builder()
                .country("Polska")
                .build();
        Assertions.assertDoesNotThrow(() -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_CountryMaxLength_DoesNotThrow() {
        AddressRequest addressRequest = AddressRequest.builder()
                .country(RandomStringUtils.randomAlphabetic(60))
                .build();
        Assertions.assertDoesNotThrow(() -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForUser_CountryToLong_ThrowIllegalArgumentException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .country(RandomStringUtils.randomAlphabetic(61))
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationAddress.validForUser(addressRequest));
    }

    @Test
    void validForBusiness_AllFieldsEmpty_ThrowIllegalException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLine("")
                .city("")
                .code("")
                .state("")
                .country("")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationAddress.validForBusiness(addressRequest));
    }

    @Test
    void validForBusiness_OneFieldEmptyLine_ThrowIllegalException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLine("")
                .city("City")
                .code("99-999")
                .state("Mazowieckie")
                .country("Polska")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationAddress.validForBusiness(addressRequest));
    }

    @Test
    void validForBusiness_OneFieldEmptyCity_ThrowIllegalException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLine("Line")
                .city("")
                .code("99-999")
                .state("Mazowieckie")
                .country("Polska")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationAddress.validForBusiness(addressRequest));
    }

    @Test
    void validForBusiness_OneFieldEmptyCode_ThrowIllegalException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLine("Line")
                .city("City")
                .code("")
                .state("Mazowieckie")
                .country("Polska")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationAddress.validForBusiness(addressRequest));
    }

    @Test
    void validForBusiness_OneFieldEmptyState_ThrowIllegalException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLine("Line")
                .city("City")
                .code("99-999")
                .state("")
                .country("Polska")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationAddress.validForBusiness(addressRequest));
    }

    @Test
    void validForBusiness_OneFieldEmptyCountry_ThrowIllegalException() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLine("Line")
                .city("City")
                .code("99-999")
                .state("Mazowieckie")
                .country("")
                .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationAddress.validForBusiness(addressRequest));
    }

    @Test
    void validForBusiness_AlleFieldsRequired_DoesNotThrow() {
        AddressRequest addressRequest = AddressRequest.builder()
                .addressLine("Line")
                .city("City")
                .code("99-999")
                .state("Mazowieckie")
                .country("Polska")
                .build();
        Assertions.assertDoesNotThrow(() -> ValidationAddress.validForBusiness(addressRequest));
    }

}
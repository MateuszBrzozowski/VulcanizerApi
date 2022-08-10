package pl.mbrzozowski.vulcanizer.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidationBusinessTest {

    @Test
    void validNip_correct_DoesNotThrow() {
        String nip = "1234563218";
        Assertions.assertDoesNotThrow(() -> ValidationCompany.validNip(nip));
    }

    @Test
    void validNip_correctWithMinus_DoesNotThrow() {
        String nip = "123-456-32-18";
        Assertions.assertDoesNotThrow(() -> ValidationCompany.validNip(nip));
    }

    @Test
    void validNip_correctWithBlank_DoesNotThrow() {
        String nip = "123 456 32 18";
        Assertions.assertDoesNotThrow(() -> ValidationCompany.validNip(nip));
    }

    @Test
    void validNip_ToLong_ThrowIllegalException() {
        String nip = "123-456-32-181";
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationCompany.validNip(nip));
    }

    @Test
    void validNip_ToShort_ThrowIllegalException() {
        String nip = "123-456-32-1";
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationCompany.validNip(nip));
    }

    @Test
    void validNip_Blank_ThrowIllegalException() {
        String nip = "";
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationCompany.validNip(nip));
    }

    @Test
    void validNip_Blank2_ThrowIllegalException() {
        String nip = " ";
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationCompany.validNip(nip));
    }

    @Test
    void validNip_NoDigit_ThrowIllegalException() {
        String nip = "123 45s 32 18 ";
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationCompany.validNip(nip));
    }

    @Test
    void validNip_InvalidCheckDigit_ThrowIllegalException() {
        String nip = "123 455 32 18 ";
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidationCompany.validNip(nip));
    }
}
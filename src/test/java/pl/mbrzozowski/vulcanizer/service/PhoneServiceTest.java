package pl.mbrzozowski.vulcanizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.entity.Phone;
import pl.mbrzozowski.vulcanizer.repository.PhoneRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

class PhoneServiceTest {
    private PhoneRepository phoneRepository;
    private PhoneService phoneService;

    @BeforeEach
    public void beforeEach() {
        phoneRepository = mock(PhoneRepository.class);
        phoneService = new PhoneService(phoneRepository);
    }

    @Test
    void save_Correct_NoThrowRepositorySave() {
        Phone phone = new Phone("666666666");
        Assertions.assertDoesNotThrow(() -> phoneService.save(phone));
        verify(phoneRepository).save(phone);
    }

    @Test
    void save_NullPhone_ThrowIllegalArgumentException() {
        Phone phone = new Phone(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> phoneService.save(phone));
    }

    @Test
    void save_EmptyPhone_ThrowIllegalArgumentException() {
        Phone phone = new Phone("");
        Assertions.assertThrows(IllegalArgumentException.class, () -> phoneService.save(phone));
    }

    @Test
    void save_PhoneNumberToLong_ThrowIllegalArgumentException() {
        Phone phone = new Phone("00486666666667");
        Assertions.assertThrows(IllegalArgumentException.class, () -> phoneService.save(phone));
    }

    @Test
    void save_PhoneNumberMaxLength_DoesNotThrow() {
        Phone phone = new Phone("+48666666666");
        Assertions.assertDoesNotThrow(() -> phoneService.save(phone));
    }

    @Test
    void save_PhoneNumberToLongWithPlus_DoesNotThrow() {
        Phone phone = new Phone("+486666666667");
        Assertions.assertThrows(IllegalArgumentException.class, () -> phoneService.save(phone));
    }

    @Test
    void save_PhoneNumberMaxLengthWithPlus_DoesNotThrow() {
        Phone phone = new Phone("+48666666666");
        Assertions.assertDoesNotThrow(() -> phoneService.save(phone));
    }

    @Test
    void save_PhoneNumberWithEmptyChars_DoeasNotThrow() {
        Phone phone = new Phone("+48 66 666 66 66");
        Assertions.assertDoesNotThrow(() -> phoneService.save(phone));
    }

    @Test
    void save_PhoneNumberWithDash_DoeasNotThrow() {
        Phone phone = new Phone("+48 66-666-66-66");
        Assertions.assertDoesNotThrow(() -> phoneService.save(phone));
    }

    @Test
    void save_NoLetters_ThrowIllegalArgumentException() {
        Phone phone = new Phone("66-66A-66-66");
        Assertions.assertThrows(IllegalArgumentException.class, () -> phoneService.save(phone));
    }

    @Test
    void update_Correct_DoesNotThrowAndVerfiryRepositorySave() {
        Phone phone = new Phone(1L, "+48666666666");
        Assertions.assertDoesNotThrow(() -> phoneService.update(phone, phone.getNumber()));
        verify(phoneRepository).save(phone);
    }

    @Test
    void update_NotValidPhoneNumber_DoesNotThrowAndVerfiryRepositorySave() {
        Phone phoneNew = new Phone(1L, "00486666666667");
        Phone phoneDB = new Phone(1L, "0048666666666");
        Assertions.assertThrows(IllegalArgumentException.class, () -> phoneService.update(phoneDB, phoneNew.getNumber()));
    }

    @Test
    void findById_Found_DoesNotThrow() {
        Phone phone = new Phone(1L, "0048666666666");
        when(phoneRepository.findById(1L)).thenReturn(Optional.of(phone));
        Assertions.assertDoesNotThrow(() -> phoneService.findById(1L));
    }

    @Test
    void findById_DoesNotFound_ThrowNoSuchElementException() {
        when(phoneRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> phoneService.findById(1L));
    }

    @Test
    void findById_NullId_ThrowNoSuchElementException() {
        when(phoneRepository.findById(null)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> phoneService.findById(null));
    }

    @Test
    void deleteById_deleted() {
        long id = 1L;
        Phone phone = new Phone(id, "55555");
        when(phoneRepository.findById(id)).thenReturn(Optional.of(phone));
        phoneService.findById(id);
        phoneService.deleteById(id);
        when(phoneRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> phoneService.findById(id));
    }

}
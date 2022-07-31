package pl.mbrzozowski.vulcanizer.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.entity.ConfirmationToken;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.repository.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
class ConfirmationTokenServiceTest {
    private ConfirmationTokenRepository confirmationTokenRepository;
    private ConfirmationTokenService confirmationTokenService;

    @BeforeEach
    public void beforeEach() {
        confirmationTokenRepository = mock(ConfirmationTokenRepository.class);
        confirmationTokenService = new ConfirmationTokenService(confirmationTokenRepository);
    }

    @Test
    void createNewToken_Success_ReturnToken() {
        User user = new User();
        when(confirmationTokenRepository.findByUser(user)).thenReturn(Optional.empty());
        String newToken = confirmationTokenService.createNewToken(user);
        log.info(newToken);
        Assertions.assertNotNull(newToken);
    }

    @Test
    void createNewToken_Success_ReturnNewToken() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Name");
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        when(confirmationTokenRepository.findByUser(user)).thenReturn(Optional.of(confirmationToken));
        String newToken = confirmationTokenService.createNewToken(user);
        log.info(newToken);
        Assertions.assertNotNull(newToken);
    }

    @Test
    void createNewToken_UserNull_ThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> confirmationTokenService.createNewToken(null));
    }

    @Test
    void confirmToken_NullUser_ThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> confirmationTokenService.confirmToken(null, null));
    }

    @Test
    void confirmToken_NullToken_ReturnFalse() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Name");
        boolean result = confirmationTokenService.confirmToken(user, null);
        Assertions.assertFalse(result);
    }

    @Test
    void confirmToken_Success_ReturnTrue() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Name");
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationToken.setToken(token);
        confirmationToken.setId(1L);
        confirmationToken.setCreatedTime(LocalDateTime.now().minusSeconds(1));
        when(confirmationTokenRepository.findByUser(user)).thenReturn(Optional.of(confirmationToken));
        boolean result = confirmationTokenService.confirmToken(user, token);
        Assertions.assertTrue(result);
    }

    @Test
    void confirmToken_TokenExpired_ReturnFalse() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Name");
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationToken.setToken(token);
        confirmationToken.setId(1L);
        confirmationToken.setCreatedTime(LocalDateTime.now().minusSeconds(1));
        confirmationToken.setExpiredTime(LocalDateTime.now().minusMinutes(16));
        when(confirmationTokenRepository.findByUser(user)).thenReturn(Optional.of(confirmationToken));
        boolean result = confirmationTokenService.confirmToken(user, token);
        Assertions.assertFalse(result);
    }
}
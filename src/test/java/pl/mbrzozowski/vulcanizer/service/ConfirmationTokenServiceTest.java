package pl.mbrzozowski.vulcanizer.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import pl.mbrzozowski.vulcanizer.repository.ConfirmationTokenRepository;

import static org.mockito.Mockito.mock;

@Slf4j
class ConfirmationTokenServiceTest {
    private ConfirmationTokenRepository confirmationTokenRepository;
    private ConfirmationTokenService confirmationTokenService;

    @BeforeEach
    public void beforeEach() {
        confirmationTokenRepository = mock(ConfirmationTokenRepository.class);
        confirmationTokenService = new ConfirmationTokenService(confirmationTokenRepository);
    }
//
//    @Test
//    void createNewToken_Success_ReturnToken() {
//        User user = new User();
//        when(confirmationTokenRepository.findByUser(user)).thenReturn(Optional.empty());
//        String newToken = confirmationTokenService.createNewToken(user);
//        log.info(newToken);
//        Assertions.assertNotNull(newToken);
//    }
//
//    @Test
//    void createNewToken_Success_ReturnNewToken() {
//        User user = new User();
//        user.setId(1L);
//        user.setFirstName("Name");
//        ConfirmationToken confirmationToken = new ConfirmationToken(user);
//        when(confirmationTokenRepository.findByUser(user)).thenReturn(Optional.of(confirmationToken));
//        String newToken = confirmationTokenService.createNewToken(user);
//        log.info(newToken);
//        Assertions.assertNotNull(newToken);
//    }
//
//    @Test
//    void createNewToken_UserNull_ThrowException() {
//        Assertions.assertThrows(IllegalArgumentException.class, () -> confirmationTokenService.createNewToken(null));
//    }
//
//    @Test
//    void confirmToken_NullToken_ReturnFalse() {
//        User user = new User();
//        user.setId(1L);
//        user.setFirstName("Name");
//        boolean result = confirmationTokenService.confirmToken(null);
//        Assertions.assertFalse(result);
//    }
//
//    @Test
//    void confirmToken_Success_ReturnTrue() {
//        User user = new User();
//        user.setId(1L);
//        user.setFirstName("Name");
//        String token = UUID.randomUUID().toString();
//        ConfirmationToken confirmationToken = new ConfirmationToken(user);
//        confirmationToken.setToken(token);
//        confirmationToken.setId(1L);
//        confirmationToken.setCreatedTime(LocalDateTime.now().minusSeconds(1));
//        when(confirmationTokenRepository.findByUser(user)).thenReturn(Optional.of(confirmationToken));
//        boolean result = confirmationTokenService.confirmToken(token);
//        Assertions.assertTrue(result);
//    }
//
//    @Test
//    void confirmToken_TokenExpired_ReturnFalse() {
//        User user = new User();
//        user.setId(1L);
//        user.setFirstName("Name");
//        String token = UUID.randomUUID().toString();
//        ConfirmationToken confirmationToken = new ConfirmationToken(user);
//        confirmationToken.setToken(token);
//        confirmationToken.setId(1L);
//        confirmationToken.setCreatedTime(LocalDateTime.now().minusSeconds(1));
//        confirmationToken.setExpiredTime(LocalDateTime.now().minusMinutes(16));
//        when(confirmationTokenRepository.findByUser(user)).thenReturn(Optional.of(confirmationToken));
//        boolean result = confirmationTokenService.confirmToken(token);
//        Assertions.assertFalse(result);
//    }
}
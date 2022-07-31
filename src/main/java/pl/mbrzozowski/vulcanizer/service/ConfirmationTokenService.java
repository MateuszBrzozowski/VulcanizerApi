package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.entity.ConfirmationToken;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.repository.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;


    /**
     * Confirm token for user
     *
     * @param user  for which confirm token
     * @param token which confirm
     * @return true if token is not expired and is possible, otherwise return false
     */
    public boolean confirmToken(User user, String token) {
        if (user == null) {
            throw new IllegalArgumentException("User can not be empty");
        }
        Optional<ConfirmationToken> tokenDB = findByUser(user);
        if (tokenDB.isPresent()) {
            ConfirmationToken confirmationToken = tokenDB.get();
            boolean expired = isExpired(confirmationToken);
            if (!expired) {
                boolean tokenCorrect = isTokenCorrect(token, confirmationToken);
                if (tokenCorrect) {
                    confirmationToken.setConfirmTime(LocalDateTime.now());
                    confirmationTokenRepository.save(confirmationToken);
                    return true;
                }
            } else {
                confirmationTokenRepository.delete(confirmationToken);
                return false;
            }
        }
        return false;
    }

    private boolean isTokenCorrect(String token, ConfirmationToken confirmationToken) {
        return confirmationToken.getToken().equals(token);
    }

    /**
     * Creates token for user. If user's token is existing create new token.
     * Token is active 15 minutes from the time of creation
     *
     * @param user for which created token
     * @return token String
     */
    public String createNewToken(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User can not by empty");
        }
        Optional<ConfirmationToken> tokenByUser = findByUser(user);
        if (tokenByUser.isPresent()) {
            ConfirmationToken confirmationToken = tokenByUser.get();
            String token = confirmationToken.resetToken();
            confirmationTokenRepository.save(confirmationToken);
            return token;
        } else {
            return save(user);
        }
    }

    private String save(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        return confirmationToken.getToken();
    }

    private Optional<ConfirmationToken> findByToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    private Optional<ConfirmationToken> findByUser(User user) {
        return confirmationTokenRepository.findByUser(user);
    }

    /**
     * Checking is token expired
     *
     * @param confirmationToken Instance of {@link ConfirmationToken}
     * @return true if token is expired, otherwise return false
     */
    private boolean isExpired(ConfirmationToken confirmationToken) {
        LocalDateTime expiredTime = confirmationToken.getExpiredTime();
        if (expiredTime != null) {
            LocalDateTime now = LocalDateTime.now();
            return expiredTime.isBefore(now);
        }
        return true;
    }
}

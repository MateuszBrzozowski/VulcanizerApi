package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.entity.ResetPasswordToken;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.repository.ResetPasswordTokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResetPasswordTokenService implements TokenService {
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;


    @Override
    public User confirmToken(String token) {
        Optional<ResetPasswordToken> tokenDB = findByToken(token);
        if (tokenDB.isPresent()) {
            ResetPasswordToken resetPasswordToken = tokenDB.get();
            boolean expired = isExpired(resetPasswordToken);
            boolean confirm = isConfirm(resetPasswordToken);
            if (confirm) {
                return resetPasswordToken.getUser();
            }
            if (!expired) {
                resetPasswordToken.setConfirmTime(LocalDateTime.now());
                resetPasswordTokenRepository.save(resetPasswordToken);
                return resetPasswordToken.getUser();
            } else {
                resetPasswordTokenRepository.deleteById(resetPasswordToken.getId());
                return null;
            }
        }
        return null;
    }

    @Override
    public String createNewToken(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User can not be empty");
        }
        Optional<ResetPasswordToken> resetPasswordTokenByUser = findByUser(user);
        if (resetPasswordTokenByUser.isPresent()) {
            ResetPasswordToken resetPasswordToken = resetPasswordTokenByUser.get();
            String token = resetPasswordToken.resetToken();
            resetPasswordTokenRepository.save(resetPasswordToken);
            return token;
        } else {
            return generateToken(user);
        }
    }

    private String generateToken(User user) {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(user);
        resetPasswordTokenRepository.save(resetPasswordToken);
        return resetPasswordToken.getToken();
    }

    private Optional<ResetPasswordToken> findByUser(User user) {
        return resetPasswordTokenRepository.findByUser(user);
    }

    private boolean isExpired(ResetPasswordToken resetPasswordToken) {
        LocalDateTime expiredTime = resetPasswordToken.getExpiredTime();
        if (expiredTime != null) {
            LocalDateTime now = LocalDateTime.now();
            return expiredTime.isBefore(now);
        }
        return true;
    }

    private boolean isConfirm(ResetPasswordToken resetPasswordToken) {
        return resetPasswordToken.getConfirmTime() != null;
    }

    private Optional<ResetPasswordToken> findByToken(String token) {
        return resetPasswordTokenRepository.findByToken(token);
    }
}

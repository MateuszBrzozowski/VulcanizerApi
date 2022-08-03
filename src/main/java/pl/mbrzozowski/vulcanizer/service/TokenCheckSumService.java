package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.entity.TokenCheckSum;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.repository.TokenCheckSumRepository;

import java.time.LocalDateTime;

import static pl.mbrzozowski.vulcanizer.constant.SecurityConstant.EXPIRATION_TIME;

@Service
@RequiredArgsConstructor
public class TokenCheckSumService {
    private final TokenCheckSumRepository tokenCheckSumRepository;

    public TokenCheckSum getNewSum(User user, String token, String md5Hex) {
        TokenCheckSum tokenCheckSum = new TokenCheckSum();
        tokenCheckSum.setUser(user);
        tokenCheckSum.setTokenHash(token.hashCode());
        tokenCheckSum.setExpiredTimed(LocalDateTime.now().plusMinutes(EXPIRATION_TIME / 1000 / 60));
        tokenCheckSum.setSum(md5Hex);
        return tokenCheckSumRepository.save(tokenCheckSum);
    }

    public boolean checkSum(Long id, String md5Hex, int tokenHash) {
        TokenCheckSum checkSum = findById(id);
        if (checkSum == null) {
            return false;
        }
        if (tokenHash != checkSum.getTokenHash()) {
            return false;
        }
        return md5Hex.equals(checkSum.getSum());
    }

    public void deleteAllSumsForUser(User user) {
        tokenCheckSumRepository.deleteByUser(user);
    }

    private TokenCheckSum findById(Long id) {
        return tokenCheckSumRepository.findById(id).orElse(null);
    }
}

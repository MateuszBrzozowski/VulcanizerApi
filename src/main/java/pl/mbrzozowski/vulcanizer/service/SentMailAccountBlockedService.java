package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.entity.SentMailAccountBlocked;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.repository.SentMailAccountBlockedRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SentMailAccountBlockedService {
    private final SentMailAccountBlockedRepository sentMailAccountBlockedRepository;
    private final EmailService emailService;

    public void checkAndSendEmail(User user) {
        Optional<SentMailAccountBlocked> sentMailAccountBlockedOptional = findByUser(user);
        if (sentMailAccountBlockedOptional.isPresent()) {
            SentMailAccountBlocked sentMailAccountBlocked = sentMailAccountBlockedOptional.get();
            if (!sentMailAccountBlocked.isSent()) {
                sendEmail(user, sentMailAccountBlocked);
                sentMailAccountBlockedRepository.save(sentMailAccountBlocked);
            }
        } else {
            save(user);
        }
    }

    private void sendEmail(User user, SentMailAccountBlocked sentMailAccountBlocked) {
        emailService.yourAccountWasBlocked(user.getEmail());
        sentMailAccountBlocked.setSent(true);
    }

    private void save(User user) {
        SentMailAccountBlocked sentMailAccountBlocked = new SentMailAccountBlocked(user);
        sendEmail(user, sentMailAccountBlocked);
        sentMailAccountBlockedRepository.save(sentMailAccountBlocked);
    }

    private Optional<SentMailAccountBlocked> findByUser(User user) {
        return sentMailAccountBlockedRepository.findByUser(user);
    }

    public void deleteByUser(User user) {
        sentMailAccountBlockedRepository.deleteByUser(user);
    }
}

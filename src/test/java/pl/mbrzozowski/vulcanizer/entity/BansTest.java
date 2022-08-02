package pl.mbrzozowski.vulcanizer.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.enums.TimeUnit;

import java.time.LocalDateTime;

@Slf4j
class BansTest {
    User user = new User();

    @BeforeEach
    public void beforeEach() {
        user.setId(1L);
        user.setFirstName("Name");
        user.setLastName("LastName");
        user.setPassword("password");
    }

    @Test
    void createBansForHours_True() {
        Bans ban = new Bans(user, "description", TimeUnit.HOURS, 1);
        LocalDateTime expiredTime = ban.getExpiredTime();
        Assertions.assertNotNull(expiredTime);
        LocalDateTime exceptedExpiredTime = LocalDateTime.now().plusHours(1).plusMinutes(1);
        boolean before = expiredTime.isBefore(exceptedExpiredTime);
        Assertions.assertTrue(before);
    }

    @Test
    void createBansForHours_False() {
        Bans ban = new Bans(user, "description", TimeUnit.HOURS, 1);
        LocalDateTime expiredTime = ban.getExpiredTime();
        Assertions.assertNotNull(expiredTime);
        LocalDateTime exceptedExpiredTime = LocalDateTime.now().plusHours(1).minusMinutes(1);
        boolean before = expiredTime.isBefore(exceptedExpiredTime);
        Assertions.assertFalse(before);
    }

    @Test
    void createBansForMinutes_True() {
        Bans ban = new Bans(user, "description", TimeUnit.MINUTES, 1);
        LocalDateTime expiredTime = ban.getExpiredTime();
        Assertions.assertNotNull(expiredTime);
        LocalDateTime exceptedExpiredTime = LocalDateTime.now().plusMinutes(1).plusSeconds(10);
        boolean before = expiredTime.isBefore(exceptedExpiredTime);
        Assertions.assertTrue(before);
    }

    @Test
    void createBansForMinutes_False() {
        Bans ban = new Bans(user, "description", TimeUnit.MINUTES, 1);
        LocalDateTime expiredTime = ban.getExpiredTime();
        Assertions.assertNotNull(expiredTime);
        LocalDateTime exceptedExpiredTime = LocalDateTime.now().plusMinutes(1).minusSeconds(10);
        boolean before = expiredTime.isBefore(exceptedExpiredTime);
        Assertions.assertFalse(before);
    }

    @Test
    void createBansForDays_True() {
        Bans ban = new Bans(user, "description", TimeUnit.DAYS, 1);
        LocalDateTime expiredTime = ban.getExpiredTime();
        Assertions.assertNotNull(expiredTime);
        LocalDateTime exceptedExpiredTime = LocalDateTime.now().plusDays(1).plusMinutes(1);
        boolean before = expiredTime.isBefore(exceptedExpiredTime);
        Assertions.assertTrue(before);
    }

    @Test
    void createBansForDays_False() {
        Bans ban = new Bans(user, "description", TimeUnit.DAYS, 1);
        LocalDateTime expiredTime = ban.getExpiredTime();
        Assertions.assertNotNull(expiredTime);
        LocalDateTime exceptedExpiredTime = LocalDateTime.now().plusDays(1).minusMinutes(1);
        boolean before = expiredTime.isBefore(exceptedExpiredTime);
        Assertions.assertFalse(before);
    }

    @Test
    void createBansForMonths_True() {
        Bans ban = new Bans(user, "description", TimeUnit.MONTHS, 1);
        LocalDateTime expiredTime = ban.getExpiredTime();
        Assertions.assertNotNull(expiredTime);
        LocalDateTime exceptedExpiredTime = LocalDateTime.now().plusMonths(1).plusMinutes(1);
        boolean before = expiredTime.isBefore(exceptedExpiredTime);
        Assertions.assertTrue(before);
    }

    @Test
    void createBansForMonths_False() {
        Bans ban = new Bans(user, "description", TimeUnit.MONTHS, 1);
        LocalDateTime expiredTime = ban.getExpiredTime();
        Assertions.assertNotNull(expiredTime);
        LocalDateTime exceptedExpiredTime = LocalDateTime.now().plusMonths(1).minusMinutes(1);
        boolean before = expiredTime.isBefore(exceptedExpiredTime);
        Assertions.assertFalse(before);
    }

    @Test
    void createPermBan(){
        Bans ban = new Bans(user, "description", TimeUnit.PERM, 1);
        LocalDateTime expiredTime = ban.getExpiredTime();
        Assertions.assertNull(expiredTime);
    }
}
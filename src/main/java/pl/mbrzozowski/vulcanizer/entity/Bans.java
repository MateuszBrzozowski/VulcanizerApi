package pl.mbrzozowski.vulcanizer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.enums.TimeUnit;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity(name = "bans")
public class Bans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String description;
    private LocalDateTime createdTime;
    private LocalDateTime expiredTime; // when null ban is perm

    /**
     * Cretaed ban for user with specific time
     *
     * @param user          for which is created ban
     * @param description   of ban
     * @param timeUnit      for how long it is banned {@link TimeUnit}
     * @param timeToExpired exactly how much time to expired ban. For created perm ban this parameter is overlooked
     */
    public Bans(User user, String description, TimeUnit timeUnit, int timeToExpired) {
        this.user = user;
        this.description = description;
        createdTime = LocalDateTime.now();
        setExpiredTimeByTimeUnit(timeUnit, timeToExpired);
    }

    private void setExpiredTimeByTimeUnit(TimeUnit timeUnit, int timeToExpired) {
        switch (timeUnit) {
            case MINUTES -> expiredTime = LocalDateTime.now().plusMinutes(timeToExpired);
            case HOURS -> expiredTime = LocalDateTime.now().plusHours(timeToExpired);
            case DAYS -> expiredTime = LocalDateTime.now().plusDays(timeToExpired);
            case MONTHS -> expiredTime = LocalDateTime.now().plusMonths(timeToExpired);
            default -> expiredTime = null;
        }
    }
}

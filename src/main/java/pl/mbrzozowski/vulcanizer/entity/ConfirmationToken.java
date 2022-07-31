package pl.mbrzozowski.vulcanizer.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "confirm_token")
public class ConfirmationToken {
    @Transient
    private static final int EXPIRED_TIME = 15; // in minutes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String token;
    private LocalDateTime createdTime;
    private LocalDateTime expiredTime;
    private LocalDateTime confirmTime;

    public ConfirmationToken(User userId) {
        this.user = userId;
        token = UUID.randomUUID().toString();
        createdTime = LocalDateTime.now();
        expiredTime = LocalDateTime.now().plusMinutes(EXPIRED_TIME);
    }

    public String resetToken() {
        token = UUID.randomUUID().toString();
        createdTime = LocalDateTime.now();
        expiredTime = LocalDateTime.now().plusMinutes(EXPIRED_TIME);
        confirmTime = null;
        return token;
    }
}

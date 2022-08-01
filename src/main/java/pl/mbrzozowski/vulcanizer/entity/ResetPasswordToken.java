package pl.mbrzozowski.vulcanizer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity(name = "reset_password_token")
public class ResetPasswordToken {
    @Transient
    private static final int EXPIRED_TIME = 10; // in minutes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    @Column(length = 160)
    private String token;
    private LocalDateTime createdTime;
    private LocalDateTime expiredTime;
    private LocalDateTime confirmTime;

    public ResetPasswordToken(User user) {
        this.user = user;
        setNewToken();
    }

    public String resetToken() {
        setNewToken();
        return token;
    }

    private void setNewToken() {
        token = RandomStringUtils.randomAlphanumeric(150, 160);
        createdTime = LocalDateTime.now();
        expiredTime = LocalDateTime.now().plusMinutes(EXPIRED_TIME);
        confirmTime = null;
    }
}

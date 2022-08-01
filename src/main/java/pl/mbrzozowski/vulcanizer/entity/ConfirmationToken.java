package pl.mbrzozowski.vulcanizer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity(name = "confirm_token")
@NoArgsConstructor
public class ConfirmationToken {
    @Transient
    private static final int EXPIRED_TIME = 15; // in minutes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
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

    @Override
    public String toString() {
        return "ConfirmationToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfirmationToken that = (ConfirmationToken) o;
        return Objects.equals(id, that.id) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token);
    }
}

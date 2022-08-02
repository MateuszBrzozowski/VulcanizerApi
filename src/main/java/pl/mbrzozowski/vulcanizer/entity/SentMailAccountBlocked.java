package pl.mbrzozowski.vulcanizer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "sent_mail_account_blocked")
public class SentMailAccountBlocked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    private boolean isSent;

    public SentMailAccountBlocked(User user) {
        this.user = user;
        isSent = false;
    }
}

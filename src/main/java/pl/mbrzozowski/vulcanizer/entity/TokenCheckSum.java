package pl.mbrzozowski.vulcanizer.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity(name = "token_check_sum")
public class TokenCheckSum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int tokenHash;
    private String sum;
    private LocalDateTime expiredTimed;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}

package pl.mbrzozowski.vulcanizer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.enums.VisitStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "visit")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @Enumerated(EnumType.STRING)
    private VisitStatus status = VisitStatus.NEW_VISIT;
    @OneToOne
    @JoinColumn(name = "id_opinion")
    private Opinion opinion;

    @Column(name = "time_start")
    private LocalDateTime startTime;

    public Visit(User user, LocalDateTime startTime) {
        this.user = user;
        this.startTime = startTime;
    }
}

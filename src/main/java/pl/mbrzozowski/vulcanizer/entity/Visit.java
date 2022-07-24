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
    @ManyToOne
    @JoinColumn(name = "id_service")
    private BusinessServices service;
    @Enumerated(EnumType.STRING)
    private VisitStatus status = VisitStatus.NEW_VISIT;

    @Column(name = "time_start")
    private LocalDateTime startTime;

    public Visit(User user, BusinessServices service, LocalDateTime startTime) {
        this.user = user;
        this.service = service;
        this.startTime = startTime;
    }
}

package pl.mbrzozowski.vulcanizer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Service service;

    // TODO
    //  : CREATE ENUM!
    private String status;

    @Column(name = "time_start")
    private LocalDateTime startTime;

    public Visit(User user, Service service, String status, LocalDateTime startTime) {
        this.user = user;
        this.service = service;
        this.status = status;
        this.startTime = startTime;
    }
}

package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.enums.VisitStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitRequest {
    private Long id;
    private Long user;
    private Long service;
    private VisitStatus status;
    private LocalDateTime startTime;

    public VisitRequest(Long user, Long service, LocalDateTime startTime) {
        this.user = user;
        this.service = service;
        this.startTime = startTime;
    }
}

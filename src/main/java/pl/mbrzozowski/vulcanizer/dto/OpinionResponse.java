package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpinionResponse {

    private Long id;
    private int stars;
    private String description;
    private boolean visibility;
    private LocalDateTime createdTime;
    private String authorName;
}

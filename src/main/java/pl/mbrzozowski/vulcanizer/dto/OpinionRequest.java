package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpinionRequest {

    private Long id;
    private User user;
    private Business business;
    private int stars;
    private String description;
    private boolean visibility;
    private LocalDateTime createdTime = LocalDateTime.now();
    private String authorName;
}

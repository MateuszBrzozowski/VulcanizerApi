package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpinionRequest {

    private Long id;
    private Long user;
    private Long business;
    private Long visit;
    private int stars;
    private String description;
    private boolean visibility;
}

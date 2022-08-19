package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomOpeningHoursRequest {
    private String dateStart;
    private String dateEnd;
    private String timeStart;
    private String timeEnd;
}

package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomOpeningHoursRequest {
    private String dateStart;
    private String dateEnd;
    private String timeStart;
    private String timeEnd;
}

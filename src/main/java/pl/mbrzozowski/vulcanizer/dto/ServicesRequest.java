package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicesRequest {
    private String id;
    private String price;
    private String name;
    private String time;
    private String typOfServices;
    private String wheelType;
    private String sizeFrom;
    private String sizeTo;
}

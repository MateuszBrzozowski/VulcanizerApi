package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserBusinessesResponse {
    private String position;
    private String businessId;
    private String businessName;
    private String businessStatus;
}

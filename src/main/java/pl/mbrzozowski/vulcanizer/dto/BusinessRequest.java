package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessRequest {
    private Long id;
    private String name;
    private String displayName;
    private String nip;
    private String description;
    private AddressRequest address;
    private String photo;
    private String phoneFirst;
    private String phoneSecond;
}

package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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
    private Set<String> phones;
}

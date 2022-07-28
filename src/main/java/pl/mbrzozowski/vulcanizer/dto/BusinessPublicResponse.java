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
public class BusinessPublicResponse {
    private Long id;
    private String name;
    private String description;
    private AddressResponse address;
    private String photo;
    private Set<String> phones;
}

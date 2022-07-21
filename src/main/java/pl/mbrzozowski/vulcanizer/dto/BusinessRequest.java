package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.entity.Phone;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessRequest {
    private Long id;
    private Long userId;
    private String name;
    private String nip;
    private String description;
    private AddressRequest address;
    private String photo;
    private Set<Phone> phones;
}

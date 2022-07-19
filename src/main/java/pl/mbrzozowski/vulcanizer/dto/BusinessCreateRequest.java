package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.entity.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessCreateRequest {
    private Long userId;
    private String name;
    private String nip;
    private String description;
    private Address address;
    private String photo;
}

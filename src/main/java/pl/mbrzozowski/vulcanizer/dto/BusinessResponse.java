package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.entity.Address;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessResponse {
    private Long id;
    private String name;
    private Long nip;
    private LocalDate createdDate;
    private String description;
    private String status;
    private Address address;
    private String photo;
}

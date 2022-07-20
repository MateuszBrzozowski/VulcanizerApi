package pl.mbrzozowski.vulcanizer.dto;

import lombok.*;
import pl.mbrzozowski.vulcanizer.entity.Address;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessResponse {
    private Long id;
    private String name;
    private Long nip;
    private LocalDateTime createdDate;
    private String description;
    private String status;
    private AddressResponse address;
    private String photo;
}

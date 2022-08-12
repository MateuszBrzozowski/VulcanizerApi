package pl.mbrzozowski.vulcanizer.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {
    private Long id;
    private String nip;
    private String name;
    private LocalDateTime createdDate;
    private String status;
    private AddressResponse address;
    private String phone;
}

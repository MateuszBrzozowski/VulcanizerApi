package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest {
    private Long id;
    private String name;
    private String nip;
    private AddressRequest address;
    private String phone;
    private String nameCB;
    private String descriptionCB;
    private AddressRequest addressCB;
    private String phoneCB;
}

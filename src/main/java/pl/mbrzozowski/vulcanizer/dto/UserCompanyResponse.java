package pl.mbrzozowski.vulcanizer.dto;

import lombok.Getter;
import lombok.Setter;
import pl.mbrzozowski.vulcanizer.entity.Phone;

@Getter
@Setter
public class UserCompanyResponse {
    private Long id;
    private String nip;
    private String name;
    private AddressResponse address;
    private String phone;
}

package pl.mbrzozowski.vulcanizer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCompanyResponse {
    private Long id;
    private String nip;
    private String name;
}

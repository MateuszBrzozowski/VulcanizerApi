package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanyBranchResponse {
    private Long id;
    private String name;
    private String description;
    private String companyBranchStatus;
    private AddressResponse address;
    private String createdDate;
    private String phone;
    private CompanyResponse company;
    private int companyBranchSize;
    private UserResponse user;
}

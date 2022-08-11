package pl.mbrzozowski.vulcanizer.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCompanyBranchResponse {
    private String position;
    private String companyId;
    private String companyBranchId;
    private String companyBranchName;
    private String companyBranchStatus;
}

package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.EmployeeRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequest {
    private Long id;
    private Long userId;
    private Business business;
    private EmployeeRole role;

    public EmployeeRequest(Long userId, Business business, EmployeeRole role) {
        this.userId = userId;
        this.business = business;
        this.role = role;
    }
}

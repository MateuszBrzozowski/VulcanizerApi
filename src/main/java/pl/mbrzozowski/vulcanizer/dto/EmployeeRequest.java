package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.entity.Business;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequest {
    private Long id;
    private Long userId;
    private Business business;

    public EmployeeRequest(Long userId, Business business) {
        this.userId = userId;
        this.business = business;
    }
}

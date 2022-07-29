package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {
    private Long id;
    private User userId;
    private Business businessId;
}

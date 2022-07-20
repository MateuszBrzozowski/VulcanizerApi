package pl.mbrzozowski.vulcanizer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeRoleRequest {
    private Long id;
    private String name;

    public EmployeeRoleRequest(String name) {
        this.name = name;
    }
}

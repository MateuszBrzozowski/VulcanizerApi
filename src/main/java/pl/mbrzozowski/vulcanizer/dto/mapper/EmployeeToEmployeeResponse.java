package pl.mbrzozowski.vulcanizer.dto.mapper;

import pl.mbrzozowski.vulcanizer.dto.EmployeeResponse;
import pl.mbrzozowski.vulcanizer.entity.Employee;

public class EmployeeToEmployeeResponse {

    public static EmployeeResponse convert(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .userId(employee.getUserId())
                .businessId(employee.getBusinessId())
                .roleId(employee.getRoleId())
                .build();
    }
}

package pl.mbrzozowski.vulcanizer.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mbrzozowski.vulcanizer.dto.EmployeeRequest;
import pl.mbrzozowski.vulcanizer.entity.Employee;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.service.UserServiceImpl;

@Component
@RequiredArgsConstructor
public class EmployeeRequestToEmployee {
    private final UserServiceImpl userService;

    public Employee convert(EmployeeRequest employeeRequest) {
        User user = findUserById(employeeRequest);

        return Employee.builder()
                .id(employeeRequest.getId())
                .userId(user)
                .businessId(employeeRequest.getBusiness())
                .roleId(employeeRequest.getRole())
                .build();
    }

    private User findUserById(EmployeeRequest employeeRequest) {
        try {
            return userService.findById(employeeRequest.getUserId());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("User Id is not valid", e);
        }
    }
}

package pl.mbrzozowski.vulcanizer.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.mbrzozowski.vulcanizer.dto.EmployeeRequest;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.Employee;
import pl.mbrzozowski.vulcanizer.entity.EmployeeRole;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.exceptions.NoSuchElementException;
import pl.mbrzozowski.vulcanizer.exceptions.UserWasNotFoundException;
import pl.mbrzozowski.vulcanizer.service.BusinessService;
import pl.mbrzozowski.vulcanizer.service.EmployeeRoleService;
import pl.mbrzozowski.vulcanizer.service.UserService;

@Component
@RequiredArgsConstructor
public class EmployeeRequestToEmployee {
    private final BusinessService businessService;
    private final UserService userService;
    private final EmployeeRoleService employeeRoleService;

    public Employee convert(EmployeeRequest employeeRequest) {
        User user = findUserById(employeeRequest);
        Business business = findBusinessById(employeeRequest);
        EmployeeRole employeeRole = findRoleById(employeeRequest);

        return Employee.builder()
                .id(employeeRequest.getId())
                .userId(user)
                .businessId(business)
                .roleId(employeeRole)
                .build();
    }

    private EmployeeRole findRoleById(EmployeeRequest employeeRequest) {
        try {
            return employeeRoleService.findById(employeeRequest.getRoleId());
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Role is not valid", e);
        }
    }

    private Business findBusinessById(EmployeeRequest employeeRequest) {
        try {
            return businessService.findById(employeeRequest.getBusinessId());
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Business Id is not valid", e);
        }
    }

    private User findUserById(EmployeeRequest employeeRequest) {
        try {
            return userService.findById(employeeRequest.getUserId());
        } catch (UserWasNotFoundException e) {
            throw new IllegalArgumentException("User Id is not valid", e);
        }
    }
}

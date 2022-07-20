package pl.mbrzozowski.vulcanizer.validation;

import pl.mbrzozowski.vulcanizer.dto.EmployeeRequest;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;

public class ValidationEmployee {

    public static void valid(EmployeeRequest employeeRequest) {
        isUserIdNull(employeeRequest);
        isBusinessNull(employeeRequest);
        isRoleNull(employeeRequest);
    }

    private static void isUserIdNull(EmployeeRequest employeeRequest) {
        if (employeeRequest.getUserId() == null) {
            throw new IllegalArgumentException("User Id can not be null");
        }
    }

    private static void isBusinessNull(EmployeeRequest employeeRequest) {
        if (employeeRequest.getBusiness() == null) {
            throw new IllegalArgumentException("User Id can not be null");
        }
    }

    private static void isRoleNull(EmployeeRequest employeeRequest) {
        if (employeeRequest.getRole() == null) {
            throw new IllegalArgumentException("User Id can not be null");
        } else {
            if (employeeRequest.getRole().getId() == null) {
                throw new IllegalArgumentException("Role id can not be null");
            }
        }
    }
}

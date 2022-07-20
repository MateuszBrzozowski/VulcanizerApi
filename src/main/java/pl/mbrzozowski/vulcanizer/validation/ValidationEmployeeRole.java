package pl.mbrzozowski.vulcanizer.validation;

import pl.mbrzozowski.vulcanizer.dto.EmployeeRoleRequest;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;

public class ValidationEmployeeRole {

    public static void valid(EmployeeRoleRequest employeeRoleRequest) {
        checkIsRequestNull(employeeRoleRequest);
        checkIsNameNull(employeeRoleRequest);
        checkIsNameEmpty(employeeRoleRequest);
        checkIsNameToLong(employeeRoleRequest);
    }

    public static void validBeforeUpdate(EmployeeRoleRequest employeeRoleRequest) {
        valid(employeeRoleRequest);
        checkIsIdNull(employeeRoleRequest);
    }

    private static void checkIsIdNull(EmployeeRoleRequest employeeRoleRequest) {
        if (employeeRoleRequest.getId() == null) {
            throw new IllegalArgumentException("Id can not be null");
        }
    }

    private static void checkIsNameToLong(EmployeeRoleRequest employeeRoleRequest) {
        if (employeeRoleRequest.getName().length() > 100) {
            throw new IllegalArgumentException("Role name to long");
        }
    }

    private static void checkIsNameEmpty(EmployeeRoleRequest employeeRoleRequest) {
        if (employeeRoleRequest.getName().equalsIgnoreCase("")) {
            throw new IllegalArgumentException("Role name can not be empty");
        }
    }

    private static void checkIsRequestNull(EmployeeRoleRequest employeeRoleRequest) {
        if (employeeRoleRequest == null) {
            throw new IllegalArgumentException("Request can not be null");
        }
    }

    private static void checkIsNameNull(EmployeeRoleRequest employeeRoleRequest) {
        if (employeeRoleRequest.getName() == null) {
            throw new IllegalArgumentException("Role name can not be null");
        }
    }
}

package pl.mbrzozowski.vulcanizer.validation;

import pl.mbrzozowski.vulcanizer.dto.EmployeeRequest;

public class ValidationEmployee {

    public static void valid(EmployeeRequest employeeRequest) {
        isUserIdNull(employeeRequest);
        isBusinessNull(employeeRequest);
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

}

package pl.mbrzozowski.vulcanizer.validation;

import pl.mbrzozowski.vulcanizer.dto.BusinessServicesRequest;

import java.time.LocalTime;

public class ValidationService {

    public static void validBeforeCreated(BusinessServicesRequest service) {
        validBusinessId(service.getBusiness());
        validParam(service);
    }

    public static void validBeforeUpdate(BusinessServicesRequest serviceRequest) {
        validParam(serviceRequest);
    }

    private static void validParam(BusinessServicesRequest service) {
        validNameOne(service.getNameOne());
        validNameTwo(service.getNameTwo());
        validExecutionTime(service.getExecutionTime());
        validPrice(service.getPrice());
    }

    private static void validNameTwo(String nameTwo) {
        if (nameTwo != null) {
            if (nameTwo.length() < 3 || nameTwo.length() > 255) {
                throw new IllegalArgumentException("Name two is not valid");
            }
        }
    }

    private static void validBusinessId(Long business) {
        if (business == null) {
            throw new IllegalArgumentException("Business id can not be null");
        }
    }

    private static void validNameOne(String nameOne) {
        if (nameOne == null) {
            throw new IllegalArgumentException("Name one can not be null");
        } else {
            if (nameOne.length() < 3 || nameOne.length() > 255) {
                throw new IllegalArgumentException("Name one is not valid.");
            }
        }
    }

    private static void validExecutionTime(LocalTime executionTime) {
        if (executionTime == null) {
            throw new IllegalArgumentException("Execution time can not be null");
        }
        LocalTime timeZero = LocalTime.of(0, 0);
        if (executionTime.equals(timeZero)) {
            throw new IllegalArgumentException("Execution time can not be zero");
        }
    }

    private static void validPrice(double price) {
        if (price > 10_000 || price < 0) {
            throw new IllegalArgumentException("Price is not valid");
        }
    }
}

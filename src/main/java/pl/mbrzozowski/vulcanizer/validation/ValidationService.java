package pl.mbrzozowski.vulcanizer.validation;

import pl.mbrzozowski.vulcanizer.dto.BusinessServiceRequest;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;

import java.time.LocalTime;

public class ValidationService {

    public static void validBeforeCreated(BusinessServiceRequest service) {
        validBusinessId(service.getBusiness());
        validParam(service);
    }

    public static void validBeforeUpdate(BusinessServiceRequest serviceRequest) {
        validParam(serviceRequest);
    }

    private static void validParam(BusinessServiceRequest service) {
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
    }

    private static void validPrice(double price) {
        if (price > 1_000) {
            throw new IllegalArgumentException("Price is not valid");
        }
    }
}

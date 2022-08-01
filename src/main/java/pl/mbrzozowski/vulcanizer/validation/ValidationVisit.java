package pl.mbrzozowski.vulcanizer.validation;

import pl.mbrzozowski.vulcanizer.dto.VisitRequest;

import java.time.LocalDateTime;

public class ValidationVisit {

    public static void validBeforeCreated(VisitRequest visitRequest) {
        validUser(visitRequest);
        validBusinessService(visitRequest);
        validStartTime(visitRequest);
    }

    public static void validBeforeEditing(VisitRequest visitRequest) {
        validStatus(visitRequest);
        validStartTime(visitRequest);
    }

    private static void validStatus(VisitRequest visitRequest) {
        if (visitRequest.getStatus() == null) {
            throw new IllegalArgumentException("Status can not be null");
        }
    }

    private static void validUser(VisitRequest visitRequest) {
        if (visitRequest.getUser() == null) {
            throw new IllegalArgumentException("User Id can not be null");
        }
    }

    private static void validBusinessService(VisitRequest visitRequest) {
        if (visitRequest.getService() == null) {
            throw new IllegalArgumentException("Business Id can not be null");
        }
    }

    private static void validStartTime(VisitRequest visitRequest) {
        if (visitRequest.getStartTime() == null) {
            throw new IllegalArgumentException("Start time can not be null");
        }
        LocalDateTime now = LocalDateTime.now().plusSeconds(1);
        if (visitRequest.getStartTime().isBefore(now)) {
            throw new IllegalArgumentException("Start time can not be before than now");
        }
    }
}

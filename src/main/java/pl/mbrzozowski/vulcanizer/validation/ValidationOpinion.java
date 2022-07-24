package pl.mbrzozowski.vulcanizer.validation;

import pl.mbrzozowski.vulcanizer.dto.OpinionRequest;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;

public class ValidationOpinion {

    public static void validBeforeCreated(OpinionRequest opinionRequest) {
        validUser(opinionRequest.getUser());
        validBusiness(opinionRequest.getBusiness());
        validVisit(opinionRequest.getVisit());
        validStars(opinionRequest.getStars());
        validDescription(opinionRequest.getDescription());
    }

    public static void validBeforeEdit(OpinionRequest opinionRequest) {
        validStars(opinionRequest.getStars());
        validDescription(opinionRequest.getDescription());
    }

    private static void validDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description can not be null");
        }
        if (description.length() <= 0 || description.length() > 1000) {
            throw new IllegalArgumentException("Description is not valid");
        }
    }

    private static void validStars(int stars) {
        if (stars <= 0 || stars > 5) {
            throw new IllegalArgumentException("Stars is not valid");
        }
    }

    private static void validVisit(Long visit) {
        if (visit == null) {
            throw new IllegalArgumentException("Visit can not be null");
        }
    }

    private static void validBusiness(Long business) {
        if (business == null) {
            throw new IllegalArgumentException("Business id can not be null");
        }
    }

    private static void validUser(Long user) {
        if (user == null) {
            throw new IllegalArgumentException("User id can not be null");
        }
    }
}

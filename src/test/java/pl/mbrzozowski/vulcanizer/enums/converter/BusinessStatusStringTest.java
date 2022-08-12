package pl.mbrzozowski.vulcanizer.enums.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.enums.BusinessStatus;

class BusinessStatusStringTest {
    private final CompanyStatusString businessStatusString = new CompanyStatusString();

    @Test
    void convert_returnActive() {
        boolean isActive = true;
        boolean isLocked = false;
        boolean isClosed = false;
        String stringExcepted = BusinessStatus.ACTIVE.name();
        String stringResult = businessStatusString.convert(isActive, isLocked, isClosed);
        Assertions.assertEquals(stringExcepted, stringResult);
    }

    @Test
    void convert_returnNotActive() {
        boolean isActive = false;
        boolean isLocked = false;
        boolean isClosed = false;
        String stringExcepted = BusinessStatus.NOT_ACTIVE.name();
        String stringResult = businessStatusString.convert(isActive, isLocked, isClosed);
        Assertions.assertEquals(stringExcepted, stringResult);
    }

    @Test
    void convert_returnRejected() {
        boolean isActive = false;
        boolean isLocked = true;
        boolean isClosed = false;
        String stringExcepted = BusinessStatus.REJECTED.name();
        String stringResult = businessStatusString.convert(isActive, isLocked, isClosed);
        Assertions.assertEquals(stringExcepted, stringResult);
    }

    @Test
    void convert_returnLocked() {
        boolean isActive = true;
        boolean isLocked = true;
        boolean isClosed = false;
        String stringExcepted = BusinessStatus.LOCKED.name();
        String stringResult = businessStatusString.convert(isActive, isLocked, isClosed);
        Assertions.assertEquals(stringExcepted, stringResult);
    }

    @Test
    void convert_returnClosed() {
        boolean isActive = true;
        boolean isLocked = true;
        boolean isClosed = true;
        String stringExcepted = BusinessStatus.CLOSED.name();
        String stringResult = businessStatusString.convert(isActive, isLocked, isClosed);
        Assertions.assertEquals(stringExcepted, stringResult);
    }

}
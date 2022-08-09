package pl.mbrzozowski.vulcanizer.enums.converter;

import static pl.mbrzozowski.vulcanizer.enums.BusinessStatus.*;

public class BusinessStatusString {

    public String convert(boolean isActive, boolean isLocked, boolean isClosed) {
        if (!isClosed) {
            if (isActive) {
                if (isLocked) {
                    return LOCKED.name();
                } else {
                    return ACTIVE.name();
                }
            } else {
                if (isLocked) {
                    return REJECTED.name();
                }
                return NOT_ACTIVE.name();
            }
        } else {
            return CLOSED.name();
        }
    }
}

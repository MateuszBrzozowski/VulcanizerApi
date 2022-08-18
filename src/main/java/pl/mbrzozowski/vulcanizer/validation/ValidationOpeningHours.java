package pl.mbrzozowski.vulcanizer.validation;

import pl.mbrzozowski.vulcanizer.dto.OpeningHoursRequest;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.DayOfWeek.*;

public class ValidationOpeningHours {

    public static void validRequest(List<OpeningHoursRequest> openingHoursRequestList) {
        if (openingHoursRequestList.size() == 7) {
            checkIsExistAllDays(openingHoursRequestList);
            checkTimes(openingHoursRequestList);
        } else {
            throw new IllegalArgumentException("Need all 7 days of week.");
        }
    }

    public static void isAnyNull(LocalTime open, LocalTime close) {
        if (open == null && close != null) {
            throw new IllegalArgumentException("One time of day is not logic. Open time is null.");
        } else if (open != null && close == null) {
            throw new IllegalArgumentException("One time of day is not logic. Close time is null.");
        }
    }

    public static void isCloseTimeAfterOpenTime(LocalTime open, LocalTime close) {
        if (open != null && close != null) {
            if (open.isAfter(close)) {
                throw new IllegalArgumentException(String.format("Close time [%s] is before than open time [%s]. Not logic.", close, open));
            }
        }
    }

    private static void checkTimes(List<OpeningHoursRequest> openingHoursRequestList) {
        for (OpeningHoursRequest openingHoursRequest : openingHoursRequestList) {
            if (openingHoursRequest.getOpenTime() != null) {
                if (openingHoursRequest.getOpenTime().length() == 4) {
                    openingHoursRequest.setOpenTime("0" + openingHoursRequest.getOpenTime());
                }
                validTime(openingHoursRequest.getOpenTime());
            }
            if (openingHoursRequest.getCloseTime() != null) {
                if (openingHoursRequest.getCloseTime().length() == 4) {
                    openingHoursRequest.setCloseTime("0" + openingHoursRequest.getCloseTime());
                }
                validTime(openingHoursRequest.getCloseTime());
            }
        }
    }

    private static void validTime(String source) {
        try {
            LocalTime time = LocalTime.parse(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("%s is not valid. (TIME)", source));
        }
    }

    private static void checkIsExistAllDays(List<OpeningHoursRequest> openingHoursRequestList) {
        List<DayOfWeek> dayOfWeeks = new ArrayList<>(List.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY));
        for (DayOfWeek dayOfWeek : dayOfWeeks) {
            boolean isExist = false;
            for (OpeningHoursRequest openingHoursRequest : openingHoursRequestList) {
                if (dayOfWeek.name().equalsIgnoreCase(openingHoursRequest.getDay())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                throw new IllegalArgumentException(String.format("%s are missing. Need all 7 day of week.", dayOfWeek.name()));
            }
        }
    }
}

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

    private static void checkTimes(List<OpeningHoursRequest> openingHoursRequestList) {
        for (OpeningHoursRequest openingHoursRequest : openingHoursRequestList) {
            validTime(openingHoursRequest.getOpenTime());
            validTime(openingHoursRequest.getCloseTime());
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
                if (dayOfWeek.name().equalsIgnoreCase(openingHoursRequest.getDayOfWeek())) {
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

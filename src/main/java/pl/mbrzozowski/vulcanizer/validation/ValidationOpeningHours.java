package pl.mbrzozowski.vulcanizer.validation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import pl.mbrzozowski.vulcanizer.dto.CustomOpeningHoursRequest;
import pl.mbrzozowski.vulcanizer.dto.OpeningHoursRequest;
import pl.mbrzozowski.vulcanizer.entity.CustomOpeningHours;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static java.time.DayOfWeek.*;

@Slf4j
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
        String exceptionMessage = "One of times is blank.";
        if (open == null && close != null) {
            throw new IllegalArgumentException(exceptionMessage);
        } else if (open != null && close == null) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    public static void isCloseTimeAfterOpenTime(LocalTime open, LocalTime close) {
        if (open != null && close != null) {
            if (open.isAfter(close)) {
                throw new IllegalArgumentException("Close time is before than open time. Not logic.");
            }
        }
    }

    public static void validCustomRequest(CustomOpeningHoursRequest openingHoursRequest) {
        validDate(openingHoursRequest.getDateStart());
        validDate(openingHoursRequest.getDateEnd());
        if (StringUtils.isNotBlank(openingHoursRequest.getTimeStart())
                && StringUtils.isNotBlank(openingHoursRequest.getTimeEnd())) {
            openingHoursRequest.setTimeStart(prepareTime(openingHoursRequest.getTimeStart()));
            openingHoursRequest.setTimeEnd(prepareTime(openingHoursRequest.getTimeEnd()));
            validTime(openingHoursRequest.getTimeStart());
            validTime(openingHoursRequest.getTimeEnd());
        }
    }

    public static void validCustomOpeningHours(CustomOpeningHours newOpeningHours) {
        dataIsMaxTwoMonths(newOpeningHours);
        startDateIsBeforeOrEqualsDateEnd(newOpeningHours);
        isAnyNull(newOpeningHours.getTimeStart(), newOpeningHours.getTimeEnd());
        isCloseTimeAfterOpenTime(newOpeningHours.getTimeStart(), newOpeningHours.getTimeEnd());
    }

    private static void dataIsMaxTwoMonths(CustomOpeningHours newOpeningHours) {
        String exceptionMessage = "Max in next two months";
        if (newOpeningHours.getDateStart().isAfter(LocalDate.now().plusMonths(2))
                || newOpeningHours.getDateEnd().isAfter(LocalDate.now().plusMonths(2))) {
            throw new IllegalArgumentException(exceptionMessage);
        }
        if (newOpeningHours.getDateStart().isBefore(LocalDate.now().plusDays(1))
                || newOpeningHours.getDateEnd().isBefore(LocalDate.now().plusDays(1))) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    private static void startDateIsBeforeOrEqualsDateEnd(CustomOpeningHours newOpeningHours) {
        if (!newOpeningHours.getDateStart().equals(newOpeningHours.getDateEnd())) {
            if (newOpeningHours.getDateStart().isAfter(newOpeningHours.getDateEnd())) {
                throw new IllegalArgumentException("Date start is after date end");
            }
        }
    }


    public static void datesAreNotExist(CustomOpeningHours newOpeningHours, List<CustomOpeningHours> customOpeningHours) {
        for (CustomOpeningHours customOpeningHour : customOpeningHours) {
            String exceptionMessage = "Date not valid";
            if (newOpeningHours.getDateStart().equals(customOpeningHour.getDateStart()) ||
                    newOpeningHours.getDateStart().equals(customOpeningHour.getDateEnd()) ||
                    newOpeningHours.getDateEnd().equals(customOpeningHour.getDateStart()) ||
                    newOpeningHours.getDateEnd().equals(customOpeningHour.getDateEnd())) {
                throw new IllegalArgumentException(exceptionMessage);
            }
            if (newOpeningHours.getDateStart().isAfter(customOpeningHour.getDateStart())) {
                if (newOpeningHours.getDateStart().isBefore(customOpeningHour.getDateEnd()) ||
                        newOpeningHours.getDateStart().equals(customOpeningHour.getDateEnd())) {
                    throw new IllegalArgumentException(exceptionMessage);
                }
            }
            if (newOpeningHours.getDateStart().isBefore(customOpeningHour.getDateStart())) {
                if (newOpeningHours.getDateEnd().isAfter(customOpeningHour.getDateStart()) ||
                        newOpeningHours.getDateEnd().equals(customOpeningHour.getDateStart())) {
                    throw new IllegalArgumentException(exceptionMessage);
                }
            }
        }
    }

    private static void validDate(String date) {
        if (StringUtils.isNotBlank(date)) {
            try {
                LocalDate.parse(date);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Date is not valid. YYYY-MM-DD");
            }
        } else {
            throw new IllegalArgumentException("Date can not be blank");
        }
    }

    private static void checkTimes(List<OpeningHoursRequest> openingHoursRequestList) {
        for (OpeningHoursRequest openingHoursRequest : openingHoursRequestList) {
            if (openingHoursRequest.getOpenTime() != null) {
                openingHoursRequest.setOpenTime(prepareTime(openingHoursRequest.getOpenTime()));
                validTime(openingHoursRequest.getOpenTime());
            }
            if (openingHoursRequest.getCloseTime() != null) {
                openingHoursRequest.setCloseTime(prepareTime(openingHoursRequest.getCloseTime()));
                validTime(openingHoursRequest.getCloseTime());
            }
        }
    }

    private static String prepareTime(String source) {
        if (StringUtils.isNotBlank(source)) {
            if (source.length() == 4) {
                return "0" + source;
            }
        }
        return source;
    }

    private static void validTime(String source) {
        try {
            LocalTime time = LocalTime.parse(source);
        } catch (Exception e) {
            throw new IllegalArgumentException("String can not convert to time");
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

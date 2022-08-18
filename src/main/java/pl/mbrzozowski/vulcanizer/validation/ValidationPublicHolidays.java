package pl.mbrzozowski.vulcanizer.validation;

import org.apache.commons.lang3.StringUtils;
import pl.mbrzozowski.vulcanizer.dto.PublicHolidaysRequest;

import java.time.LocalDate;

public class ValidationPublicHolidays {

    public static void validRequest(PublicHolidaysRequest publicHolidaysRequest) {
        validDate(publicHolidaysRequest.getDate());
        validName(publicHolidaysRequest.getName());
    }

    private static void validName(String name) {
        if (!StringUtils.isBlank(name)) {
            if (name.length() > 255) {
                throw new IllegalArgumentException("Name to long");
            }
        } else {
            throw new IllegalArgumentException("Name can not be blank");
        }
    }

    private static void validDate(String date) {
        if (!StringUtils.isBlank(date)) {
            try {
                LocalDate.parse(date);
            } catch (Exception e) {
                throw new IllegalArgumentException("Date is not valid");
            }
        } else {
            throw new IllegalArgumentException("Date can not be blank");
        }
    }
}

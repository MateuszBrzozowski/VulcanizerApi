package pl.mbrzozowski.vulcanizer.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.CustomOpeningHoursRequest;
import pl.mbrzozowski.vulcanizer.dto.OpeningHoursRequest;
import pl.mbrzozowski.vulcanizer.entity.CustomOpeningHours;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

class ValidationOpeningHoursTest {

    @Test
    void validRequest_NotAllDaysOfWeek_ThrowIllegalExc() {
        List<OpeningHoursRequest> openingHoursRequestList = new ArrayList<>();
        OpeningHoursRequest monday = new OpeningHoursRequest("MONDAY", "10:00", "11:00");
        OpeningHoursRequest tuesday = new OpeningHoursRequest("TUESDAY", "10:00", "11:00");
        OpeningHoursRequest wednesday = new OpeningHoursRequest("WEDNESDAY", "10:00", "11:00");
        OpeningHoursRequest thursday = new OpeningHoursRequest("THURSDAY", "10:00", "11:00");
        OpeningHoursRequest friday = new OpeningHoursRequest("FRIDAY", "10:00", "11:00");
        OpeningHoursRequest saturday = new OpeningHoursRequest("SATURDAY", "10:00", "11:00");
        openingHoursRequestList.add(monday);
        openingHoursRequestList.add(tuesday);
        openingHoursRequestList.add(wednesday);
        openingHoursRequestList.add(thursday);
        openingHoursRequestList.add(friday);
        openingHoursRequestList.add(saturday);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.validRequest(openingHoursRequestList));
    }

    @Test
    void validRequest_8DaysOfWeek_ThrowIllegalExc() {
        List<OpeningHoursRequest> openingHoursRequestList = new ArrayList<>();
        OpeningHoursRequest monday = new OpeningHoursRequest("MONDAY", "10:00", "11:00");
        OpeningHoursRequest tuesday = new OpeningHoursRequest("TUESDAY", "10:00", "11:00");
        OpeningHoursRequest wednesday = new OpeningHoursRequest("WEDNESDAY", "10:00", "11:00");
        OpeningHoursRequest thursday = new OpeningHoursRequest("THURSDAY", "10:00", "11:00");
        OpeningHoursRequest friday = new OpeningHoursRequest("FRIDAY", "10:00", "11:00");
        OpeningHoursRequest saturday = new OpeningHoursRequest("SATURDAY", "10:00", "11:00");
        OpeningHoursRequest sunday = new OpeningHoursRequest("SUNDAY", "10:00", "11:00");
        openingHoursRequestList.add(monday);
        openingHoursRequestList.add(tuesday);
        openingHoursRequestList.add(wednesday);
        openingHoursRequestList.add(thursday);
        openingHoursRequestList.add(friday);
        openingHoursRequestList.add(saturday);
        openingHoursRequestList.add(sunday);
        openingHoursRequestList.add(sunday);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.validRequest(openingHoursRequestList));
    }

    @Test
    void validRequest_7DaysButFridayMissing_ThrowIllegalExc() {
        List<OpeningHoursRequest> openingHoursRequestList = new ArrayList<>();
        OpeningHoursRequest monday = new OpeningHoursRequest("MONDAY", "10:00", "11:00");
        OpeningHoursRequest tuesday = new OpeningHoursRequest("TUESDAY", "10:00", "11:00");
        OpeningHoursRequest wednesday = new OpeningHoursRequest("WEDNESDAY", "10:00", "11:00");
        OpeningHoursRequest thursday = new OpeningHoursRequest("THURSDAY", "10:00", "11:00");
        OpeningHoursRequest friday = new OpeningHoursRequest("FRIDA", "10:00", "11:00");
        OpeningHoursRequest saturday = new OpeningHoursRequest("SATURDAY", "10:00", "11:00");
        OpeningHoursRequest sunday = new OpeningHoursRequest("SUNDAY", "10:00", "11:00");
        openingHoursRequestList.add(monday);
        openingHoursRequestList.add(tuesday);
        openingHoursRequestList.add(wednesday);
        openingHoursRequestList.add(thursday);
        openingHoursRequestList.add(friday);
        openingHoursRequestList.add(saturday);
        openingHoursRequestList.add(sunday);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.validRequest(openingHoursRequestList));
    }

    @Test
    void validRequest_OneDayNotValidOpenTime_ThrowIllegalExc() {
        List<OpeningHoursRequest> openingHoursRequestList = new ArrayList<>();
        OpeningHoursRequest monday = new OpeningHoursRequest("MONDAY", "25:00", "11:00");
        OpeningHoursRequest tuesday = new OpeningHoursRequest("TUESDAY", "10:00", "11:00");
        OpeningHoursRequest wednesday = new OpeningHoursRequest("WEDNESDAY", "10:00", "11:00");
        OpeningHoursRequest thursday = new OpeningHoursRequest("THURSDAY", "10:00", "11:00");
        OpeningHoursRequest friday = new OpeningHoursRequest("FRIDAY", "10:00", "11:00");
        OpeningHoursRequest saturday = new OpeningHoursRequest("SATURDAY", "10:00", "11:00");
        OpeningHoursRequest sunday = new OpeningHoursRequest("SUNDAY", "10:00", "11:00");
        openingHoursRequestList.add(monday);
        openingHoursRequestList.add(tuesday);
        openingHoursRequestList.add(wednesday);
        openingHoursRequestList.add(thursday);
        openingHoursRequestList.add(friday);
        openingHoursRequestList.add(saturday);
        openingHoursRequestList.add(sunday);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.validRequest(openingHoursRequestList));
    }

    @Test
    void validRequest_OneDayNotValidCloseTime_ThrowIllegalExc() {
        List<OpeningHoursRequest> openingHoursRequestList = new ArrayList<>();
        OpeningHoursRequest monday = new OpeningHoursRequest("MONDAY", "10:00", "11:00");
        OpeningHoursRequest tuesday = new OpeningHoursRequest("TUESDAY", "10:00", "25:00");
        OpeningHoursRequest wednesday = new OpeningHoursRequest("WEDNESDAY", "10:00", "11:00");
        OpeningHoursRequest thursday = new OpeningHoursRequest("THURSDAY", "10:00", "11:00");
        OpeningHoursRequest friday = new OpeningHoursRequest("FRIDAY", "10:00", "11:00");
        OpeningHoursRequest saturday = new OpeningHoursRequest("SATURDAY", "10:00", "11:00");
        OpeningHoursRequest sunday = new OpeningHoursRequest("SUNDAY", "10:00", "11:00");
        openingHoursRequestList.add(monday);
        openingHoursRequestList.add(tuesday);
        openingHoursRequestList.add(wednesday);
        openingHoursRequestList.add(thursday);
        openingHoursRequestList.add(friday);
        openingHoursRequestList.add(saturday);
        openingHoursRequestList.add(sunday);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.validRequest(openingHoursRequestList));
    }

    @Test
    void validRequest_OneDayNotValidCloseTime2_ThrowIllegalExc() {
        List<OpeningHoursRequest> openingHoursRequestList = new ArrayList<>();
        OpeningHoursRequest monday = new OpeningHoursRequest("MONDAY", "10:00", "11:00");
        OpeningHoursRequest tuesday = new OpeningHoursRequest("TUESDAY", "10:00", "11:60");
        OpeningHoursRequest wednesday = new OpeningHoursRequest("WEDNESDAY", "10:00", "11:00");
        OpeningHoursRequest thursday = new OpeningHoursRequest("THURSDAY", "10:00", "11:00");
        OpeningHoursRequest friday = new OpeningHoursRequest("FRIDAY", "10:00", "11:00");
        OpeningHoursRequest saturday = new OpeningHoursRequest("SATURDAY", "10:00", "11:00");
        OpeningHoursRequest sunday = new OpeningHoursRequest("SUNDAY", "10:00", "11:00");
        openingHoursRequestList.add(monday);
        openingHoursRequestList.add(tuesday);
        openingHoursRequestList.add(wednesday);
        openingHoursRequestList.add(thursday);
        openingHoursRequestList.add(friday);
        openingHoursRequestList.add(saturday);
        openingHoursRequestList.add(sunday);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.validRequest(openingHoursRequestList));
    }

    @Test
    void validRequest_OneDayLowerCase_ThrowIllegalExc() {
        List<OpeningHoursRequest> openingHoursRequestList = new ArrayList<>();
        OpeningHoursRequest monday = new OpeningHoursRequest("Monday", "10:00", "11:00");
        OpeningHoursRequest tuesday = new OpeningHoursRequest("TUESDAY", "10:00", "11:00");
        OpeningHoursRequest wednesday = new OpeningHoursRequest("WEDNESDAY", "10:00", "11:00");
        OpeningHoursRequest thursday = new OpeningHoursRequest("THURSDAY", "10:00", "11:00");
        OpeningHoursRequest friday = new OpeningHoursRequest("FRIDAY", "10:00", "11:00");
        OpeningHoursRequest saturday = new OpeningHoursRequest("SATURDAY", "10:00", "11:00");
        OpeningHoursRequest sunday = new OpeningHoursRequest("SUNDAY", "10:00", "11:00");
        openingHoursRequestList.add(monday);
        openingHoursRequestList.add(tuesday);
        openingHoursRequestList.add(wednesday);
        openingHoursRequestList.add(thursday);
        openingHoursRequestList.add(friday);
        openingHoursRequestList.add(saturday);
        openingHoursRequestList.add(sunday);
        Assertions.assertDoesNotThrow(() -> ValidationOpeningHours.validRequest(openingHoursRequestList));
    }

    @Test
    void validCustomRequest_isValid_DoesNotThrows() {
        CustomOpeningHoursRequest customOpeningHoursRequest = CustomOpeningHoursRequest.builder()
                .dateStart("2022-01-01")
                .dateEnd("2023-01-02")
                .timeStart("10:00")
                .timeEnd("11:00")
                .build();
        Assertions.assertDoesNotThrow(() -> ValidationOpeningHours.validCustomRequest(customOpeningHoursRequest));
    }

    @Test
    void validCustomRequest_isValidTimeNull_DoesNotThrows() {
        CustomOpeningHoursRequest customOpeningHoursRequest = CustomOpeningHoursRequest.builder()
                .dateStart("2022-01-01")
                .dateEnd("2022-01-02")
                .timeStart(null)
                .timeEnd(null)
                .build();
        Assertions.assertDoesNotThrow(() -> ValidationOpeningHours.validCustomRequest(customOpeningHoursRequest));
    }

    @Test
    void validCustomRequest_DateEndNull_ThrowIllegalException() {
        CustomOpeningHoursRequest customOpeningHoursRequest = CustomOpeningHoursRequest.builder()
                .dateStart("2022-01-01")
                .dateEnd(null)
                .timeStart("10:00")
                .timeEnd("11:00")
                .build();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.validCustomRequest(customOpeningHoursRequest));
        Assertions.assertEquals("Date can not be blank", exception.getMessage());
    }

    @Test
    void validCustomRequest_DateEndBlank_ThrowIllegalException() {
        CustomOpeningHoursRequest customOpeningHoursRequest = CustomOpeningHoursRequest.builder()
                .dateStart("2022-01-01")
                .dateEnd(" ")
                .timeStart("10:00")
                .timeEnd("11:00")
                .build();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.validCustomRequest(customOpeningHoursRequest));
        Assertions.assertEquals("Date can not be blank", exception.getMessage());
    }

    @Test
    void validCustomRequest_DateStartNull_ThrowIllegalException() {
        CustomOpeningHoursRequest customOpeningHoursRequest = CustomOpeningHoursRequest.builder()
                .dateStart(null)
                .dateEnd("2022-01-01")
                .timeStart("10:00")
                .timeEnd("11:00")
                .build();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.validCustomRequest(customOpeningHoursRequest));
        Assertions.assertEquals("Date can not be blank", exception.getMessage());
    }

    @Test
    void validCustomRequest_DateStartBlank_ThrowIllegalException() {
        CustomOpeningHoursRequest customOpeningHoursRequest = CustomOpeningHoursRequest.builder()
                .dateStart(" ")
                .dateEnd("2022-01-01")
                .timeStart("10:00")
                .timeEnd("11:00")
                .build();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.validCustomRequest(customOpeningHoursRequest));
        Assertions.assertEquals("Date can not be blank", exception.getMessage());
    }

    @Test
    void validCustomRequest_TimeNine_DoesNotThrowExc() {
        CustomOpeningHoursRequest customOpeningHoursRequest = CustomOpeningHoursRequest.builder()
                .dateStart("2022-01-01")
                .dateEnd("2022-01-01")
                .timeStart("9:00")
                .timeEnd("11:00")
                .build();
        Assertions.assertDoesNotThrow(() -> ValidationOpeningHours.validCustomRequest(customOpeningHoursRequest));
    }

    @Test
    void validCustomOpeningHours_DaysGood_NoThrows() {
        CustomOpeningHours openingHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now().plusDays(1))
                .dateEnd(LocalDate.now().plusDays(1))
                .build();
        Assertions.assertDoesNotThrow(() -> ValidationOpeningHours.validCustomOpeningHours(openingHours));
    }

    @Test
    void validCustomOpeningHours_DaysAfterMaxRangeTwoMonths_ThrowsIllegalException() {
        CustomOpeningHours openingHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now().plusDays(1).plusMonths(2))
                .dateEnd(LocalDate.now().plusDays(1).plusMonths(2))
                .build();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.validCustomOpeningHours(openingHours));
        Assertions.assertEquals("Max in next two months", exception.getMessage());
    }

    @Test
    void validCustomOpeningHours_DaysToday_ThrowsIllegalException() {
        CustomOpeningHours openingHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(1))
                .build();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.validCustomOpeningHours(openingHours));
        Assertions.assertEquals("Max in next two months", exception.getMessage());
    }

    @Test
    void validCustomOpeningHours_DaysToday2_ThrowsIllegalException() {
        CustomOpeningHours openingHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now().plusDays(1))
                .dateEnd(LocalDate.now())
                .build();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.validCustomOpeningHours(openingHours));
        Assertions.assertEquals("Max in next two months", exception.getMessage());
    }

    @Test
    void validCustomOpeningHours_EndDayIsBeforeThanStartDay_ThrowsIllegalException() {
        CustomOpeningHours openingHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now().plusDays(2))
                .dateEnd(LocalDate.now().plusDays(1))
                .build();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.validCustomOpeningHours(openingHours));
        Assertions.assertEquals("Date start is after date end", exception.getMessage());
    }

    @Test
    void validCustomOpeningHours_OnlyOneTime_ThrowsIllegalException() {
        CustomOpeningHours openingHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now().plusDays(1))
                .dateEnd(LocalDate.now().plusDays(1))
                .timeEnd(LocalTime.now())
                .build();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.validCustomOpeningHours(openingHours));
        Assertions.assertEquals("One of times is blank.", exception.getMessage());
    }

    @Test
    void validCustomOpeningHours_OnlyOneTime2_ThrowsIllegalException() {
        CustomOpeningHours openingHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now().plusDays(1))
                .dateEnd(LocalDate.now().plusDays(1))
                .timeStart(LocalTime.now())
                .build();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.validCustomOpeningHours(openingHours));
        Assertions.assertEquals("One of times is blank.", exception.getMessage());
    }

    @Test
    void validCustomOpeningHours_TimeStartIsAfterThanTimeEnd_ThrowsIllegalException() {
        CustomOpeningHours openingHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now().plusDays(1))
                .dateEnd(LocalDate.now().plusDays(1))
                .timeStart(LocalTime.of(10, 1))
                .timeEnd(LocalTime.of(10, 0))
                .build();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.validCustomOpeningHours(openingHours));
        Assertions.assertEquals("Close time is before than open time. Not logic.", exception.getMessage());
    }

    @Test
    void validCustomOpeningHours_AllGood_DoesNotThrows() {
        CustomOpeningHours openingHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now().plusDays(1))
                .dateEnd(LocalDate.now().plusDays(1))
                .timeStart(LocalTime.of(10, 0))
                .timeEnd(LocalTime.of(10, 1))
                .build();
        Assertions.assertDoesNotThrow(() -> ValidationOpeningHours.validCustomOpeningHours(openingHours));
    }

    @Test
    void datesAreNotExist_DateIsExist_ThrowIllegalException() {
        List<CustomOpeningHours> customOpeningHoursList = new ArrayList<>();
        CustomOpeningHours customOpeningHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now().plusDays(2))
                .dateEnd(LocalDate.now().plusDays(3))
                .build();
        customOpeningHoursList.add(customOpeningHours);
        CustomOpeningHours newOpeningHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now().plusDays(3))
                .dateEnd(LocalDate.now().plusDays(4))
                .build();
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.datesAreNotExist(newOpeningHours, customOpeningHoursList));
    }

    @Test
    void datesAreNotExist_DateIsExistNewBefore_ThrowIllegalException() {
        List<CustomOpeningHours> customOpeningHoursList = new ArrayList<>();
        CustomOpeningHours customOpeningHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now().plusDays(3))
                .dateEnd(LocalDate.now().plusDays(4))
                .build();
        customOpeningHoursList.add(customOpeningHours);
        CustomOpeningHours newOpeningHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now().plusDays(2))
                .dateEnd(LocalDate.now().plusDays(3))
                .build();
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.datesAreNotExist(newOpeningHours, customOpeningHoursList));
    }

    @Test
    void datesAreNotExist_DateIsExistNewAbove_ThrowIllegalException() {
        List<CustomOpeningHours> customOpeningHoursList = new ArrayList<>();
        CustomOpeningHours customOpeningHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now().plusDays(3))
                .dateEnd(LocalDate.now().plusDays(4))
                .build();
        customOpeningHoursList.add(customOpeningHours);
        CustomOpeningHours newOpeningHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now().plusDays(2))
                .dateEnd(LocalDate.now().plusDays(5))
                .build();
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ValidationOpeningHours.datesAreNotExist(newOpeningHours, customOpeningHoursList));
    }

    @Test
    void datesAreNotExist_NewIsBefore_DoesNotThrowsException() {
        List<CustomOpeningHours> customOpeningHoursList = new ArrayList<>();
        CustomOpeningHours customOpeningHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now().plusDays(3))
                .dateEnd(LocalDate.now().plusDays(4))
                .build();
        customOpeningHoursList.add(customOpeningHours);
        CustomOpeningHours newOpeningHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now().plusDays(2))
                .dateEnd(LocalDate.now().plusDays(2))
                .build();
        Assertions.assertDoesNotThrow(() -> ValidationOpeningHours.datesAreNotExist(newOpeningHours, customOpeningHoursList));
    }

    @Test
    void datesAreNotExist_NewIsAfter_DoesNotThrowsException() {
        List<CustomOpeningHours> customOpeningHoursList = new ArrayList<>();
        CustomOpeningHours customOpeningHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now().plusDays(3))
                .dateEnd(LocalDate.now().plusDays(4))
                .build();
        customOpeningHoursList.add(customOpeningHours);
        CustomOpeningHours newOpeningHours = CustomOpeningHours.builder()
                .dateStart(LocalDate.now().plusDays(5))
                .dateEnd(LocalDate.now().plusDays(5))
                .build();
        Assertions.assertDoesNotThrow(() -> ValidationOpeningHours.datesAreNotExist(newOpeningHours, customOpeningHoursList));
    }
}
package pl.mbrzozowski.vulcanizer.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mbrzozowski.vulcanizer.dto.OpeningHoursRequest;

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
//        OpeningHoursRequest sunday = new OpeningHoursRequest("SUNDAY", "10:00", "11:00");
        openingHoursRequestList.add(monday);
        openingHoursRequestList.add(tuesday);
        openingHoursRequestList.add(wednesday);
        openingHoursRequestList.add(thursday);
        openingHoursRequestList.add(friday);
        openingHoursRequestList.add(saturday);
//        openingHoursRequestList.add(sunday);
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
}
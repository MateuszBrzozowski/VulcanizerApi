package pl.mbrzozowski.vulcanizer.dto.mapper;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.dto.CustomOpeningHoursRequest;
import pl.mbrzozowski.vulcanizer.entity.CustomOpeningHours;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class CustomOpeningHoursReqToEntity implements Converter<CustomOpeningHoursRequest, CustomOpeningHours> {

    @Override
    public @NotNull CustomOpeningHours convert(CustomOpeningHoursRequest source) {
        CustomOpeningHours customOpeningHours = new CustomOpeningHours();
        try {
            customOpeningHours.setDateStart(LocalDate.parse(source.getDateStart()));
        } catch (DateTimeParseException e) {
            customOpeningHours.setDateStart(null);
        }
        try {
            customOpeningHours.setDateEnd(LocalDate.parse(source.getDateEnd()));
        } catch (DateTimeParseException e) {
            customOpeningHours.setDateEnd(null);
        }
        try {
            customOpeningHours.setTimeStart(LocalTime.parse(source.getTimeStart()));
        } catch (DateTimeParseException e) {
            customOpeningHours.setTimeStart(null);
        }
        try {
            customOpeningHours.setTimeEnd(LocalTime.parse(source.getTimeEnd()));
        } catch (DateTimeParseException e) {
            customOpeningHours.setTimeEnd(null);
        }
        return customOpeningHours;
    }
}

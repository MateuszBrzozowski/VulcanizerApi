package pl.mbrzozowski.vulcanizer.enums.converter;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;

import java.time.DayOfWeek;

public class StringDayToDayOfWeek implements Converter<String, DayOfWeek> {

    @Override
    public DayOfWeek convert(@NotNull String source) {
        source = source.toUpperCase();
        return switch (source) {
            case "MONDAY" -> DayOfWeek.MONDAY;
            case "TUESDAY" -> DayOfWeek.TUESDAY;
            case "WEDNESDAY" -> DayOfWeek.WEDNESDAY;
            case "THURSDAY" -> DayOfWeek.THURSDAY;
            case "FRIDAY" -> DayOfWeek.FRIDAY;
            case "SATURDAY" -> DayOfWeek.SATURDAY;
            case "SUNDAY" -> DayOfWeek.SUNDAY;
            default -> null;
        };
    }
}

package pl.mbrzozowski.vulcanizer.util;

import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class Converter {

    public static @NotNull Long stringToLong(String source) {
        try {
            return Long.parseLong(source);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Can not convert to Long");
        }
    }

    public static int stringToInt(String source) {
        try {
            return Integer.parseInt(source);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Can not convert to Int");
        }
    }

    public static double stringToDouble(String source) {
        try {
            return Double.parseDouble(source);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Can not convert to double");
        }
    }

    public static LocalTime stringToLocalTime(@NotNull String source) {
        if (source.length() == 4) {
            source = "0" + source;
        }
        try {
            return LocalTime.parse(source);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Can not convert to Time");
        }
    }
}

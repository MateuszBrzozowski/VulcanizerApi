package pl.mbrzozowski.vulcanizer.enums.converter;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.enums.WheelType;

public class StringToWheelType implements Converter<String, WheelType> {

    @Override
    public WheelType convert(String source) {
        switch (source) {
            case "ALUMINIUM" -> {
                return WheelType.ALUMINIUM;
            }
            case "STEEL" -> {
                return WheelType.STEEL;
            }
            default -> {
                return null;
            }
        }
    }
}

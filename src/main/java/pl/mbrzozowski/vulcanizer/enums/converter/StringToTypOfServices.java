package pl.mbrzozowski.vulcanizer.enums.converter;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.enums.TypeOfServices;

public class StringToTypOfServices implements Converter<String, TypeOfServices> {

    @Override
    public TypeOfServices convert(String source) {
        switch (source) {
            case "TIRES_SWAP" -> {
                return TypeOfServices.TIRES_SWAP;
            }
            case "WHEEL_SWAP" -> {
                return TypeOfServices.WHEEL_SWAP;
            }
            case "WHEEL_BALANCE" -> {
                return TypeOfServices.WHEEL_BALANCE;
            }
            case "STRAIGHTENING_RIMS" -> {
                return TypeOfServices.STRAIGHTENING_RIMS;
            }
            case "CUSTOM" -> {
                return TypeOfServices.CUSTOM;
            }
            default -> {
                return null;
            }
        }
    }
}

package pl.mbrzozowski.vulcanizer.dto.mapper;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.dto.ServicesRequest;
import pl.mbrzozowski.vulcanizer.entity.Services;
import pl.mbrzozowski.vulcanizer.enums.TypeOfServices;
import pl.mbrzozowski.vulcanizer.enums.WheelType;
import pl.mbrzozowski.vulcanizer.enums.converter.StringToTypOfServices;
import pl.mbrzozowski.vulcanizer.enums.converter.StringToWheelType;

import static pl.mbrzozowski.vulcanizer.util.Converter.*;

public class ServicesRequestToServices implements Converter<ServicesRequest, Services> {

    @Override
    public @NotNull Services convert(ServicesRequest source) {
        Services services = new Services();
        setId(source, services);
        setPrice(source, services);
        setName(source, services);
        setTime(source, services);
        setTypeOfServices(source, services);
        setTypeOfWheel(source, services);
        seSizeFrom(source, services);
        setSizeTo(source, services);
        return services;
    }

    private void seSizeFrom(ServicesRequest source, Services services) {
        if (StringUtils.isNotBlank(source.getSizeFrom())) {
            services.setSizeFrom(stringToInt(source.getSizeFrom()));
        } else {
            services.setSizeFrom(null);
        }
    }

    private void setSizeTo(ServicesRequest source, Services services) {
        if (StringUtils.isNotBlank(source.getSizeTo())) {
            services.setSizeTo(stringToInt(source.getSizeTo()));
        } else {
            services.setSizeTo(null);
        }
    }

    private void setTypeOfWheel(@NotNull ServicesRequest source, Services services) {
        if (StringUtils.isNotBlank(source.getWheelType())) {
            WheelType wheelType = new StringToWheelType().convert(source.getWheelType());
            if (wheelType != null) {
                services.setWheelType(wheelType);
            } else {
                throw new IllegalArgumentException("Type of wheel is not valid");
            }
        } else {
            services.setWheelType(null);
        }
    }

    private void setTypeOfServices(@NotNull ServicesRequest source, Services services) {
        if (StringUtils.isNotBlank(source.getTypOfServices())) {
            TypeOfServices typOfServices = new StringToTypOfServices().convert(source.getTypOfServices());
            if (typOfServices != null) {
                services.setTypeOfServices(typOfServices);
            } else {
                throw new IllegalArgumentException("Type of services is not valid");
            }
        } else {
            throw new IllegalArgumentException("Type of services can not be blank");
        }
    }

    private void setTime(@NotNull ServicesRequest source, Services services) {
        if (StringUtils.isNotBlank(source.getTime())) {
            services.setTime(stringToLocalTime(source.getTime()));
        } else {
            throw new IllegalArgumentException("Time is required");
        }
    }

    private void setName(@NotNull ServicesRequest source, Services services) {
        if (StringUtils.isNotBlank(source.getName())) {
            services.setName(source.getName());
        } else {
            services.setName(null);
        }
    }

    private void setPrice(@NotNull ServicesRequest source, Services services) {
        if (StringUtils.isNotBlank(source.getPrice())) {
            services.setPrice(stringToDouble(source.getPrice()));
        } else {
            throw new IllegalArgumentException("Price is required");
        }
    }

    private void setId(@NotNull ServicesRequest source, Services services) {
        if (StringUtils.isNotBlank(source.getId())) {
            services.setId(stringToLong(source.getId()));
        } else {
            services.setId(null);
        }
    }
}

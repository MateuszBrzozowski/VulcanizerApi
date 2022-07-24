package pl.mbrzozowski.vulcanizer.dto.mapper;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.dto.BusinessServicesResponse;
import pl.mbrzozowski.vulcanizer.entity.BusinessServices;

public class ServicesBusinessToServicesBusinessResponse implements Converter<BusinessServices, BusinessServicesResponse> {

    @Override
    public BusinessServicesResponse convert(BusinessServices servicesBusiness) {
        return
                new BusinessServicesResponse(servicesBusiness.getId(),
                        servicesBusiness.getNameOne(),
                        servicesBusiness.getNameTwo(),
                        servicesBusiness.getExecutionTime(),
                        servicesBusiness.getPrice());
    }
}

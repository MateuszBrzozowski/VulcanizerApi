package pl.mbrzozowski.vulcanizer.dto.mapper;

import lombok.RequiredArgsConstructor;
import pl.mbrzozowski.vulcanizer.dto.BusinessRequest;
import pl.mbrzozowski.vulcanizer.entity.Business;

import java.util.function.Function;

@RequiredArgsConstructor
public class BusinessRequestToBusiness implements Function<BusinessRequest, Business> {

    @Override
    public Business apply(BusinessRequest businessRequest) {
        return Business.builder()
                .id(businessRequest.getId())
                .name(businessRequest.getName())
                .nip(businessRequest.getNip())
                .description(businessRequest.getDescription())
                .phones(businessRequest.getPhones())
                .build();
    }
}
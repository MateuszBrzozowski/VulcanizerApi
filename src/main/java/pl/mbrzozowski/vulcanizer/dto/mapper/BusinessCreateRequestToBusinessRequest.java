package pl.mbrzozowski.vulcanizer.dto.mapper;

import pl.mbrzozowski.vulcanizer.dto.BusinessCreateRequest;
import pl.mbrzozowski.vulcanizer.dto.BusinessRequest;

import java.util.function.Function;

public class BusinessCreateRequestToBusinessRequest implements Function<BusinessCreateRequest, BusinessRequest> {

    @Override
    public BusinessRequest apply(BusinessCreateRequest businessCreateRequest) {
        return BusinessRequest.builder()
                .userId(businessCreateRequest.getUserId())
                .name(businessCreateRequest.getName())
                .nip(businessCreateRequest.getNip())
                .description(businessCreateRequest.getDescription())
                .address(businessCreateRequest.getAddress())
                .photo(businessCreateRequest.getPhoto())
                .build();
    }
}

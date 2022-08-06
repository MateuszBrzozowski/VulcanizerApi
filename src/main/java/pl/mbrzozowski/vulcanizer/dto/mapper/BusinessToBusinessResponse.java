package pl.mbrzozowski.vulcanizer.dto.mapper;

import pl.mbrzozowski.vulcanizer.dto.BusinessResponse;
import pl.mbrzozowski.vulcanizer.entity.Business;

public class BusinessToBusinessResponse {

    public BusinessResponse apply(Business business) {
        BusinessResponse response = BusinessResponse.builder()
                .id(business.getId())
                .name(business.getName())
                .nip(Long.parseLong(business.getNip()))
                .createdDate(business.getCreatedDate())
                .description(business.getDescription())
                .status(business.getStatus())
                .address(new AddressToAddressResponse().convert(business.getAddress()))
                .build();

        if (business.getPhoto() != null) {
            response.setPhoto(business.getPhoto().getUrl());
        }
        return response;
    }
}

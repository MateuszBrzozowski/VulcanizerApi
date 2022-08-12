package pl.mbrzozowski.vulcanizer.dto.mapper;

import org.springframework.core.convert.converter.Converter;
import pl.mbrzozowski.vulcanizer.dto.CompanyResponse;
import pl.mbrzozowski.vulcanizer.entity.Company;

import static pl.mbrzozowski.vulcanizer.enums.converter.Converter.getCompanyBranchStatus;

public class CompanyToCompanyResponse implements Converter<Company, CompanyResponse> {

    @Override
    public CompanyResponse convert(Company source) {
        String status = getCompanyBranchStatus(source.isActive(), source.isLocked(), source.isClosed());
        return CompanyResponse.builder()
                .id(source.getId())
                .nip(source.getNip())
                .name(source.getName())
                .createdDate(source.getCreatedDate())
                .status(status)
                .address(new AddressToAddressResponse().convert(source.getAddress()))
                .build();
    }
}

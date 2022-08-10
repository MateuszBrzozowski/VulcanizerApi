package pl.mbrzozowski.vulcanizer.validation;

import org.apache.commons.lang3.StringUtils;
import pl.mbrzozowski.vulcanizer.dto.CompanyRequest;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.entity.Company;
import pl.mbrzozowski.vulcanizer.entity.CompanyBranch;
import pl.mbrzozowski.vulcanizer.entity.Phone;

public class ValidationCompanyBranch {

    public static void validBeforeCreate(CompanyRequest companyRequest) {
        validDisplayName(companyRequest.getNameCB());
        validDescription(companyRequest.getDescriptionCB());
    }

    public static void valid(CompanyBranch companyBranch) {
        validDisplayName(companyBranch.getName());
        validDescription(companyBranch.getDescription());
        validAddress(companyBranch.getAddress());
        validPhone(companyBranch.getPhone());
        validCompany(companyBranch.getCompany());
    }

    private static void validCompany(Company company) {
        if (company == null) {
            throw new IllegalArgumentException("Company can not be null");
        }
    }

    private static void validAddress(Address address) {
        if (address != null) {
            ValidationAddress.validForBusiness(address);
        } else {
            throw new IllegalArgumentException("Address for Company branch can not be null");
        }
    }

    private static void validPhone(Phone phone) {
        if (phone != null) {
            ValidationPhone.validNumberForBusiness(phone.getNumber());
        } else {
            throw new IllegalArgumentException("Phone for company branch can not be null");
        }
    }

    static void validDisplayName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Display name can not be blank.");
        } else {
            if (name.length() > 255) {
                throw new IllegalArgumentException("Display name to long");
            }
        }
    }


    static void validDescription(String description) {
        if (StringUtils.isNotBlank(description)) {
            if (description.length() > 1000) {
                throw new IllegalArgumentException("Description to Long. Max 1000 length.");
            }
        }
    }
}

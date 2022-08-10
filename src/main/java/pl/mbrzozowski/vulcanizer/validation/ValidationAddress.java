package pl.mbrzozowski.vulcanizer.validation;

import org.apache.commons.lang3.StringUtils;
import pl.mbrzozowski.vulcanizer.dto.AddressRequest;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.enums.StatesPL;

public class ValidationAddress {

    public static void validForBusiness(AddressRequest addressRequest) {
        if (addressRequest != null) {
            allFieldsRequired(addressRequest);
            validFields(addressRequest);
        } else {
            throw new IllegalArgumentException("Address is required");
        }
    }

    public static void validForBusiness(Address address) {
        if (address != null) {
            AddressRequest addressRequest =
                    new AddressRequest(address.getId(),
                            address.getAddressLine(),
                            address.getCity(), address.getCode(),
                            address.getState().getName(),
                            address.getCountry());
            allFieldsRequired(addressRequest);
            validFields(addressRequest);
        } else {
            throw new IllegalArgumentException("Address is required");
        }
    }

    public static void validForUser(AddressRequest addressRequest) {
        if (addressRequest != null) {
            validFields(addressRequest);
        }
    }

    public static void allFieldsBlank(AddressRequest addressRequest) {
        if (StringUtils.isBlank(addressRequest.getAddressLine()) &&
                StringUtils.isBlank(addressRequest.getCity()) &&
                StringUtils.isBlank(addressRequest.getCode()) &&
                StringUtils.isBlank(addressRequest.getState()) &&
                StringUtils.isBlank(addressRequest.getCountry())) {
            throw new IllegalArgumentException("All fields can not be blank");
        }
    }

    private static void allFieldsRequired(AddressRequest addressRequest) {
        if (StringUtils.isBlank(addressRequest.getAddressLine()) ||
                StringUtils.isBlank(addressRequest.getCity()) ||
                StringUtils.isBlank(addressRequest.getCode()) ||
                StringUtils.isBlank(addressRequest.getState()) ||
                StringUtils.isBlank(addressRequest.getCountry())) {
            throw new IllegalArgumentException("All field of address is required");
        }
    }

    private static void validFields(AddressRequest addressRequest) {
        validLine(addressRequest.getAddressLine());
        validCity(addressRequest.getCity());
        validCode(addressRequest.getCode());
        validState(addressRequest.getState());
        validCountry(addressRequest.getCountry());
    }

    private static void validLine(String addressLine) {
        if (StringUtils.isNotBlank(addressLine)) {
            if (addressLine.length() > 255) {
                throw new IllegalArgumentException("Address line to long");
            }
        }
    }

    private static void validCity(String city) {
        if (StringUtils.isNotBlank(city)) {
            if (city.length() > 50) {
                throw new IllegalArgumentException("Address city to long");
            }
        }
    }

    private static void validCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            String messageNotValid = "Postal code is not valid. Format XX-XXX";
            if (code.length() > 0 && code.length() != 6) {
                throw new IllegalArgumentException(messageNotValid);
            }
            if (code.length() == 6) {
                char c = code.charAt(2);
                if (c != '-') {
                    throw new IllegalArgumentException(messageNotValid);
                }
                String codeOnlyNumber = code.replace("-", "");
                if (!codeOnlyNumber.matches("\\d+")) {
                    throw new IllegalArgumentException(messageNotValid);
                }
            }
        }
    }

    private static void validState(String state) {
        if (StringUtils.isNotBlank(state)) {
            StatesPL[] statesPl = StatesPL.values();
            boolean isExist = false;
            for (StatesPL statesPL : statesPl) {
                if (statesPL.getName().equalsIgnoreCase(state)) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                throw new IllegalArgumentException(String.format("State %s is not exist", state.toUpperCase()));
            }
        }
    }

    private static void validCountry(String country) {
        if (StringUtils.isNotBlank(country)) {
            if (country.length() > 60) {
                throw new IllegalArgumentException("Country to long");
            }
        }
    }
}

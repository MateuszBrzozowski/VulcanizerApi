package pl.mbrzozowski.vulcanizer.util;

import pl.mbrzozowski.vulcanizer.dto.AddressRequest;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.entity.Phone;

public class Comparator {

    public static boolean compare(Address address, AddressRequest addressRequest) {
        if (address != null && addressRequest != null) {
            if (address.getAddressLine() != null && addressRequest.getAddressLine() != null) {
                String addressLine = removeBlank(address.getAddressLine());
                String addressRequestLine = removeBlank(addressRequest.getAddressLine());
                if (!addressLine.equalsIgnoreCase(addressRequestLine)) {
                    return false;
                }
            }
            if (address.getCity() != null && addressRequest.getCity() != null) {
                String city = removeBlank(address.getCity());
                String cityRequest = removeBlank(addressRequest.getCity());
                if (!city.equalsIgnoreCase(cityRequest)) {
                    return false;
                }
            }
            if (address.getCode() != null && addressRequest.getCode() != null) {
                String code = removeBlank(address.getCode());
                String codeRequest = removeBlank(addressRequest.getCode());
                if (!code.equalsIgnoreCase(codeRequest)) {
                    return false;
                }
            }
            if (address.getState() != null && addressRequest.getState() != null) {
                String state = removeBlank(address.getState().getName());
                String stateRequest = removeBlank(addressRequest.getState());
                if (!state.equalsIgnoreCase(stateRequest)) {
                    return false;
                }
            }
            if (address.getCountry() != null && addressRequest.getCountry() != null) {
                String country = removeBlank(address.getCountry());
                String countryRequest = removeBlank(addressRequest.getCountry());
                return country.equalsIgnoreCase(countryRequest);
            }
        }
        return true;
    }

    private static String removeBlank(String source) {
        return source.replace(" ", "");
    }

    public static boolean compare(Phone phone, String phoneNumber) {
        if (phone != null && phoneNumber != null) {
            return phoneNumber.equalsIgnoreCase(phone.getNumber());
        }
        return true;
    }
}

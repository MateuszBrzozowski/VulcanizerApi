package pl.mbrzozowski.vulcanizer.util;

import pl.mbrzozowski.vulcanizer.dto.AddressRequest;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.entity.Phone;

public class Comparator {

    public static boolean compare(Address address, AddressRequest addressRequest) {
        if (address != null && addressRequest != null) {
            if (address.getAddressLine() != null && addressRequest.getAddressLine() != null) {
                if (!address.getAddressLine().equalsIgnoreCase(addressRequest.getAddressLine())) {
                    return false;
                }
            }
            if (address.getCity() != null && addressRequest.getCity() != null) {
                if (!address.getCity().equalsIgnoreCase(addressRequest.getCity())) {
                    return false;
                }
            }
            if (address.getCode() != null && addressRequest.getCode() != null) {
                if (!address.getCode().equalsIgnoreCase(addressRequest.getCode())) {
                    return false;
                }
            }
            if (address.getState() != null && addressRequest.getState() != null) {
                if (!address.getState().getName().equalsIgnoreCase(addressRequest.getState())) {
                    return false;
                }
            }
            if (address.getCountry() != null && addressRequest.getCountry() != null) {
                return address.getCountry().equalsIgnoreCase(addressRequest.getCountry());
            }
        }
        return true;
    }

    public static boolean compare(Phone phone, String phoneNumber) {
        if (phone != null && phoneNumber != null) {
            return phoneNumber.equalsIgnoreCase(phone.getNumber());
        }
        return true;
    }
}

package pl.mbrzozowski.vulcanizer.validation;

import org.apache.commons.lang3.StringUtils;

public class ValidationPhone {

    public static void validNumber(String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber)) {
            throw new IllegalArgumentException("Phone can not be blank");
        }
        if (!phoneNumber.matches("\\d+")) {
            throw new IllegalArgumentException("Wrong input phone number");
        }
        if (phoneNumber.length() > 11) {
            throw new IllegalArgumentException("Phone number to long");
        }
    }

    public static void validNumberForBusiness(String number) {
        if (StringUtils.isBlank(number)) {
            throw new IllegalArgumentException("Business must have phone number.");
        } else {
            ValidationPhone.validNumber(number);
        }
    }
}

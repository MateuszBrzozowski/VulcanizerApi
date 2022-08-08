package pl.mbrzozowski.vulcanizer.validation;

import org.apache.commons.lang3.StringUtils;
import pl.mbrzozowski.vulcanizer.dto.BusinessRequest;

public class ValidationBusiness {
    public static void validBeforeCreate(BusinessRequest businessRequest) {
        validName(businessRequest.getName());
        validDisplayName(businessRequest.getDisplayName());
        validNip(businessRequest.getNip());
        validDescription(businessRequest.getDescription());
        validPhones(businessRequest);
        ValidationAddress.validForBusiness(businessRequest.getAddress());
    }

    private static void validPhones(BusinessRequest businessRequest) {
        if (StringUtils.isBlank(businessRequest.getPhoneFirst())) {
            throw new IllegalArgumentException("Business must have at least one number.");
        } else {
            String number = preparePhoneNumber(businessRequest.getPhoneFirst());
            ValidationPhone.validNumber(number);
        }
        if (StringUtils.isNotBlank(businessRequest.getPhoneSecond())) {
            String number = preparePhoneNumber(businessRequest.getPhoneSecond());
            ValidationPhone.validNumber(number);
        }
    }

    private static String preparePhoneNumber(String number) {
        number = number.replace(" ", "");
        number = number.replace("-", "");
        number = number.replace("+", "");
        number = number.replace("(", "");
        number = number.replace(")", "");
        return number;
    }

    private static void validName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Name can not be blank.");
        }
    }

    private static void validDisplayName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Display name can not be blank.");
        }
    }

    private static void validNip(String nip) {
        if (StringUtils.isBlank(nip)) {
            throw new IllegalArgumentException("Nip can not be blank");
        } else {
            nip = nip.replace("-", "");
            nip = nip.replace(" ", "");
            if (nip.length() != 10) {
                throw new IllegalArgumentException("NIP is to long or to short.");
            }
            try {
                Long.parseLong(nip);
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("NIP is not valid. Illegal characters.");
            }
            nipControl(nip);
        }
    }

    private static void nipControl(String nip) {
        int[] weights = {6, 5, 7, 2, 3, 4, 5, 6, 7};
        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            int charInt = Integer.parseInt(String.valueOf(nip.charAt(i)));
            sum += (weights[i] * charInt);
        }
        int charLast = Integer.parseInt(String.valueOf(nip.charAt(9)));
        if (sum % 11 != charLast) {
            throw new IllegalArgumentException("NIP is not valid. Checked digit.");
        }
    }

    private static void validDescription(String description) {
        if (StringUtils.isNotBlank(description)) {
            if (description.length() > 1000) {
                throw new IllegalArgumentException("Description to Long. Max 1000 length.");
            }
            if (StringUtils.isBlank(description)) {
                description = null;
            }
        }
    }

}

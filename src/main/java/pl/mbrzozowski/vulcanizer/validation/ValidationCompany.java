package pl.mbrzozowski.vulcanizer.validation;

import org.apache.commons.lang3.StringUtils;
import pl.mbrzozowski.vulcanizer.dto.CompanyRequest;

public class ValidationCompany {
    public static void validBeforeCreate(CompanyRequest companyRequest) {
        validName(companyRequest.getName());
        companyRequest.setNip(validNip(companyRequest.getNip()));
    }

    public static void validBeforeCreateCompanyBranch(CompanyRequest companyRequest) {
        validNip(companyRequest.getNip());
    }

    static void validName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("Name can not be blank.");
        } else {
            if (name.length() > 255) {
                throw new IllegalArgumentException("Name to long");
            }
        }
    }

    static String validNip(String nip) {
        if (StringUtils.isBlank(nip)) {
            throw new IllegalArgumentException("Nip can not be blank.");
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
            return nip;
        }
    }

    static void nipControl(String nip) {
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


}

package pl.mbrzozowski.vulcanizer.validation;

import pl.mbrzozowski.vulcanizer.dto.BusinessRequest;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;

public class ValidationBusiness {

    public static void validCreateRequest(BusinessRequest businessRequest,
                                          StateRepository stateRepository,
                                          Address address) {
        validIdUser(businessRequest.getUserId());
        validParam(businessRequest, stateRepository, address);
    }

    public static void validBeforeEdit(BusinessRequest businessRequest,
                                       StateRepository stateRepository,
                                       Address address) {
        validID(businessRequest);
        validParam(businessRequest, stateRepository, address);
    }

    private static void validParam(BusinessRequest businessRequest,
                                   StateRepository stateRepository,
                                   Address address) {

        validName(businessRequest.getName());
        validNip(businessRequest.getNip());
        validDescription(businessRequest.getDescription());
        validAddress(address, stateRepository);
    }

    private static void validID(BusinessRequest businessRequest) {
        if (businessRequest.getId() == null) {
            throw new IllegalArgumentException("Business id can not be null");
        }
    }

    private static void validIdUser(Long idUser) {
        if (idUser == null) {
            throw new IllegalArgumentException("userId can not be null");
        }
    }

    private static void validName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name can not be null");
        } else {
            if (name.equalsIgnoreCase("")) {
                throw new java.lang.IllegalArgumentException("Name can not be empty");
            }
        }
    }

    private static void validNip(String nip) {
        if (nip == null) {
            throw new IllegalArgumentException("nip can not be null");
        } else {
            nip = nip.replace("-", "");
            nip = nip.replace(" ", "");
            if (nip.length() > 10) {
                throw new IllegalArgumentException("NIP is to long.");
            }
            if (nip.length() < 10) {
                throw new IllegalArgumentException("NIP is to short.");
            }
            try {
                Long.parseLong(nip);
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("NIP is not valid. Illegal characters.");
            }
        }
    }

    private static void validDescription(String description) {
        if (description != null) {
            if (description.length() > 1000) {
                throw new IllegalArgumentException("Description to Long. Max 1000 length.");
            }
            if (description.equalsIgnoreCase("")) {
                description = null;
            }
        }
    }

    private static void validAddress(Address address, StateRepository stateRepository) {
        if (address == null) {
            throw new IllegalArgumentException("Address can not be null");
        } else {
            ValidationAddress.allParamRequired(address, stateRepository);
        }
    }
}

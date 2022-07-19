package pl.mbrzozowski.vulcanizer.validation;

import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.exceptions.NullParameterException;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;

public class ValidationAddress {

    public static void valid(Address address) {
        ifArgsEmptySetNull(address);
        if (isAllParametersNull(address)) {
            throw new NullParameterException("Address: All parameters can not be null");
        } else {
            if (isAllParametersEmpty(address)) {
                throw new IllegalArgumentException("Address: All parameters can not be empty");
            }
            isValidLineOne(address);
            isValidLineTwo(address);
            isValidCity(address);
            isValidCode(address);
        }
    }

    public static void allParamRequired(Address address, StateRepository stateRepository) {
        ifArgsEmptySetNull(address);
        if (isNullLineOne(address.getAddressLineOne())
                || isNullCity(address.getCity())
                || isNullPostalCode(address.getCode())
                || isNullState(address.getState())
                || isNulllCountry(address.getCountry())) {
            throw new IllegalArgumentException("Line one, city, postal code, state, country is required");
        } else {
            lineOneToLong(address);
            lineTwoToLong(address);
            cityToLong(address);
            postalCodeToLong(address);
            countryToLong(address);
            ValidationState.isNameExist(address.getState(), stateRepository);
        }

    }

    private static void ifArgsEmptySetNull(Address address) {
        if (address.getAddressLineOne() != null) {
            if (address.getAddressLineOne().equalsIgnoreCase("")) {
                address.setAddressLineOne(null);
            }
        }
        if (address.getAddressLineTwo() != null) {
            if (address.getAddressLineTwo().equalsIgnoreCase("")) {
                address.setAddressLineTwo(null);
            }
        }
        if (address.getCity() != null) {
            if (address.getCity().equalsIgnoreCase("")) {
                address.setCity(null);
            }
        }
        if (address.getCode() != null) {
            if (address.getCode().equalsIgnoreCase("")) {
                address.setCode(null);
            }
        }
        if (address.getState() != null) {
            if (address.getState().getName().equalsIgnoreCase("")) {
                address.setState(null);
            }
        }
    }

    private static void isValidCode(Address address) {
        if (address.getCode() != null) {
            if (address.getAddressLineOne() == null
                    && address.getAddressLineTwo() == null
                    && address.getCity() == null
                    && address.getState() == null) {
                if (address.getCode().equalsIgnoreCase("")) {
                    throw new IllegalArgumentException("Address: All parameters can not be null or empty");
                }
            }
            postalCodeToLong(address);
        }
    }

    private static void postalCodeToLong(Address address) {
        if (address.getCode().length() != 6) {
            throw new IllegalArgumentException("Address: Postal code not valid");
        }
    }

    private static void isValidCity(Address address) {
        if (address.getCity() != null) {
            if (address.getAddressLineOne() == null
                    && address.getAddressLineTwo() == null
                    && address.getCode() == null
                    && address.getState() == null) {
                if (address.getCity().equalsIgnoreCase("")) {
                    throw new IllegalArgumentException("Address: All parameters can not be null or empty");
                }
            }
            cityToLong(address);
        }
    }

    private static void cityToLong(Address address) {
        if (address.getCity().length() > 40) {
            throw new IllegalArgumentException("Address: City name to Long");
        }
    }

    private static void isValidLineTwo(Address address) {
        if (address.getAddressLineTwo() != null) {
            if (address.getAddressLineOne() == null
                    && address.getCity() == null
                    && address.getCode() == null
                    && address.getState() == null) {
                if (address.getAddressLineTwo().equalsIgnoreCase("")) {
                    throw new IllegalArgumentException("Address: All parameters can not be null or empty");
                }
            }
            lineTwoToLong(address);
        }
    }

    private static void lineTwoToLong(Address address) {
        if (address.getAddressLineTwo() != null) {
            if (address.getAddressLineTwo().length() > 100) {
                throw new IllegalArgumentException("Address: Line two to Long");
            }
        }
    }

    private static void isValidLineOne(Address address) {
        if (address.getAddressLineOne() != null) {
            if (address.getAddressLineTwo() == null
                    && address.getCity() == null
                    && address.getCode() == null
                    && address.getState() == null) {
                if (address.getAddressLineOne().equalsIgnoreCase("")) {
                    throw new IllegalArgumentException("Address: All parameters can not be null or empty");
                }
            }
            lineOneToLong(address);
        }
    }

    private static void lineOneToLong(Address address) {
        if (address.getAddressLineOne().length() > 100) {
            throw new IllegalArgumentException("Address: Line one to Long");
        }
    }

    private static void isValidCountry(Address address) {
        if (address.getAddressLineOne() != null) {
            if (address.getAddressLineTwo() == null
                    && address.getCity() == null
                    && address.getCode() == null
                    && address.getState() == null) {
                if (address.getAddressLineOne().equalsIgnoreCase("")) {
                    throw new IllegalArgumentException("Address: All parameters can not be null or empty");
                }
            }
            cityToLong(address);
        }
    }

    private static void countryToLong(Address address) {
        if (address.getCountry().length() > 50) {
            throw new IllegalArgumentException("Address: Line one to Long");
        }
    }

    private static boolean isAllParametersNull(Address address) {
        return isNullLineOne(address.getAddressLineOne())
                && isNullLineTwo(address.getAddressLineTwo())
                && isNullCity(address.getCity())
                && isNullPostalCode(address.getCode())
                && isNullState(address.getState());
    }

    private static boolean isAllParametersEmpty(Address address) {
        return isEmptyLineOne(address.getAddressLineOne())
                && isEmptyLineTwo(address.getAddressLineTwo())
                && isEmptyCity(address.getCity())
                && isEmptyPostalCode(address.getCode());
    }

    private static boolean isEmptyLineOne(String addressLineOne) {
        if (addressLineOne != null) {
            return addressLineOne.equalsIgnoreCase("");
        }
        return false;
    }

    private static boolean isEmptyLineTwo(String addressLineTwo) {
        if (addressLineTwo != null) {
            return addressLineTwo.equalsIgnoreCase("");
        }
        return false;
    }

    private static boolean isEmptyCity(String city) {
        if (city != null) {
            return city.equalsIgnoreCase("");
        }
        return false;
    }

    private static boolean isEmptyPostalCode(String code) {
        if (code != null) {
            return code.equalsIgnoreCase("");
        }
        return false;
    }

    private static boolean isNullLineOne(String addressLineOne) {
        return addressLineOne == null;
    }

    private static boolean isNullLineTwo(String addressLineTwo) {
        return addressLineTwo == null;
    }

    private static boolean isNullCity(String city) {
        return city == null;
    }

    private static boolean isNullPostalCode(String code) {
        return code == null;
    }

    private static boolean isNullState(State state) {
        return state == null;
    }

    private static boolean isNulllCountry(String country) {
        return country == null;
    }
}

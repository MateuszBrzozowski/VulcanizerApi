package pl.mbrzozowski.vulcanizer.validation;

import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.exceptions.NullParameterException;

import java.util.function.Consumer;

public class ValidationAddress implements Consumer<Address> {
    @Override
    public void accept(Address address) {
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

    private void ifArgsEmptySetNull(Address address) {
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

    private void isValidCode(Address address) {
        if (address.getCode() != null) {
            if (address.getAddressLineOne() == null
                    && address.getAddressLineTwo() == null
                    && address.getCity() == null
                    && address.getState() == null) {
                if (address.getCode().equalsIgnoreCase("")) {
                    throw new IllegalArgumentException("Address: All parameters can not be null or empty");
                }
            }
            if (address.getCode().length() > 6) {
                throw new IllegalArgumentException("Address: Postal code to long");
            }
        }
    }

    private void isValidCity(Address address) {
        if (address.getCity() != null) {
            if (address.getAddressLineOne() == null
                    && address.getAddressLineTwo() == null
                    && address.getCode() == null
                    && address.getState() == null) {
                if (address.getCity().equalsIgnoreCase("")) {
                    throw new IllegalArgumentException("Address: All parameters can not be null or empty");
                }
            }
            if (address.getCity().length() > 40) {
                throw new IllegalArgumentException("Address: City name to Long");
            }
        }
    }

    private void isValidLineTwo(Address address) {
        if (address.getAddressLineTwo() != null) {
            if (address.getAddressLineOne() == null
                    && address.getCity() == null
                    && address.getCode() == null
                    && address.getState() == null) {
                if (address.getAddressLineTwo().equalsIgnoreCase("")) {
                    throw new IllegalArgumentException("Address: All parameters can not be null or empty");
                }
            }
            if (address.getAddressLineTwo().length() > 100) {
                throw new IllegalArgumentException("Address: Line two to Long");
            }
        }
    }

    private void isValidLineOne(Address address) {
        if (address.getAddressLineOne() != null) {
            if (address.getAddressLineTwo() == null
                    && address.getCity() == null
                    && address.getCode() == null
                    && address.getState() == null) {
                if (address.getAddressLineOne().equalsIgnoreCase("")) {
                    throw new IllegalArgumentException("Address: All parameters can not be null or empty");
                }
            }
            if (address.getAddressLineOne().length() > 100) {
                throw new IllegalArgumentException("Address: Line one to Long");
            }
        }
    }

    private boolean isAllParametersNull(Address address) {
        return isNullLineOne(address.getAddressLineOne())
                && isNullLineTwo(address.getAddressLineTwo())
                && isNullCity(address.getCity())
                && isNullPostalCode(address.getCode())
                && isNullState(address.getState());
    }

    private boolean isAllParametersEmpty(Address address) {
        return isEmptyLineOne(address.getAddressLineOne())
                && isEmptyLineTwo(address.getAddressLineTwo())
                && isEmptyCity(address.getCity())
                && isEmptyPostalCode(address.getCode());
    }

    private boolean isEmptyLineOne(String addressLineOne) {
        if (addressLineOne != null) {
            return addressLineOne.equalsIgnoreCase("");
        }
        return false;
    }

    private boolean isEmptyLineTwo(String addressLineTwo) {
        if (addressLineTwo != null) {
            return addressLineTwo.equalsIgnoreCase("");
        }
        return false;
    }

    private boolean isEmptyCity(String city) {
        if (city != null) {
            return city.equalsIgnoreCase("");
        }
        return false;
    }

    private boolean isEmptyPostalCode(String code) {
        if (code != null) {
            return code.equalsIgnoreCase("");
        }
        return false;
    }

    private boolean isNullLineOne(String addressLineOne) {
        return addressLineOne == null;
    }

    private boolean isNullLineTwo(String addressLineTwo) {
        return addressLineTwo == null;
    }

    private boolean isNullCity(String city) {
        return city == null;
    }

    private boolean isNullPostalCode(String code) {
        return code == null;
    }

    private boolean isNullState(State state) {
        return state == null;
    }
}

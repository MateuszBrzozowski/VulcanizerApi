package pl.mbrzozowski.vulcanizer.validation;

import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.entity.State;
import pl.mbrzozowski.vulcanizer.exceptions.NullPointerException;

import java.util.function.Consumer;

public class ValidationAddress implements Consumer<Address> {
    @Override
    public void accept(Address address) {
        if (isAllParametersNull(address)) {
            throw new NullPointerException("All parameters can not be null");
        }
    }

    private boolean isAllParametersNull(Address address) {
        if (isNullLineOne(address.getAddressLineOne())) {
            return false;
        }
        if (isNullLineTwo(address.getAddressLineTwo())) {
            return false;
        }
        if (isNullCity(address.getCity())) {
            return false;
        }
        if (isNullPostalCode(address.getCode())) {
            return false;
        }
        return !isNullState(address.getState());
    }

    private boolean isNullState(State state) {
        return state == null;
    }

    private boolean isNullPostalCode(String code) {
        return code == null;
    }

    private boolean isNullCity(String city) {
        return city == null;
    }

    private boolean isNullLineTwo(String addressLineTwo) {
        return addressLineTwo == null;
    }

    private boolean isNullLineOne(String addressLineOne) {
        return addressLineOne == null;
    }
}

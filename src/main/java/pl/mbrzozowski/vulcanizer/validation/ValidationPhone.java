package pl.mbrzozowski.vulcanizer.validation;

import pl.mbrzozowski.vulcanizer.entity.Phone;

import java.util.function.Consumer;

public class ValidationPhone implements Consumer<Phone> {

    @Override
    public void accept(Phone phone) {
        if (phone.getNumber() == null) {
            throw new IllegalArgumentException("Phone can not be null");
        } else {
            if (phone.getNumber().equalsIgnoreCase("")) {
                throw new IllegalArgumentException("Phone can not be empty");
            }
            phone.setNumber(phone.getNumber().replace(" ", ""));
            phone.setNumber(phone.getNumber().replace("-", ""));
            phone.setNumber(phone.getNumber().replace("+", "00"));
            if (!phone.getNumber().matches("\\d+")) {
                throw new IllegalArgumentException("Wrong input phone number");
            }
            if (phone.getNumber().length() > 13) {
                throw new IllegalArgumentException("Phone number to long");
            }
        }
    }
}

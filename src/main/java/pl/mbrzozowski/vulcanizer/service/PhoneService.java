package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.entity.Phone;
import pl.mbrzozowski.vulcanizer.repository.PhoneRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationPhone;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneService {
    private final PhoneRepository phoneRepository;

    public Phone save(Phone phone) {
        String number = prepareNumber(phone.getNumber());
        ValidationPhone.validNumber(number);
        Phone newPhone = new Phone(number);
        phoneRepository.save(newPhone);
        return newPhone;
    }

    public Phone update(Phone phone, String newPhoneNumber) {
        newPhoneNumber = prepareNumber(newPhoneNumber);
        ValidationPhone.validNumber(newPhoneNumber);
        phone.setNumber(newPhoneNumber);
        phoneRepository.save(phone);
        return phone;
    }

    public List<Phone> findAll() {
        return phoneRepository.findAll();
    }

    public Phone findById(Long id) {
        return phoneRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Not found phone by id [%s]", id));
                });
    }

    public Phone findByNumber(String number) {
        return phoneRepository
                .findByNumber(number)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Not found phone number [%s]", number));
                });

    }

    public void deleteById(Long id) {
        phoneRepository.deleteById(id);
    }

    private String prepareNumber(String number) {
        if (StringUtils.isNotBlank(number)) {
            number = number.replace(" ", "");
            number = number.replace("-", "");
            number = number.replace("+", "");
            number = number.replace("(", "");
            number = number.replace(")", "");
        }
        return number;
    }
}

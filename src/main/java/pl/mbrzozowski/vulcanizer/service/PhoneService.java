package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.entity.Phone;
import pl.mbrzozowski.vulcanizer.repository.PhoneRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationPhone;

import java.util.List;
import java.util.Optional;

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

    public Phone saveForBusiness(String number) {
        number = prepareNumber(number);
        ValidationPhone.validNumberForBusiness(number);
        Phone phone = new Phone(number);
        return phoneRepository.save(phone);
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

    public Optional<Phone> findByNumber(String number) {
        return phoneRepository.findByNumber(number);
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

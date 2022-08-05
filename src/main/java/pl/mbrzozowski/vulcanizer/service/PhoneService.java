package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.entity.Phone;
import pl.mbrzozowski.vulcanizer.repository.PhoneRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationPhone;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneService {
    private final PhoneRepository phoneRepository;
    private final ValidationPhone validationPhone = new ValidationPhone();

    public Phone save(Phone phone) {
        Phone newPhone = new Phone(phone.getNumber());
        validationPhone.accept(newPhone);
        phoneRepository.save(newPhone);
        return newPhone;
    }

    public Phone update(Phone phone) {
        validationPhone.accept(phone);
        Phone phoneUpdate = findById(phone.getId());
        phoneUpdate.setNumber(phone.getNumber());
        phoneRepository.save(phoneUpdate);
        return phoneUpdate;
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
}

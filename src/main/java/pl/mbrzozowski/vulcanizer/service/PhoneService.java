package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.entity.Phone;
import pl.mbrzozowski.vulcanizer.exceptions.NoSuchElementException;
import pl.mbrzozowski.vulcanizer.repository.PhoneRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationPhone;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneService implements ServiceLayer<Phone, Phone, Phone> {
    private final PhoneRepository phoneRepository;
    private ValidationPhone validationPhone = new ValidationPhone();


    @Override
    public Phone save(Phone phone) {
        Phone newPhone = new Phone(phone.getNumber());
        validationPhone.accept(newPhone);
        phoneRepository.save(newPhone);
        return newPhone;
    }

    @Override
    public Phone update(Phone phone) {
        validationPhone.accept(phone);
        Phone phoneUpdate = findById(phone.getId());
        phoneUpdate.setNumber(phone.getNumber());
        phoneRepository.save(phoneUpdate);
        return phoneUpdate;
    }

    @Override
    public List<Phone> findAll() {
        return phoneRepository.findAll();
    }

    @Override
    public Phone findById(Long id) {
        return phoneRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new NoSuchElementException(String.format("Not found phone by id [%s]", id));
                });
    }

    public Phone findByNumber(String number) {
        return phoneRepository
                .findByNumber(number)
                .orElseThrow(() -> {
                    throw new NoSuchElementException(String.format("Not found phone number [%s]", number));
                });

    }

    @Override
    public void deleteById(Long id) {
        phoneRepository.deleteById(id);
    }
}

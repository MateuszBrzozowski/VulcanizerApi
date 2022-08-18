package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.PublicHolidaysRequest;
import pl.mbrzozowski.vulcanizer.entity.PublicHolidays;
import pl.mbrzozowski.vulcanizer.repository.PublicHolidaysRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationPublicHolidays;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublicHolidaysService {
    private final PublicHolidaysRepository publicHolidaysRepository;

    public void save(PublicHolidaysRequest publicHolidaysRequest) {
        ValidationPublicHolidays.validRequest(publicHolidaysRequest);
        LocalDate date = LocalDate.parse(publicHolidaysRequest.getDate());
        PublicHolidays publicHolidays = new
                PublicHolidays(
                null,
                date,
                publicHolidaysRequest.getName(),
                publicHolidaysRequest.isEveryYear());
        publicHolidaysRepository.save(publicHolidays);
    }

    public void update(PublicHolidaysRequest publicHolidaysRequest) {

    }

    public List<PublicHolidays> findAll() {
        return null;
    }

    public List<PublicHolidays> findNextTwoMonths() {
        return null;
    }

    public List<PublicHolidays> findAllThisYear() {
        return null;
    }

    public List<PublicHolidays> findAllNextYear() {
        return null;
    }

    public void deleteById(Long id) {
        publicHolidaysRepository.deleteById(id);
    }

    public Optional<PublicHolidays> findById(Long id) {
        return publicHolidaysRepository.findById(id);
    }


}

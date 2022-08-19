package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.PublicHolidaysRequest;
import pl.mbrzozowski.vulcanizer.entity.PublicHolidays;
import pl.mbrzozowski.vulcanizer.repository.PublicHolidaysRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationPublicHolidays;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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

    public List<PublicHolidays> findNextTwoMonths() {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusMonths(2);
        List<PublicHolidays> holidaysList = publicHolidaysRepository.findAllByDateBetweenDates(start, end);
        holidaysList.sort(Comparator.comparing(PublicHolidays::getDate));
        return holidaysList;
    }

    public List<PublicHolidays> findAllThisYear() {
        LocalDate start = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate end = LocalDate.of(LocalDate.now().getYear(), 12, 31);
        List<PublicHolidays> holidaysList = publicHolidaysRepository.findAllByDateBetweenDatesAndAllEveryYear(start, end);
        holidaysList.sort(Comparator.comparing(PublicHolidays::getDate));
        return holidaysList;
    }

    public List<PublicHolidays> findAllNextYear() {
        LocalDate start = LocalDate.of(LocalDate.now().getYear() + 1, 1, 1);
        LocalDate end = LocalDate.of(LocalDate.now().getYear() + 1, 12, 31);
        List<PublicHolidays> holidaysList = publicHolidaysRepository.findAllByDateBetweenDatesAndAllEveryYear(start, end);
        for (PublicHolidays publicHolidays : holidaysList) {
            LocalDate date = publicHolidays.getDate();
            LocalDate newDate = LocalDate.of(LocalDate.now().getYear()+1, date.getMonth(), date.getDayOfMonth());
            publicHolidays.setDate(newDate);
        }
        holidaysList.sort(Comparator.comparing(PublicHolidays::getDate));
        return holidaysList;
    }

    public void deleteById(String id) {
        try {
            Long longId = Long.parseLong(id);
            deleteById(longId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Id can not convert to Long");
        }
    }

    public void deleteById(Long id) {
        publicHolidaysRepository.deleteById(id);
    }

    public Optional<PublicHolidays> findById(Long id) {
        return publicHolidaysRepository.findById(id);
    }


}

package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.PublicHolidaysRequest;
import pl.mbrzozowski.vulcanizer.entity.PublicHolidays;
import pl.mbrzozowski.vulcanizer.repository.PublicHolidaysRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublicHolidaysService {
    private final PublicHolidaysRepository publicHolidaysRepository;

    public List<PublicHolidays> findAll() {
        return null;
    }

    public void save(PublicHolidaysRequest publicHolidaysRequest) {

    }

    public void update(PublicHolidaysRequest publicHolidaysRequest) {

    }

    public void deleteById(Long id) {
        publicHolidaysRepository.deleteById(id);
    }

    public Optional<PublicHolidays> findById(Long id) {
        return publicHolidaysRepository.findById(id);
    }


}

package pl.mbrzozowski.vulcanizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbrzozowski.vulcanizer.entity.PublicHolidays;

@Repository
public interface PublicHolidaysRepository extends JpaRepository<PublicHolidays, Long> {
}

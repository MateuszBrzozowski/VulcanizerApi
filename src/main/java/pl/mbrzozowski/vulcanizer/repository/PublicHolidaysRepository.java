package pl.mbrzozowski.vulcanizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.mbrzozowski.vulcanizer.entity.PublicHolidays;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PublicHolidaysRepository extends JpaRepository<PublicHolidays, Long> {

    @Query("SELECT ph FROM public_holidays ph WHERE (ph.date between :start AND :end) OR ph.everyYear=true")
    List<PublicHolidays> findAllByDateBetweenDatesAndAllEveryYear(@Param("start") LocalDate start, @Param("end") LocalDate end);
}

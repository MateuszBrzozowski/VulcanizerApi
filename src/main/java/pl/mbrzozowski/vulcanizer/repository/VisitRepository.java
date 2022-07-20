package pl.mbrzozowski.vulcanizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbrzozowski.vulcanizer.entity.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
}

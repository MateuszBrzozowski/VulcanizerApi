package pl.mbrzozowski.vulcanizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbrzozowski.vulcanizer.entity.BusinessServices;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.entity.Visit;

import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    Optional<Visit> findByService(BusinessServices servicesBusiness);

    Optional<Visit> findByUser(User user);
}

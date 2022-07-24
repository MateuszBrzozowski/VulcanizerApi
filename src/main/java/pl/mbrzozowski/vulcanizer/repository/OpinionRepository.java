package pl.mbrzozowski.vulcanizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.Opinion;

import java.util.List;
import java.util.Optional;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Long> {
    Optional<Opinion> findByBusiness(Business business);
}

package pl.mbrzozowski.vulcanizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbrzozowski.vulcanizer.entity.BusinessServices;

@Repository
public interface BusinessServicesRepository extends JpaRepository<BusinessServices, Long> {
}

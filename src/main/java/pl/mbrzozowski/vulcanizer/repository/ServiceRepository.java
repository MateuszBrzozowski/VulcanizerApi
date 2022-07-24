package pl.mbrzozowski.vulcanizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbrzozowski.vulcanizer.entity.BusinessService;

@Repository
public interface ServiceRepository extends JpaRepository<BusinessService, Long> {
}

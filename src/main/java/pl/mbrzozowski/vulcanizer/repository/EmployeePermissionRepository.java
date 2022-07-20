package pl.mbrzozowski.vulcanizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbrzozowski.vulcanizer.entity.EmployeePermission;

@Repository
public interface EmployeePermissionRepository extends JpaRepository<EmployeePermission, Long> {
}

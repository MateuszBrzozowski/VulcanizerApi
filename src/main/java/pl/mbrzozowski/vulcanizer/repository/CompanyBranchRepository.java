package pl.mbrzozowski.vulcanizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mbrzozowski.vulcanizer.entity.CompanyBranch;

@Repository
public interface CompanyBranchRepository extends JpaRepository<CompanyBranch, Long> {
}

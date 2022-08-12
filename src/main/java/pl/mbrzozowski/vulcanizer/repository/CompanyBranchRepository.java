package pl.mbrzozowski.vulcanizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.mbrzozowski.vulcanizer.entity.CompanyBranch;

import java.util.List;

@Repository
public interface CompanyBranchRepository extends JpaRepository<CompanyBranch, Long> {

    @Query("SELECT cb FROM company_branch cb WHERE cb.isActive = 0 AND cb.isLocked = 0 AND cb.isClosed = 0")
    List<CompanyBranch> findAllByWaiting();
}

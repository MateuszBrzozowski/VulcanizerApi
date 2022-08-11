package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.entity.CompanyBranch;
import pl.mbrzozowski.vulcanizer.repository.CompanyBranchRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationCompanyBranch;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CompanyBranchService {
    private final CompanyBranchRepository companyBranchRepository;

    /**
     * Saving new companyBranch in DB. Name, address, phone, Company can not be null/blank.
     *
     * @param companyBranch already instance {@link CompanyBranch} to save in DB.
     * @throws IllegalArgumentException when name is blank or not valid, address is null or not valid
     *                                  Phone is null or not valid
     */
    public CompanyBranch save(CompanyBranch companyBranch) {
        ValidationCompanyBranch.valid(companyBranch);
        companyBranch.setCreatedDate(LocalDateTime.now());
        companyBranch.setActive(false);
        companyBranch.setLocked(false);
        companyBranch.setClosed(false);
        return companyBranchRepository.save(companyBranch);
    }
}

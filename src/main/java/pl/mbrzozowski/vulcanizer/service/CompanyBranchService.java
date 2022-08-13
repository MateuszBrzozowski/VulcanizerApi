package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.CompanyBranchResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.AddressToAddressResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.CompanyToCompanyResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserToUserResponse;
import pl.mbrzozowski.vulcanizer.entity.Company;
import pl.mbrzozowski.vulcanizer.entity.CompanyBranch;
import pl.mbrzozowski.vulcanizer.entity.Employee;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.enums.CompanyRole;
import pl.mbrzozowski.vulcanizer.repository.CompanyBranchRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationCompanyBranch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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

    public List<CompanyBranchResponse> findAllWaiting() {
        List<CompanyBranchResponse> companyBranchResponseList = new ArrayList<>();
        List<CompanyBranch> companyBranchList = companyBranchRepository.findAllByWaiting();
        for (CompanyBranch companyBranch : companyBranchList) {
            User user = getUser(companyBranch);
            CompanyBranchResponse companyBranchResponse = CompanyBranchResponse.builder()
                    .id(companyBranch.getId())
                    .name(companyBranch.getName())
                    .description(companyBranch.getDescription())
                    .address(new AddressToAddressResponse().convert(companyBranch.getAddress()))
                    .createdDate(companyBranch.getCreatedDate().toString())
                    .phone(companyBranch.getPhone().getNumber())
                    .companyBranchSize(companyBranch.getCompany().getCompanyBranch().size())
                    .company(new CompanyToCompanyResponse().convert(companyBranch.getCompany()))
                    .build();
            if (user != null) {
                companyBranchResponse.setUser(new UserToUserResponse().convert(user));
            }
            companyBranchResponseList.add(companyBranchResponse);
        }
        return companyBranchResponseList;
    }

    private User getUser(CompanyBranch companyBranch) {
        Company company = companyBranch.getCompany();
        List<Employee> employees = company.getEmployees();
        for (Employee employee : employees) {
            if (employee.getRole() == CompanyRole.OWNER) {
                return employee.getUser();
            }
        }
        return null;
    }

    public void accept(String companyBranchId) {
        Optional<CompanyBranch> branchOptional = findById(getLongIdFromString(companyBranchId));
        if (branchOptional.isEmpty()) {
            throw new IllegalArgumentException("Company Branch is not exist");
        } else {
            CompanyBranch companyBranch = branchOptional.get();
            companyBranch.setActive(true);
            companyBranchRepository.save(companyBranch);
        }
    }

    public void decline(String companyBranchId) {
        Optional<CompanyBranch> branchOptional = findById(getLongIdFromString(companyBranchId));
        if (branchOptional.isEmpty()) {
            throw new IllegalArgumentException("Company Branch is not exist");
        } else {
            CompanyBranch companyBranch = branchOptional.get();
            companyBranch.setLocked(true);
            companyBranchRepository.save(companyBranch);
        }
    }

    private Long getLongIdFromString(String companyBranchId) {
        try {
            return Long.parseLong(companyBranchId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Id is not a number (Long)");
        }
    }

    public Optional<CompanyBranch> findById(Long id) {
        return companyBranchRepository.findById(id);
    }
}

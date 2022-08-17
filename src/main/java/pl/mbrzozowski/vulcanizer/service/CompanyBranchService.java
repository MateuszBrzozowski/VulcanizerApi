package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.CompanyBranchResponse;
import pl.mbrzozowski.vulcanizer.dto.StandRequest;
import pl.mbrzozowski.vulcanizer.dto.mapper.AddressToAddressResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.CompanyToCompanyResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserToUserResponse;
import pl.mbrzozowski.vulcanizer.entity.*;
import pl.mbrzozowski.vulcanizer.enums.CompanyRole;
import pl.mbrzozowski.vulcanizer.repository.CompanyBranchRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationCompanyBranch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyBranchService {
    private final CompanyBranchRepository companyBranchRepository;
    private final int MAX_STANDS = 10;

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

    public Optional<CompanyBranch> findById(Long id) {
        return companyBranchRepository.findById(id);
    }

    public void standAdd(User user, StandRequest standRequest) {
        ValidationCompanyBranch.validStandAdd(standRequest);
        long companyBranchId = getLongIdFromString(standRequest.getBranchId());
        int countOfStands = getIntIdFromString(standRequest.getCount());
        Optional<CompanyBranch> branchOptional = findById(companyBranchId);
        if (branchOptional.isPresent()) {
            CompanyBranch companyBranch = branchOptional.get();
            checkBranchIsUser(user, companyBranch);
            if (companyBranch.getStands().size() > MAX_STANDS || companyBranch.getStands().size() + countOfStands > 10) {
                throw new IllegalArgumentException("Max count of stands");
            }
            List<Stand> stands = companyBranch.getStands();
            for (int i = 0; i < countOfStands; i++) {
                int minNumber = getNumber(stands);
                Stand stand = new Stand(null, minNumber, companyBranch);
                stands.add(stand);
            }
            companyBranch.setStands(stands);
            companyBranchRepository.save(companyBranch);
        } else {
            throw new IllegalArgumentException("Company branch not exist");
        }

    }

    public void standRemove(User user, StandRequest standRequest) {
        ValidationCompanyBranch.validStandRemove(standRequest);
        long companyBranchId = getLongIdFromString(standRequest.getBranchId());
        int numberOfStand = getIntIdFromString(standRequest.getNumber());
        Optional<CompanyBranch> branchOptional = findById(companyBranchId);
        if (branchOptional.isPresent()) {
            CompanyBranch companyBranch = branchOptional.get();
            checkBranchIsUser(user, companyBranch);
            if (companyBranch.getStands().size() > 0) {
                List<Stand> stands = companyBranch.getStands();
                stands.sort(Comparator.comparing(Stand::getNumber));
                int indexToRemove = getIndexToRemove(numberOfStand, stands);
                removeStandAndUpdateNumbersOfAnotherStand(stands, indexToRemove);
                companyBranch.setStands(stands);
                companyBranchRepository.save(companyBranch);
            } else {
                throw new IllegalArgumentException("Stands are not exist");
            }

        }

    }

    private @NotNull Long getLongIdFromString(String source) {
        try {
            return Long.parseLong(source);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("%s can not convert to Long", source));
        }
    }

    private int getIntIdFromString(String source) {
        try {
            return Integer.parseInt(source);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("%s can not convert to Integer", source));
        }
    }

    private void checkBranchIsUser(User user, @NotNull CompanyBranch companyBranch) {
        Company company = companyBranch.getCompany();
        for (Employee employee : company.getEmployees()) {
            if (employee.getRole() == CompanyRole.OWNER) {
                User userCheck = employee.getUser();
                if (!user.equals(userCheck)) {
                    throw new BadCredentialsException("This company branch is not your");
                }
            }
        }
    }

    private int getIndexToRemove(int numberOfStand, @NotNull List<Stand> stands) {
        int indexToRemove = -1;
        for (int i = 0; i < stands.size(); i++) {
            if (stands.get(i).getNumber() == numberOfStand) {
                indexToRemove = i;
                break;
            }
        }
        return indexToRemove;
    }

    private void removeStandAndUpdateNumbersOfAnotherStand(List<Stand> stands, int index) {
        if (index != -1) {
            int removedNumber = stands.get(index).getNumber();
            stands.remove(index);
            for (int i = index; i < stands.size(); i++) {
                int number = stands.get(i).getNumber();
                stands.get(i).setNumber(removedNumber);
                removedNumber = number;
            }
        } else {
            throw new IllegalArgumentException("Stand not exist");
        }
    }

    private int getNumber(@NotNull List<Stand> stands) {
        int minNumber = MAX_STANDS;
        if (stands.size() == 0) {
            minNumber = 1;
        } else {
            for (int j = 1; j < MAX_STANDS + 1; j++) {
                int countOfNumber = 0;
                for (Stand stand : stands) {
                    if (stand.getNumber() == j) {
                        countOfNumber++;
                    }
                }
                if (countOfNumber == 0) {
                    minNumber = j;
                    break;
                }
            }
        }
        return minNumber;
    }
}

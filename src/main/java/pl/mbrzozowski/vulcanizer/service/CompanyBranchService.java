package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.CompanyBranchResponse;
import pl.mbrzozowski.vulcanizer.dto.CustomOpeningHoursRequest;
import pl.mbrzozowski.vulcanizer.dto.OpeningHoursRequest;
import pl.mbrzozowski.vulcanizer.dto.mapper.AddressToAddressResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.CompanyToCompanyResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.CustomOpeningHoursReqToEntity;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserToUserResponse;
import pl.mbrzozowski.vulcanizer.entity.*;
import pl.mbrzozowski.vulcanizer.enums.CompanyRole;
import pl.mbrzozowski.vulcanizer.enums.converter.StringDayToDayOfWeek;
import pl.mbrzozowski.vulcanizer.repository.CompanyBranchRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationCompanyBranch;
import pl.mbrzozowski.vulcanizer.validation.ValidationOpeningHours;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyBranchService {
    private final CompanyBranchRepository companyBranchRepository;
    private static final int MAX_STANDS = 10;

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

    public List<Stand> addStand(User user, String branchId, String count) {
        ValidationCompanyBranch.validStandAdd(branchId, count);
        long companyBranchId = getLongIdFromString(branchId);
        int countOfStands = getIntIdFromString(count);
        CompanyBranch companyBranch = getCompanyBranch(companyBranchId);
        checkBranchIsUser(user, companyBranch);
        if (companyBranch.getStands().size() > MAX_STANDS || companyBranch.getStands().size() + countOfStands > 10) {
            throw new IllegalArgumentException("Max count of stands");
        }
        List<Stand> stands = companyBranch.getStands();
        for (int i = 0; i < countOfStands; i++) {
            int minNumber = getNumber(stands);
            Stand stand = new Stand(null, minNumber, companyBranch, null);
            stands.add(stand);
        }
        companyBranch.setStands(stands);
        companyBranchRepository.save(companyBranch);
        return stands;
    }

    public List<Stand> removeStand(User user, String branchId, String number) {
        ValidationCompanyBranch.validStandRemove(branchId, number);
        long companyBranchId = getLongIdFromString(branchId);
        int numberOfStand = getIntIdFromString(number);
        CompanyBranch companyBranch = getCompanyBranch(companyBranchId);
        checkBranchIsUser(user, companyBranch);
        if (companyBranch.getStands().size() > 0) {
            List<Stand> stands = companyBranch.getStands();
            stands.sort(Comparator.comparing(Stand::getNumber));
            int indexToRemove = getIndexToRemove(numberOfStand, stands);
            removeStandAndUpdateNumbersOfAnotherStand(stands, indexToRemove);
            companyBranch.setStands(stands);
            companyBranchRepository.save(companyBranch);
            return stands;
        } else {
            throw new IllegalArgumentException("Stands are not exist");
        }
    }

    public List<Stand> findAllStandsForBranch(User user, String branchId) {
        ValidationCompanyBranch.validBranchId(branchId);
        long companyBranchId = getLongIdFromString(branchId);
        Optional<CompanyBranch> branchOptional = findById(companyBranchId);
        if (branchOptional.isPresent()) {
            CompanyBranch companyBranch = branchOptional.get();
            checkBranchIsUser(user, companyBranch);
            companyBranch.getStands().sort(Comparator.comparing(Stand::getNumber));
            return companyBranch.getStands();
        } else {
            return null;
        }
    }

    public void updateHoursOpening(User user, String branchId, List<OpeningHoursRequest> openingHoursRequestList) {
        long companyBranchId = getLongIdFromString(branchId);
        CompanyBranch companyBranch = getCompanyBranch(companyBranchId);
        checkBranchIsUser(user, companyBranch);
        ValidationOpeningHours.validRequest(openingHoursRequestList);
        List<OpeningHours> openingHours = companyBranch.getOpeningHours();
        if (openingHours.size() == 0) {
            createNewListOpeningHours(openingHoursRequestList, companyBranch, openingHours);
        } else if (openingHours.size() == 7) {
            for (OpeningHours openingHour : openingHours) {
                for (OpeningHoursRequest openingHoursRequest : openingHoursRequestList) {
                    if (openingHour.getDay().name().equalsIgnoreCase(openingHoursRequest.getDay())) {
                        LocalTime open = getLocalTimeFromString(openingHoursRequest.getOpenTime());
                        LocalTime close = getLocalTimeFromString(openingHoursRequest.getCloseTime());
                        ValidationOpeningHours.isAnyNull(open, close);
                        ValidationOpeningHours.isCloseTimeAfterOpenTime(open, close);
                        openingHour.setOpenTime(open);
                        openingHour.setCloseTime(close);
                        break;
                    }
                }
            }
        } else {
            openingHours.clear();
            createNewListOpeningHours(openingHoursRequestList, companyBranch, openingHours);
        }
        companyBranch.setOpeningHours(openingHours);
        companyBranchRepository.save(companyBranch);
    }

    public List<OpeningHours> findHoursOpening(User user, String branchId) {
        long companyBranchId = getLongIdFromString(branchId);
        CompanyBranch companyBranch = getCompanyBranch(companyBranchId);
        checkBranchIsUser(user, companyBranch);
        return companyBranch.getOpeningHours();
    }

    public List<CustomOpeningHours> addCustomOpeningHours(User user, String branchId, CustomOpeningHoursRequest openingHoursRequest) {
        ValidationOpeningHours.validCustomRequest(openingHoursRequest);
        CustomOpeningHours newOpeningHours = new CustomOpeningHoursReqToEntity().convert(openingHoursRequest);
        ValidationOpeningHours.validCustomOpeningHours(newOpeningHours);
        Long companyBranchId = getLongIdFromString(branchId);
        CompanyBranch companyBranch = getCompanyBranch(companyBranchId);
        checkBranchIsUser(user, companyBranch);
        List<CustomOpeningHours> customOpeningHoursList = companyBranch.getCustomOpeningHours();
        removeOldCustomOpeningHours(customOpeningHoursList);
        ValidationOpeningHours.datesAreNotExist(newOpeningHours, customOpeningHoursList);
        newOpeningHours.setCompanyBranch(companyBranch);
        customOpeningHoursList.add(newOpeningHours);
        companyBranch.setCustomOpeningHours(customOpeningHoursList);
        CompanyBranch saved = companyBranchRepository.save(companyBranch);
        saved.getCustomOpeningHours().sort(Comparator.comparing(CustomOpeningHours::getDateStart));
        return saved.getCustomOpeningHours();
    }

    public List<CustomOpeningHours> findCustomOpeningHours(User user, String branchId) {
        Long companyBranchId = getLongIdFromString(branchId);
        CompanyBranch companyBranch = getCompanyBranch(companyBranchId);
        checkBranchIsUser(user, companyBranch);
        removeOldCustomOpeningHours(companyBranch.getCustomOpeningHours());
        CompanyBranch saved = companyBranchRepository.save(companyBranch);
        saved.getCustomOpeningHours().sort(Comparator.comparing(CustomOpeningHours::getDateStart));
        return companyBranch.getCustomOpeningHours();
    }

    public List<CustomOpeningHours> deleteCustomOpeningHours(User user, String branchId, String hoursId) {
        Long companyBranchId = getLongIdFromString(branchId);
        Long customOpeningHoursId = getLongIdFromString(hoursId);
        CompanyBranch companyBranch = getCompanyBranch(companyBranchId);
        checkBranchIsUser(user, companyBranch);
        boolean isRemoved = companyBranch.getCustomOpeningHours().removeIf(coh -> coh.getId().equals(customOpeningHoursId));
        if (!isRemoved) {
            throw new IllegalArgumentException("Custom opening hours can not to be deleted");
        } else {
            CompanyBranch saved = companyBranchRepository.save(companyBranch);
            saved.getCustomOpeningHours().sort(Comparator.comparing(CustomOpeningHours::getDateStart));
            return companyBranch.getCustomOpeningHours();
        }
    }

    private void removeOldCustomOpeningHours(List<CustomOpeningHours> customOpeningHoursList) {
        customOpeningHoursList.removeIf(customOpeningHours -> customOpeningHours.getDateStart().isBefore(LocalDate.now())
                && customOpeningHours.getDateEnd().isBefore(LocalDate.now()));
    }

    /**
     * @param id of company branch which search
     * @return {@link CompanyBranch} from DB
     * @throws IllegalArgumentException when company branch doesn't exist in DB
     */
    private CompanyBranch getCompanyBranch(Long id) {
        Optional<CompanyBranch> branchOptional = findById(id);
        if (branchOptional.isPresent()) {
            return branchOptional.get();
        } else {
            throw new IllegalArgumentException("Company branch doesn't exist");
        }
    }

    private void createNewListOpeningHours(List<OpeningHoursRequest> openingHoursRequestList, CompanyBranch companyBranch, List<OpeningHours> openingHours) {
        for (OpeningHoursRequest openingHoursRequest : openingHoursRequestList) {
            String day = openingHoursRequest.getDay().toUpperCase();
            DayOfWeek dayOfWeek = new StringDayToDayOfWeek().convert(day);
            LocalTime open = getLocalTimeFromString(openingHoursRequest.getOpenTime());
            LocalTime close = getLocalTimeFromString(openingHoursRequest.getCloseTime());
            ValidationOpeningHours.isAnyNull(open, close);
            ValidationOpeningHours.isCloseTimeAfterOpenTime(open, close);
            OpeningHours openingHour = new OpeningHours(null,
                    dayOfWeek,
                    open,
                    close,
                    companyBranch);
            openingHours.add(openingHour);
        }
    }

    /**
     * Converting String to LocalTime. If source is null, method return null. If source can not convert to Local
     *
     * @param source String which convert to LocalTime. XX:XX
     * @return time like {@link LocalTime}
     * @throws DateTimeParseException when source can not to be converted to LocalTime.
     */
    private LocalTime getLocalTimeFromString(String source) {
        LocalTime result = null;
        if (source != null) {
            result = LocalTime.parse(source);
        }
        return result;
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
        if (stands.size() == 0) {
            return 1;
        }
        stands.sort(Comparator.comparing(Stand::getNumber));
        return stands.get(stands.size() - 1).getNumber() + 1;
    }
}

package pl.mbrzozowski.vulcanizer.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.BusinessPublicResponse;
import pl.mbrzozowski.vulcanizer.dto.CompanyResponse;
import pl.mbrzozowski.vulcanizer.dto.CompanyRequest;
import pl.mbrzozowski.vulcanizer.dto.mapper.BusinessToBusinessPublicResponse;
import pl.mbrzozowski.vulcanizer.entity.*;
import pl.mbrzozowski.vulcanizer.repository.CompanyRepository;
import pl.mbrzozowski.vulcanizer.util.Comparator;
import pl.mbrzozowski.vulcanizer.validation.ValidationCompany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.mbrzozowski.vulcanizer.enums.CompanyRole.OWNER;

@Data
@Slf4j
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final PhotoService photoService;
    private final PhoneService phoneService;
    private final AddressService addressService;
    private final EmployeeService employeeService;
    private final EmailService emailService;
    private final CompanyBranchService companyBranchService;
    protected Logger logger = LoggerFactory.getLogger(CompanyService.class);

    @Lazy
    @Autowired
    public CompanyService(CompanyRepository businessRepository,
                          PhotoService photoService,
                          PhoneService phoneService,
                          AddressService addressService,
                          EmployeeService employeeService,
                          EmailService emailService,
                          CompanyBranchService companyBranchService) {
        this.companyRepository = businessRepository;
        this.photoService = photoService;
        this.phoneService = phoneService;
        this.addressService = addressService;
        this.employeeService = employeeService;
        this.emailService = emailService;
        this.companyBranchService = companyBranchService;
    }

    /**
     * Creating Company and company branch for user {@link User}
     *
     * @param user           which create company and company branch
     * @param companyRequest {@link  CompanyRequest} data for create company and company branch.
     * @throws IllegalArgumentException when required data is blank or not valid.
     */
    public void save(User user, CompanyRequest companyRequest) {
        ValidationCompany.validBeforeCreate(companyRequest);
        isExistByNip(companyRequest.getNip());
        Address addressCompany = addressService.saveForBusiness(companyRequest.getAddress());
        Phone phoneCompany = phoneService.saveForBusiness(companyRequest.getPhone());
        Company company = Company.builder()
                .name(companyRequest.getName())
                .nip(companyRequest.getNip())
                .createdDate(LocalDateTime.now())
                .employees(new ArrayList<>())
                .companyBranch(new ArrayList<>())
                .phone(phoneCompany)
                .address(addressCompany)
                .isActive(true)
                .isLocked(false)
                .isClosed(false)
                .build();
        Employee employee = new Employee(null, user, company, OWNER);
        company.getEmployees().add(employee);
        Company companySaved = companyRepository.save(company);
        saveForExistCompany(user, companyRequest, companySaved);
        emailService.companyApplicationAccepted(user.getEmail());
    }

    /**
     * Creating company branch for exist company. NameCB, addressCB, phoneCB can not be blank.
     *
     * @param user           {@link User} which create company branch
     * @param companyRequest {@link CompanyRequest} specific data for create company branch
     * @param company        {@link Company} parent for company branch, can be null. But if is not exist in DB
     *                       method throw Exception
     * @throws IllegalArgumentException when company is not exist in DB or when name is blank or
     *                                  not valid, address is null or not valid
     *                                  Phone is null or not valid
     */
    public void saveForExistCompany(User user, CompanyRequest companyRequest, Company company) {
        ValidationCompany.validBeforeCreateCompanyBranch(companyRequest);
        if (company == null) {
            company = getCompanyByNip(companyRequest.getNip());
            if (company != null) {
                if (!checkIsUserCompany(user, company)) {
                    throw new BadCredentialsException("The company is not a user");
                }
            }
        }
        if (company == null) {
            throw new IllegalArgumentException("Company is not exist");
        } else {
            CompanyBranch companyBranch = new CompanyBranch();
            companyBranch.setName(companyRequest.getNameCB());
            companyBranch.setDescription(companyRequest.getDescriptionCB());
            Address addressCompany = company.getAddress();
            if (Comparator.compare(addressCompany, companyRequest.getAddressCB())) {
                companyBranch.setAddress(addressCompany);
            } else {
                companyBranch.setAddress(addressService.saveForBusiness(companyRequest.getAddressCB()));
            }
            Phone phoneCompany = company.getPhone();
            if (Comparator.compare(phoneCompany, companyRequest.getPhoneCB())) {
                companyBranch.setPhone(phoneCompany);
            } else {
                companyBranch.setPhone(phoneService.saveForBusiness(companyRequest.getPhoneCB()));
            }
            companyBranch.setCompany(company);
            companyBranchService.save(companyBranch);
            emailService.companyBranchApplicationAccepted(user.getEmail());
        }
    }

    private boolean checkIsUserCompany(User user, Company company) {
        for (Employee employee : user.getEmployees()) {
            if (employee.getCompany().getId().equals(company.getId())) {
                return true;
            }
        }
        return false;
    }

    private Company getCompanyByNip(String nip) {
        Optional<Company> companyOptional = companyRepository.findByNip(nip);
        return companyOptional.orElse(null);
    }

    private void isExistByNip(String nip) {
        Optional<Company> companyOptional = companyRepository.findByNip(nip);
        if (companyOptional.isPresent()) {
            throw new IllegalArgumentException("Company by nip is exist");
        }
    }

    public CompanyResponse update(CompanyRequest businessRequest) {
        throw new Error("Method Not implement -");
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findById(Long id) {
        return companyRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Business not found by id [%s]", id));
                });
    }

    public void deleteById(Long id) {

    }

    public List<BusinessPublicResponse> getRecommendBusiness() {
        //TODO
        // Pobieramy maksymalnie 10 biznesow ktore
        // w pierwszej kolejnosci pobieramy te ktore maja najwiecej opinii
        // potem jezeli takich nie ma to te ktore sa najdluzej, najdalsza data powstania
        // Jezeli jest mniej niz 10 biznesow to wypuszczamy wszystkie
        long count = companyRepository.count();
        if (count < 10) {
            List<Company> all = companyRepository.findAll();
            return all
                    .stream()
                    .map(business -> new BusinessToBusinessPublicResponse().convert(business))
                    .collect(Collectors.toList());
        }
        return null;
    }

    private void addressToAddressTransferNewData(Address address, Address addressNewData) {
        address.setAddressLine(addressNewData.getAddressLine());
        address.setCity(addressNewData.getCity());
        address.setCode(addressNewData.getCode());
        address.setState(addressNewData.getState());
        address.setCountry(addressNewData.getCountry());
    }

    public CompanyResponse updateStatus(CompanyRequest businessRequest) {
        return null;
    }
}

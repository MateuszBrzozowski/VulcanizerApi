package pl.mbrzozowski.vulcanizer.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.BusinessPublicResponse;
import pl.mbrzozowski.vulcanizer.dto.BusinessResponse;
import pl.mbrzozowski.vulcanizer.dto.CompanyRequest;
import pl.mbrzozowski.vulcanizer.dto.mapper.BusinessToBusinessPublicResponse;
import pl.mbrzozowski.vulcanizer.entity.*;
import pl.mbrzozowski.vulcanizer.repository.CompanyRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationCompany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pl.mbrzozowski.vulcanizer.enums.BusinessRole.OWNER;

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

    public void save(User user, CompanyRequest companyRequest) {
        ValidationCompany.validBeforeCreate(companyRequest);
        Address addressCompany = addressService.saveForBusiness(companyRequest.getAddress());
        Address addressCompanyBranch = null;
        if (!companyRequest.getAddress().equals(companyRequest.getAddressCB())) {
            addressCompanyBranch = addressService.saveForBusiness(companyRequest.getAddressCB());
        }
        Phone phoneCompany = phoneService.saveForBusiness(companyRequest.getPhone());
        Phone phoneCompanyBranch = null;
        if (!companyRequest.getPhone().equalsIgnoreCase(companyRequest.getPhoneCB())) {
            phoneCompanyBranch = phoneService.saveForBusiness(companyRequest.getPhoneCB());
        }
        Company company = Company.builder()
                .name(companyRequest.getName())
                .nip(companyRequest.getNip())
                .createdDate(LocalDateTime.now())
                .employees(new ArrayList<>())
                .companyBranch(new ArrayList<>())
                .phone(phoneCompany)
                .address(addressCompany)
                .isActive(false)
                .isLocked(false)
                .isClosed(false)
                .build();

        CompanyBranch companyBranch = companyBranchService.createWhileCreatingCompany(companyRequest);
        companyBranchService.setAddressForCompanyBranch(addressCompany, addressCompanyBranch, companyBranch);
        companyBranchService.setPhoneForCompanyBranch(phoneCompany, phoneCompanyBranch, companyBranch);
        Employee employee = new Employee(null, user, company, OWNER);
        company.getEmployees().add(employee);
        Company companySaved = companyRepository.save(company);
        companyBranch.setCompany(companySaved);
        companyBranchService.save(companyBranch);
//        company.getCompanyBranch().add(companyBranch);
        emailService.businessApplicationAccepted(user.getEmail());
    }

    public BusinessResponse update(CompanyRequest businessRequest) {
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

    public BusinessResponse updateStatus(CompanyRequest businessRequest) {
        return null;
    }
}

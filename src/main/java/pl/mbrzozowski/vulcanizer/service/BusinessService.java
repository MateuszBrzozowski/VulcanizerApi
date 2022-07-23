package pl.mbrzozowski.vulcanizer.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.BusinessRequest;
import pl.mbrzozowski.vulcanizer.dto.BusinessResponse;
import pl.mbrzozowski.vulcanizer.dto.EmployeeRequest;
import pl.mbrzozowski.vulcanizer.dto.EmployeeRoleRequest;
import pl.mbrzozowski.vulcanizer.dto.mapper.AddressRequestToAddress;
import pl.mbrzozowski.vulcanizer.dto.mapper.BusinessRequestToBusiness;
import pl.mbrzozowski.vulcanizer.dto.mapper.BusinessToBusinessResponse;
import pl.mbrzozowski.vulcanizer.entity.*;
import pl.mbrzozowski.vulcanizer.enums.BusinessStatus;
import pl.mbrzozowski.vulcanizer.exceptions.NoSuchElementException;
import pl.mbrzozowski.vulcanizer.repository.BusinessRepository;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationBusiness;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Service
public class BusinessService implements ServiceLayer<BusinessRequest, BusinessResponse, Business> {
    private final BusinessRepository businessRepository;
    private final PhotoService photoService;
    private final PhoneService phoneService;
    private final StateRepository stateRepository;
    private final StateService stateService;
    private final AddressService addressService;
    private final UserServiceImpl userService;
    private final EmployeeRoleService employeeRoleService;
    private final EmployeeService employeeService;
    protected Logger logger = LoggerFactory.getLogger(BusinessService.class);

    @Lazy
    @Autowired
    public BusinessService(BusinessRepository businessRepository,
                           PhotoService photoService,
                           PhoneService phoneService,
                           StateRepository stateRepository,
                           StateService stateService,
                           AddressService addressService,
                           UserServiceImpl userService,
                           EmployeeRoleService employeeRoleService,
                           EmployeeService employeeService) {
        this.businessRepository = businessRepository;
        this.photoService = photoService;
        this.phoneService = phoneService;
        this.stateRepository = stateRepository;
        this.stateService = stateService;
        this.addressService = addressService;
        this.userService = userService;
        this.employeeRoleService = employeeRoleService;
        this.employeeService = employeeService;
    }

    @Override
    public Business save(BusinessRequest businessRequest) {
        Address address = new AddressRequestToAddress(stateService).apply(businessRequest.getAddress());
        userService.findById(businessRequest.getUserId());
        ValidationBusiness.validCreateRequest(businessRequest, stateRepository, address);
        Business business =
                new BusinessRequestToBusiness()
                        .apply(businessRequest);

        business.setPhones(
                businessRequest
                        .getPhones()
                        .stream()
                        .map(phoneService::save)
                        .collect(Collectors.toSet())
        );

        if (businessRequest.getPhoto() != null) {
            Photo photo = new Photo(businessRequest.getPhoto());
            Photo photoReady = photoService.save(photo);
            business.setPhoto(photoReady);
        }
        Address savedAddress = addressService.save(businessRequest.getAddress());
        EmployeeRole owner = employeeRoleService.save(new EmployeeRoleRequest("Owner"));
        business.setAddress(savedAddress);
        business.setStatus(BusinessStatus.NOT_ACTIVATED);
        business.setCreatedDate(LocalDateTime.now());
        Business businessSaved = businessRepository.save(business);

        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .business(businessSaved)
                .userId(businessRequest.getUserId())
                .role(owner)
                .build();

        employeeService.save(employeeRequest);
        return business;
    }

    @Override
    public BusinessResponse update(BusinessRequest businessRequest) {
        Address addressNewData = new AddressRequestToAddress(stateService).apply(businessRequest.getAddress());
        ValidationBusiness.validBeforeEdit(businessRequest, stateRepository, addressNewData);
        Business business = findById(businessRequest.getId());
        Address address = business.getAddress();
        addressToAddressTransferNewData(address, addressNewData);
        Business businessNewData =
                new BusinessRequestToBusiness()
                        .apply(businessRequest);
        businessNewData.setPhoto(business.getPhoto());
        businessToBusinessTransferNewData(business, businessNewData);
        updatePhoto(businessRequest, business);

        Set<Phone> phones = business.getPhones();
        Set<Phone> phonesToDelete = new HashSet<>(phones);
        business.deletePhones();
        phonesToDelete.forEach(phone -> phoneService.deleteById(phone.getId()));
        business.setPhones(
                businessRequest.getPhones()
                        .stream()
                        .map(phoneService::save)
                        .collect(Collectors.toSet())
        );

        businessRepository.save(business);
        return new BusinessToBusinessResponse().apply(business);
    }

    @Override
    public List<BusinessResponse> findAll() {
        return null;
    }

    @Override
    public Business findById(Long id) {
        return businessRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new NoSuchElementException(String.format("Business not found by id [%s]", id));
                });
    }

    @Override
    public void deleteById(Long id) {

    }

    private void updatePhoto(BusinessRequest businessRequest, Business business) {
        if (business.getPhoto() == null && businessRequest.getPhoto() != null) { //DB no photo, add photo from req
            Photo photo = photoService.save(new Photo(businessRequest.getPhoto()));
            business.setPhoto(photo);
        } else if (business.getPhoto() != null && businessRequest.getPhoto() != null) { //DB has a photo, edit photo from req
            business.getPhoto().setUrl(businessRequest.getPhoto());
        } else if (business.getPhoto() != null && businessRequest.getPhoto() == null) { //DB has a photo, delete from DB.
            Long id = business.getPhoto().getId();
            business.setPhoto(null);
            photoService.deleteById(id);
        }
    }

    private void addressToAddressTransferNewData(Address address, Address addressNewData) {
        address.setAddressLineOne(addressNewData.getAddressLineOne());
        address.setAddressLineTwo(addressNewData.getAddressLineTwo());
        address.setCity(addressNewData.getCity());
        address.setCode(addressNewData.getCode());
        address.setState(addressNewData.getState());
        address.setCountry(addressNewData.getCountry());
    }


    private void businessToBusinessTransferNewData(Business business, Business businessNewData) {
        business.setName(businessNewData.getName());
        business.setNip(businessNewData.getNip());
        business.setDescription(businessNewData.getDescription());
    }

    public BusinessResponse updateStatus(BusinessRequest businessRequest) {
        return null;
    }
}

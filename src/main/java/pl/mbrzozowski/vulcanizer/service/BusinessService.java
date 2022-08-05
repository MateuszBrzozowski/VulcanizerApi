package pl.mbrzozowski.vulcanizer.service;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.BusinessPublicResponse;
import pl.mbrzozowski.vulcanizer.dto.BusinessRequest;
import pl.mbrzozowski.vulcanizer.dto.BusinessResponse;
import pl.mbrzozowski.vulcanizer.dto.EmployeeRequest;
import pl.mbrzozowski.vulcanizer.dto.mapper.AddressRequestToAddress;
import pl.mbrzozowski.vulcanizer.dto.mapper.BusinessRequestToBusiness;
import pl.mbrzozowski.vulcanizer.dto.mapper.BusinessToBusinessPublicResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.BusinessToBusinessResponse;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.Phone;
import pl.mbrzozowski.vulcanizer.entity.Photo;
import pl.mbrzozowski.vulcanizer.enums.BusinessStatus;
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
public class BusinessService {
    private final BusinessRepository businessRepository;
    private final PhotoService photoService;
    private final PhoneService phoneService;
    private final StateRepository stateRepository;
    private final StateService stateService;
    private final AddressService addressService;
    private final UserServiceImpl userService;
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
                           EmployeeService employeeService) {
        this.businessRepository = businessRepository;
        this.photoService = photoService;
        this.phoneService = phoneService;
        this.stateRepository = stateRepository;
        this.stateService = stateService;
        this.addressService = addressService;
        this.userService = userService;
        this.employeeService = employeeService;
    }

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
        business.setAddress(savedAddress);
        business.setStatus(BusinessStatus.NOT_ACTIVATED);
        business.setCreatedDate(LocalDateTime.now());
        Business businessSaved = businessRepository.save(business);

        EmployeeRequest employeeRequest = EmployeeRequest.builder()
                .business(businessSaved)
                .userId(businessRequest.getUserId())
                .build();

        employeeService.save(employeeRequest);
        return business;
    }

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

    public List<BusinessResponse> findAll() {
        return null;
    }

    public Business findById(Long id) {
        return businessRepository
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
        long count = businessRepository.count();
        if (count < 10) {
            List<Business> all = businessRepository.findAll();
            return all
                    .stream()
                    .map(business -> new BusinessToBusinessPublicResponse().convert(business))
                    .collect(Collectors.toList());
        }
        return null;
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
        address.setAddressLine(addressNewData.getAddressLine());
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

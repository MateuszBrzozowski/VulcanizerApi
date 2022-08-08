package pl.mbrzozowski.vulcanizer.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.BusinessPublicResponse;
import pl.mbrzozowski.vulcanizer.dto.BusinessRequest;
import pl.mbrzozowski.vulcanizer.dto.BusinessResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.BusinessToBusinessPublicResponse;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.Photo;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.repository.BusinessRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationBusiness;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Slf4j
@Service
public class BusinessService {
    private final BusinessRepository businessRepository;
    private final PhotoService photoService;
    private final PhoneService phoneService;
    private final AddressService addressService;
    private final EmployeeService employeeService;
    private final EmailService emailService;
    protected Logger logger = LoggerFactory.getLogger(BusinessService.class);

    @Lazy
    @Autowired
    public BusinessService(BusinessRepository businessRepository,
                           PhotoService photoService,
                           PhoneService phoneService,
                           AddressService addressService,
                           EmployeeService employeeService,
                           EmailService emailService) {
        this.businessRepository = businessRepository;
        this.photoService = photoService;
        this.phoneService = phoneService;
        this.addressService = addressService;
        this.employeeService = employeeService;
        this.emailService = emailService;
    }

    public void save(User user, BusinessRequest businessRequest) {
        ValidationBusiness.validBeforeCreate(businessRequest);
        Address address = addressService.saveForBusiness(businessRequest.getAddress());
        Business business = Business.builder()
                .name(businessRequest.getName())
                .displayName(businessRequest.getDisplayName())
                .nip(businessRequest.getNip())
                .createdDate(LocalDateTime.now())
                .description(businessRequest.getDescription())
                .employees(new ArrayList<>())
                .address(address)
                .isActive(false)
                .isLocked(false)
                .isClosed(false)
                .build();
        Business businessSaved = businessRepository.save(business);
        employeeService.createBusiness(user, businessSaved);
        emailService.businessApplicationAccepted(user.getEmail());
    }

    public BusinessResponse update(BusinessRequest businessRequest) {
        throw new Error("Method Not implement -");
    }

    public List<Business> findAll() {
        return businessRepository.findAll();
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

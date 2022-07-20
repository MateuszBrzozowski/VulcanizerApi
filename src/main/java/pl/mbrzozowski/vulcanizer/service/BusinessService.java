package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.BusinessCreateRequest;
import pl.mbrzozowski.vulcanizer.dto.BusinessRequest;
import pl.mbrzozowski.vulcanizer.dto.BusinessResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.BusinessCreateRequestToBusinessRequest;
import pl.mbrzozowski.vulcanizer.dto.mapper.BusinessRequestToBusiness;
import pl.mbrzozowski.vulcanizer.entity.Address;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.Photo;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.enums.BusinessStatus;
import pl.mbrzozowski.vulcanizer.exceptions.NoSuchElementException;
import pl.mbrzozowski.vulcanizer.repository.BusinessRepository;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationBusiness;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessService implements ServiceLayer<BusinessRequest, BusinessResponse, Business> {
    private final BusinessRepository businessRepository;
    private final PhotoService photoService;
    private final StateRepository stateRepository;
    private final StateService stateService;
    private final AddressService addressService;
    private final UserService userService;

    @Override
    public Business save(BusinessRequest businessRequest) {
        Business business =
                new BusinessRequestToBusiness()
                        .apply(businessRequest);
        Address savedAddress = addressService.save(businessRequest.getAddress());
        business.setAddress(savedAddress);
        business.setStatus(BusinessStatus.NOT_ACTIVATED);
        business.setCreatedDate(LocalDateTime.now());

        if (businessRequest.getPhoto() != null) {
            Photo photo = new Photo(businessRequest.getPhoto());
            Photo photoReady = photoService.save(photo);
            business.setPhoto(photoReady);
        }
        //TODO stworzenie Domyślej roli dla pracownika, wpisanie usera tworzacego jako wlasciciela tego businessu

        return businessRepository.save(business);
    }

    public Business create(BusinessCreateRequest businessCreateRequest) {
        User user = userService.findById(businessCreateRequest.getUserId());
        BusinessRequest apply =
                new BusinessCreateRequestToBusinessRequest()
                        .apply(businessCreateRequest);
        ValidationBusiness.validCreateRequest(apply, stateRepository, stateService);
        return save(apply);
    }

    @Override
    public BusinessResponse update(BusinessRequest businessRequest) {

        return null;
    }

    public BusinessResponse updateStatus(BusinessRequest businessRequest) {
        return null;
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
}

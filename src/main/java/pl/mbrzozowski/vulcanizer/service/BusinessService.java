package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mbrzozowski.vulcanizer.dto.BusinessCreateRequest;
import pl.mbrzozowski.vulcanizer.dto.BusinessRequest;
import pl.mbrzozowski.vulcanizer.dto.BusinessResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.BusinessCreateRequestToBusinessRequest;
import pl.mbrzozowski.vulcanizer.dto.mapper.BusinessRequestToBusiness;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.Photo;
import pl.mbrzozowski.vulcanizer.repository.BusinessRepository;
import pl.mbrzozowski.vulcanizer.repository.StateRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationBusiness;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessService implements ServiceLayer<BusinessRequest, BusinessResponse, Business> {
    private final BusinessRepository businessRepository;
    private final PhotoService photoService;
    private final StateRepository stateRepository;

    @Override
    public Business save(BusinessRequest businessRequest) {
        Business business =
                new BusinessRequestToBusiness()
                        .apply(businessRequest);

        if (businessRequest.getPhoto() != null) {
            Photo photo = new Photo(businessRequest.getPhoto());
            Photo photoReady = photoService.save(photo);
            business.setPhoto(photoReady);
        }
        //TODO stworzenie Domyślej roli dla pracownika, wpisanie usera tworzacego jako wlasciciela tego businessu

        //TODO Stworzyć record adresu i dodać do business

        return businessRepository.save(business);
    }

    public Business create(BusinessCreateRequest businessCreateRequest) {
        BusinessRequest apply =
                new BusinessCreateRequestToBusinessRequest()
                        .apply(businessCreateRequest);
        ValidationBusiness.validCreateRequest(apply, stateRepository);
        return save(apply);
    }

    @Override
    public BusinessResponse update(BusinessRequest businessRequest) {

        return null;
    }

    public BusinessResponse updateStatus(BusinessRequest businessRequest) {
        //TODO możliwość zmiany tylko statusu biznesu - aktywacja - zablkoowanie itd.
        return null;
    }

    @Override
    public List<BusinessResponse> findAll() {
        return null;
    }

    @Override
    public Business findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}

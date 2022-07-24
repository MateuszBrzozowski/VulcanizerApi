package pl.mbrzozowski.vulcanizer.service;

import lombok.RequiredArgsConstructor;
import pl.mbrzozowski.vulcanizer.dto.BusinessServiceRequest;
import pl.mbrzozowski.vulcanizer.entity.Business;
import pl.mbrzozowski.vulcanizer.entity.BusinessService;
import pl.mbrzozowski.vulcanizer.exceptions.IllegalArgumentException;
import pl.mbrzozowski.vulcanizer.repository.ServiceRepository;
import pl.mbrzozowski.vulcanizer.validation.ValidationService;

import java.util.List;

@RequiredArgsConstructor
public class BusinessServiceService implements ServiceLayer<BusinessServiceRequest, BusinessService, BusinessService> {
    private final ServiceRepository serviceRepository;
    private final pl.mbrzozowski.vulcanizer.service.BusinessService businessService;

    @Override
    public BusinessService save(BusinessServiceRequest serviceRequest) {
        ValidationService.validBeforeCreated(serviceRequest);
        Business business = businessService.findById(serviceRequest.getBusiness());
        BusinessService service =
                new BusinessService(business,
                        serviceRequest.getNameOne(),
                        serviceRequest.getNameTwo(),
                        serviceRequest.getExecutionTime(),
                        serviceRequest.getPrice()
                );
        return serviceRepository.save(service);
    }

    @Override
    public BusinessService update(BusinessServiceRequest serviceRequest) {
        BusinessService service = findById(serviceRequest.getId());
        ValidationService.validBeforeUpdate(serviceRequest);
        serviceToServiceNewData(service, serviceRequest);
        return serviceRepository.save(service);
    }

    private void serviceToServiceNewData(BusinessService service, BusinessServiceRequest serviceRequest) {
        service.setNameOne(serviceRequest.getNameOne());
        service.setNameTwo(serviceRequest.getNameTwo());
        service.setExecutionTime(serviceRequest.getExecutionTime());
        service.setPrice(serviceRequest.getPrice());
    }

    @Override
    public List<BusinessService> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public BusinessService findById(Long id) {
        return serviceRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException(String.format("Not found service by id [%s]", id));
                });
    }

    @Override
    public void deleteById(Long id) {
        // TODO
        //  Nie można od tak usunąć servicu. Może ma jakas wizyte umowioną? Wizyta aktywna.
        //  Zaimplementować wizyte
        serviceRepository.deleteById(id);
    }
}
